package UnCertainTarget;

import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
//import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.mail.MessagingException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

//import com.sun.jndi.toolkit.url.Uri;


import EarlyWarning.Email;
import EarlyWarning.News;
import FileOperator.HDFSFileRead;

public class Analysis {
	static final String LinkHDFSPath = "hdfs://localhost:9000/user/hadoop/";
	public static class AnaMapper extends Mapper<Object, Text, Text, Text>{
		Text SouIp = new Text();
		Text DesIp = new Text();
		Text Url = new Text();
		Text AppMd5 = new Text();
//		Path blacklist_path = new Path();
		//写预警
		public void preWarnningW(String black,String recorde) throws IOException{
			StringTokenizer itr = new StringTokenizer(recorde);
			String devId = null;
			while(itr.hasMoreTokens()){
				String line = itr.nextToken().toString();
				if(line.contains("http://")){
					File file = new File("/home/data/History/warnRecorde.txt");
					if(!file.exists()||file.isDirectory()){
						throw new FileNotFoundException();
					}
					FileWriter fw = new FileWriter(file,true);
					fw.write(black+" ");
					fw.write(devId+" ");
					fw.write("0\r\n");
					fw.close();
					break;
				}
				devId = line;
			}
		}
		//读预警信息
		public static void preWarnningR() throws IOException, MessagingException, InterruptedException{
			File file = new File("/home/data/History/warnRecorde.txt");
			if(!file.exists()){
				throw new FileNotFoundException();
			}
			RandomAccessFile randfile = new RandomAccessFile(file, "rw");
			long filepoint = randfile.getFilePointer();
			String temp = null;
			temp = randfile.readLine();
			while(temp != null){
				WarnThreaed task = new WarnThreaed("");
				Thread thread = new Thread(task);
				thread.start();			StringTokenizer itr = new StringTokenizer(temp);
				String[] line = new String[3];
				if(itr.hasMoreTokens()){
					line[0] = itr.nextToken().toString();
					line[1] = itr.nextToken().toString();
					line[2] = itr.nextToken().toString();
					if(line[2].compareTo("0") == 0){
						if(Warnning(line[0],line[1])){
							randfile.seek(filepoint);
							randfile.writeUTF(line[0]+" "+line[1]+" 1\r\n");
						}
					}
				}
				filepoint = randfile.getFilePointer();
				temp = randfile.readLine();
			}
			randfile.close();
		}
		//查找用户ID
		public static String findPeopleId(String DevId) throws IOException{
//			File file = new File("/home/data/Device/device.txt");
//			if(!file.exists()){
//				throw new FileNotFoundException();
//			}
//			BufferedReader br = new BufferedReader(new FileReader(file));
//			String line = null;
//			line = br.readLine();
//			while(line != null){
//				StringTokenizer itr = new StringTokenizer(line);
//				if(itr.hasMoreTokens()){
//					String devId = itr.nextToken().toString();
//					if(devId.compareTo(DevId) == 0){
//						itr.nextToken();
//						return itr.nextToken().toString();
//					}
//				}
//			}
			String filepath = "hdfs://localhost:9000/user/hadoop/Input/device/device.txt";
			ArrayList<String> devicelist = HDFSFileRead.readFileAll(filepath);
			for(Iterator<String> iter = devicelist.iterator();iter.hasNext();)
			{
				StringTokenizer itr = new StringTokenizer(iter.next());
				if(itr.hasMoreTokens()){
					String devId = itr.nextToken().toString();
					if(devId.compareTo(DevId) == 0){
						itr.nextToken();
						return itr.nextToken().toString();
					}
				}
			}
			return null;
		}
		//查找用户信息
		public static String findPeopleNews(String pId) throws IOException{
//			if(pId == null)
//				return null;
//			File file = new File("/home/data/People/people.txt");
//			if(!file.exists()){
//				throw new FileNotFoundException();
//			}
//			BufferedReader br = new BufferedReader(new FileReader(file));
//			String line = null;
//			line = br.readLine();
//			while(line != null){
//				StringTokenizer itr = new StringTokenizer(line);
//				if(itr.hasMoreTokens()){
//					String peopleId = itr.nextToken().toString();
//					if(peopleId.compareTo(pId) == 0){
//						itr.nextToken();
//						return line;
//					}
//				}
//			}
			String filepath = "hdfs://localhost:9000/user/hadoop/Input/People/people.txt";
			ArrayList<String> peoplelist = HDFSFileRead.readFileAll(filepath);
			for(Iterator<String> iter = peoplelist.iterator();iter.hasNext();)
			{
				String line = iter.next();
				StringTokenizer itr = new StringTokenizer(line);
				if(itr.hasMoreTokens()){
					String peopleId = itr.nextToken().toString();
					if(peopleId.compareTo(pId) == 0){
						itr.nextToken();
						return line;
					}
				}
			}
			return null;
		}
		//推送消息
		public static boolean Warnning(String black,String devId) throws IOException, MessagingException, InterruptedException{
			String people = findPeopleNews(findPeopleId(devId));
			if(people == null)
				return false;
			StringTokenizer itr = new StringTokenizer(people);
			if(itr.hasMoreTokens()){
				String peopleId = itr.nextToken().toString();
				String Name = itr.nextToken().toString();
				String phone = itr.nextToken().toString();
				String EmailAdd = itr.nextToken().toString();
				
				String message = "尊敬的"+peopleId+":"+Name+"(先生/女士)："+buildMessage(new Text(black));
				Email email = new Email();
				email.sendMessage(EmailAdd, message);
				News.sendMessage(phone, message);
				return true;
			}
			return false;
			
		}
		//读取黑名单
		public ArrayList<String>  ReadBlackData(String path) throws IOException{
			
//			File file = new File(path);
//			if(!file.exists()||file.isDirectory()){
//				throw new FileNotFoundException();
//			}
//			
//			BufferedReader br = new BufferedReader(new FileReader(file));
//			ArrayList<String> list = new ArrayList<String>();
//			String temp = null;
//			temp = br.readLine();
//			while(temp != null){
//				StringTokenizer itr = new StringTokenizer(temp);
//				if(itr.hasMoreTokens()){
//					itr.nextToken().toString();
//					String Re = itr.nextToken().toString();
//					list.add(Re);
//				}
//				temp = br.readLine();
//			}
//			br.close();
			String filepath = LinkHDFSPath+"Input/Blacklist/"+path;
			ArrayList<String> list = HDFSFileRead.readFileAll(filepath);
			return list;
		}
		//构建消息
		public static String buildMessage(Text key){
			String message = new String();
			String str = key.toString();
			if(str.contains("http://"))
			{
				message = "您访问的资源"+key.toString()+"不安全，请慎重！" ;
			}
			else if(str.contains(".")){
				message = "您访问的Ip:"+key.toString()+"不安全，请慎重！" ;
			}
			else{
				message = "您的设备可能已经感染"+key.toString()+"病毒，请使用杀毒软件检测，一确保设备安全！";
			}
			return message;
		}
		//Map处理函数
		public void map(Object key,Text value,Context context)throws IOException,InterruptedException{
			StringTokenizer data = new StringTokenizer(value.toString());
			//处理记录 提取记录中的有用字段
			if(data.hasMoreTokens()){
				SouIp.set(data.nextToken().toString());
				data.nextToken();
				DesIp.set(data.nextToken().toString());
				while(data.hasMoreTokens()){
					String line = data.nextToken().toString();
					if(line.contains("http://")){
						Url.set(line);
						if(data.hasMoreTokens()){
							data.nextToken();
							if(data.hasMoreTokens()){
								AppMd5.set(data.nextToken().toString());
							}
						}
						break;
					}
				}
			}
			
			//find vicious
//			String Ipblackpath = "/home/data/Blacklist/Ip/ipblacklist.txt";
//			String Urlblackpath = "/home/data/Blacklist/Url/urlblacklist.txt";
//			String Appblackpath = "/home/data/Blacklist/App/appblacklist.txt";
			String Ipblackpath = "Ip/ipblacklist.txt";
			String Urlblackpath = "Url/urlblacklist.txt";
			String Appblackpath = "App/appblacklist.txt";
			
			ArrayList<String> BlackIp = ReadBlackData(Ipblackpath);
			ArrayList<String> BlackUrl = ReadBlackData(Urlblackpath);
			ArrayList<String> BlackApp = ReadBlackData(Appblackpath);
			
			String recorde;
			WarnThreaed task = new WarnThreaed("");
			Thread thread = new Thread(task);
			thread.start();	if(BlackIp.contains(SouIp.toString()) == true){

				preWarnningW(SouIp.toString(),value.toString());
				recorde = value.toString()+" dubious";
				context.write(DesIp, new Text(recorde));
				context.write(Url, new Text(recorde));
				context.write(AppMd5, new Text(recorde));
				context.write(SouIp, new Text(value.toString()+" dangerous"));
			}
			if(BlackIp.contains(DesIp.toString()) == true){

				preWarnningW(DesIp.toString(),value.toString());
				recorde = value.toString()+" dubious";
				context.write(SouIp, new Text(recorde));
				context.write(Url, new Text(recorde));
				context.write(AppMd5, new Text(recorde));
				context.write(SouIp, new Text(value.toString()+" dangerous"));
			}
			if(BlackUrl.contains(Url.toString()) == true){

				preWarnningW(Url.toString(),value.toString());
				recorde = value.toString()+" dubious";
				context.write(SouIp, new Text(recorde));
				context.write(AppMd5, new Text(recorde));
				context.write(DesIp, new Text(recorde));
				context.write(Url, new Text(value.toString()+" dangerous"));
			}
			if(BlackApp.contains(AppMd5.toString()) == true){

				preWarnningW(AppMd5.toString(),value.toString());
				recorde = value.toString()+" dubious";
				context.write(SouIp, new Text(recorde));
				context.write(Url, new Text(recorde));
				context.write(DesIp, new Text(recorde));
				context.write(AppMd5, new Text(value.toString()+" dangerous"));
			}
			
		}
	}
	
	//Reduce 处理函数
	public static class AnaReduce extends Reducer<Text, Text, Text, Text>{
//		@SuppressWarnings("null")
		public void reduce(Text key, Text value,Context context)throws IOException,InterruptedException, MessagingException{
			context.write(key, value);
		}
	}
	
	public static class WarnThreaed implements Runnable {
		@SuppressWarnings("unused")
		private String name;
		public  WarnThreaed(String name) {
			this.name = name;
		}
	    public void run() {
	    	while(true){
		    	try {
					AnaMapper.preWarnningR();
				} catch (IOException | MessagingException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	try {
					Thread.sleep(1000*60*60);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    }
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args)throws IOException,InterruptedException, ClassNotFoundException, URISyntaxException{
		if(args.length != 2){
			System.out.println("Please enter <Input> <Output>");
			System.exit(0);
		}
	
		Configuration conf = new Configuration();
		Job job = new Job(conf,"Vicious Ip Found");
		URI url = new URI("hdfs://127.0.0.1:9000");
//		URI url = new URI("hdfs://localhost:9000");
		FileSystem fs = FileSystem.get(url,conf);
		fs.delete(new Path(args[1]));
		//
		//黑名单数据是否存在
		File file = new File("/home/data/Blacklist/Ip/ipblacklist.txt");
		if(file.exists()){
			file.delete();
		}
		//
		File file1 = new File("/home/data/Blacklist/Url/urlblacklist.txt");
		if(file1.exists()){
			file1.delete();
		}
		//
		File file2 = new File("/home/data/Blacklist/App/appblacklist.txt");
		if(file2.exists()){
			file2.delete();
		}
		//加载黑名单数据到本地
//		fs.copyToLocalFile(new Path(args[0]+"/Blacklist/Ip/ipblacklist.txt"), new Path("/home/data/Blacklist/Ip/ipblacklist.txt"));
//		fs.copyToLocalFile(new Path(args[0]+"/Blacklist/Url/urlblacklist.txt"), new Path("/home/data/Blacklist/Url/urlblacklist.txt"));
//		fs.copyToLocalFile(new Path(args[0]+"/Blacklist/App/appblacklist.txt"), new Path("/home/data/Blacklist/App/appblacklist.txt"));
//		fs.copyToLocalFile(new Path(args[0]+"/Blacklist/Ip/aqzx_ip_2015_07_23_09_02_31"), new Path("/home/data/Blacklist/Ip/ipblacklist.txt"));
//		fs.copyToLocalFile(new Path(args[0]+"/Blacklist/Url/aqzx_url_2015_07_23_09_02_35"), new Path("/home/data/Blacklist/Url/urlblacklist.txt"));
//		fs.copyToLocalFile(new Path(args[0]+"/Blacklist/App/aqzx_app_2015_07_23_09_02_33"), new Path("/home/data/Blacklist/App/appblacklist.txt"));
		//设置job
		job.setJarByClass(Analysis.class);
		job.setMapperClass(AnaMapper.class);
		job.setReducerClass(AnaReduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		//设置输入输出路径
		FileInputFormat.addInputPath(job,new Path(args[0]+"/Action/MobilePhone"));
		FileOutputFormat.setOutputPath(job, new Path(args[1]+"/targetResult"));
		//启动任务
		
//		WarnThreaed task = new WarnThreaed("");
//		Thread thread = new Thread(task);
//		thread.start();
		
		if(job.waitForCompletion(true)){
			System.out.println("-----------------------------------------");
			System.out.println("Run finished");
		}
		else{
			System.out.println("-----------------------------------------");
			System.out.println("Run error");
		}
	}
}
