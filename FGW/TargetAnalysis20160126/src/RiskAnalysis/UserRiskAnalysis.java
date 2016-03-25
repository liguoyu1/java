package RiskAnalysis;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import UnCertainTarget.Analysis.AnaMapper;
import FileOperator.HDFSFileRead;

public class UserRiskAnalysis {
	static ArrayList<String> people_list = new ArrayList<String>();
	static HashMap<String, ArrayList<String>> people_devices = new HashMap<String,ArrayList<String>>();
	public static String buildPeople_device(String DevId){
		for(Iterator<String> iter = people_list.iterator();iter.hasNext();){
			StringTokenizer itr = new StringTokenizer(iter.next());
			if(itr.hasMoreTokens()){
				String devId_re = itr.nextToken().toString();
				String devId = devId_re.substring(1, devId_re.length()-1);
				if(DevId.contains(devId)){
					itr.nextToken();
					String people = itr.nextToken();
//					if(!people_devices.containsKey(people))
//					{
//						people_devices.put(people, new ArrayList<String>());
//					}
//					people_devices.get(people).add(DevId);
					return people;
				}
			}
		}
		return null;
	}
	
	//读取设备用户映射数据表
	public static void setPeople_list(String filepath,String filename) throws IOException{
		people_list = HDFSFileRead.readFileAll(filepath);
//		for(Iterator<String> iter = people_list.iterator();iter.hasNext();){
//			System.out.println(iter.next());
//		}
//		System.out.println(people_list);
	}
	
	public static void getPeople(){
		
	}
	
	//Map 节点处理
	public static class UserRiskAnalysisMapper extends Mapper<Object, Text, Text, FloatWritable>{
		
		public  void map(Object key,Text value,Context context)throws IOException,InterruptedException{
			String people = buildPeople_device(value.toString());
			if(people != null){
				System.out.println(people+"+"+value.toString());
				String num = value.toString().split(" ,")[1];
				context.write(new Text(people), new FloatWritable(Float.parseFloat(num)));
			}
		}
	}
	
	public static class UserRiskAnalysisReducer extends Reducer<Text,FloatWritable,Text,FloatWritable>{
		public void reducer(Text key,Iterator<FloatWritable> values,Context context) throws IOException, InterruptedException{
			float num = 0;
			System.out.println(key.toString());
//			for(FloatWritable v:values)
//			{
//				num += v.get();
//			}
			context.write(key, new FloatWritable(num));
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void Analysis(String[] args) throws IOException, URISyntaxException, ClassNotFoundException, InterruptedException{
		setPeople_list("hdfs://localhost:9000/user/hadoop/Input/device/device.txt", "Resault-2016-03-22-14-41-52");
		if(args.length != 2){
			System.out.println("Please enter <Input> <Output>");
			System.exit(0);
		}
	
		Configuration conf = new Configuration();
//		@SuppressWarnings("deprecation")
		Job job = new Job(conf,"Vicious Ip Found");
		URI url = new URI("hdfs://127.0.0.1:9000");
//		URI url = new URI("hdfs://localhost:9000");
		FileSystem fs = FileSystem.get(url,conf);
		fs.delete(new Path(args[1]));
		//		
		//设置job
		job.setJarByClass(UserRiskAnalysis.class);
		job.setMapperClass(UserRiskAnalysisMapper.class);
//		job.setCombinerClass(UserRiskAnalysisReducer.class);
		job.setReducerClass(UserRiskAnalysisReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FloatWritable.class);
		//设置输入输出路径
		FileInputFormat.addInputPath(job,new Path(args[0]+"/RiskResult"));
		FileOutputFormat.setOutputPath(job, new Path(args[1]+"/UserRiskAnalysisResault"));
		//启动任务
//		WarnThreaed task = new WarnThreaed("");
//		Thread thread = new Thread(task);
//		thread.start();
		AnaMapper.initBlackList();
		
		if(job.waitForCompletion(true)){
			System.out.println("-----------------------------------------");
			System.out.println("Run finished");
		}
		else{
			System.out.println("-----------------------------------------");
			System.out.println("Run error");
		}
	}
	
	public static void run(String[] args) throws ClassNotFoundException, IOException, URISyntaxException, InterruptedException{
		TargetRisk.Run(args);
		Analysis(args);
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, URISyntaxException, InterruptedException{
		run(args);
	}

}
