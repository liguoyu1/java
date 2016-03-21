package RiskAnalysis;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.collections.map.HashedMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class TargetRisk {
	@SuppressWarnings("unchecked")
	static Map<String , String> Leakuser = new HashedMap();
	@SuppressWarnings("unchecked")
	static Map<String , String> Reguser = new HashedMap();
	@SuppressWarnings("unchecked")
	static Map<String , String> Fileuser = new HashedMap();
	@SuppressWarnings("unchecked")
	static Map<String , String> userRisk = new HashedMap();
 	@SuppressWarnings("deprecation")
 	public static String ReadFile(String filename) throws IOException{
		String dst = "hdfs://localhost:9000/user/hadoop/Output/ActionAnalysis/Compute/"+filename+"/part-r-00000";  
		Configuration conf = new Configuration();  
		FileSystem fs = FileSystem.get(URI.create(dst), conf);
		FSDataInputStream hdfsInStream = fs.open(new Path(dst));
		  
		String ioBuffer = null;
		String FileBuffer = null;
		ioBuffer = hdfsInStream.readLine();
		ioBuffer = hdfsInStream.readLine();
		while(ioBuffer != null){
			if(FileBuffer == null)
				FileBuffer = ioBuffer;
			else
				FileBuffer += "\n"+ioBuffer;
			ioBuffer = hdfsInStream.readLine();
		}
		return FileBuffer;
	}
	
	//漏洞风险
	public static void getLeakRisklevel() throws IOException{
		String buf = ReadFile("Risk");
		StringTokenizer itr = new StringTokenizer(buf,"\n");
		String risk = new String();
		String UserId = new String();
		while(itr.hasMoreTokens())
		{
			StringTokenizer ite = new StringTokenizer(itr.nextToken().toString(),"\t");
			for(int i = 0; i <= 4;i++)
			{
				ite.nextToken();
			}	
			UserId = ite.nextToken();
			risk = ite.nextToken();
			Leakuser.put(UserId,risk);
		}
	}
	
	//注册表风险
	public static void getRegRisklevel() throws IOException{
		String buf = ReadFile("Registe");
		StringTokenizer itr = new StringTokenizer(buf,"\n");
		String risk = new String();
		String UserId = new String();
		while(itr.hasMoreTokens())
		{
			StringTokenizer ite = new StringTokenizer(itr.nextToken().toString(),", ");
			int i;
			for(i = 0; i <= 4;i++)
			{
				ite.nextToken();
			}
			StringTokenizer iteval = new StringTokenizer(ite.nextToken().toString());
			UserId = iteval.nextToken();
			risk = iteval.nextToken();
			Reguser.put(UserId,risk);
		}
	}
	
	//文件行为风险
	public static void getFileRisklevel() throws IOException{
		String buf = ReadFile("file");
		StringTokenizer itr = new StringTokenizer(buf,"\n");
		String risk = new String();
		String UserId = new String();
		while(itr.hasMoreTokens())
		{
			StringTokenizer ite = new StringTokenizer(itr.nextToken().toString(),", ");
			int i;
			for(i = 0; i <= 4;i++)
			{
				ite.nextToken();
			}
			StringTokenizer iteval = new StringTokenizer(ite.nextToken().toString());
			UserId = iteval.nextToken();
			risk = iteval.nextToken();
			Fileuser.put(UserId,risk);
		}
	}

	//计算风险值并写入文件
	public static void calRisklevel() throws IOException{
		//读取各风险项目数据
		getLeakRisklevel();
		getRegRisklevel();
		getFileRisklevel();
		//计算终端风险
		String value;
		int leakR = 0;
		int RegR = 0;
		int FileR = 0;
		double level = 0;
		for(String key : Leakuser.keySet()){
			value = Leakuser.get(key);
			leakR = Integer.parseInt(value);
			if(Reguser.containsKey(key))
			{
				value = Reguser.get(key);
				RegR = Integer.parseInt(value);
				Reguser.remove(key);
			}
			if(Fileuser.containsKey(key))
			{
				value = Fileuser.get(key);
				FileR = Integer.parseInt(value);
				Fileuser.remove(key);
			}
			if(RegR + FileR == 0)
				level = leakR;
			else if(RegR == 0 || FileR == 0)
				level = leakR * 0.8 + (RegR + FileR)*0.2;
			else
				level = leakR*0.45+RegR*0.35+FileR*0.2;
			userRisk.put(key, String.valueOf(level));
	    }
		for(String key : Reguser.keySet()){
			value = Reguser.get(key);
			if(Fileuser.containsKey(key))
			{
				value = Fileuser.get(key);
				FileR = Integer.parseInt(value);
				Fileuser.remove(key);
			}
			if(FileR == 0)
				level = RegR;
			else
				level = (RegR+FileR)*0.5;
			
			userRisk.put(key, String.valueOf(level));
	    }
		for(String key : Fileuser.keySet()){
			value = Fileuser.get(key);
			userRisk.put(key, value);
	    }
	}
	
	//分析结果写入文件
	public static void WriteFile() throws IOException, URISyntaxException{
		
		//变量声明
		Configuration conf = new Configuration(); 
       conf.set("hadoop.job.ugi", "hadoop-user,hadoop-user"); 
        //仍然用FileSystem和HDFS打交道 
        //获得一个对应HDFS目标文件的文件系统
       long time = System.currentTimeMillis();
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
       Date da = new Date(time);
       String sDateTime = sdf.format(da);
       String FileName = "Resault-"+sDateTime;
       
       FileSystem fs = FileSystem.get(conf);
       FSDataOutputStream out = fs.create(new Path("hdfs://localhost:9000/user/hadoop/Input/RiskResult/"+FileName));

        //创建一个指向HDFS目标文件的输出流 
      for(String key:userRisk.keySet())
       {
    	  String str = userRisk.get(key);
    	  float level = Float.parseFloat(str);
    	  String record = key + " ,"+str+"\n";
    	  if(level != 0){
	    	  try{
	    		  byte[] buffer = record.getBytes();
	    		  out.write(buffer);
	    	  }
	    	  catch(Exception e){
	    		  System.out.println(e.getMessage());
	    		  e.printStackTrace();
	    		  break;
	    	  }
    	  }
       }		
	}
	
	public static void Run(String[] args) throws IOException, URISyntaxException{
		calRisklevel();
		WriteFile();
	}
	
	//main函数
	public static void main(String[] args) throws IOException, URISyntaxException{
		calRisklevel();
		WriteFile();
		System.out.println("System End!");
	}

}
