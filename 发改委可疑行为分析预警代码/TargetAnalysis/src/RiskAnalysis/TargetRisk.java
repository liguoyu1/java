package RiskAnalysis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class TargetRisk {
	static String[] risk = new String[1000];
	static String[] RegRisk = new String[1000];
	static String[] fileRisk = new String[1000];
	static String[] UserId = new String[1000];
	static int LeakNum = 0;
	static int RegNum = 0;
	static int FileNum = 0;
	static int RegRiskSum = 0;
	static int FileRiskSum = 0;
	
 	@SuppressWarnings("deprecation")
	public static String ReadFile(String filename) throws IOException{
		String dst = "hdfs://127.0.0.1:9000/user/hadoop/Output/ActionAnalysis/Compute/"+filename+"/part-r-00000";  
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
	@SuppressWarnings("deprecation")
	public static void getLeakRisklevel() throws IOException{
		//String buf = ReadFile("Risk");
		//read file from hdfs
		String dst = "hdfs://127.0.0.1:9000/user/hadoop/Input/SystemLeak/Compute/SystemLeakB.txt";  
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
		//return FileBuffer;
		StringTokenizer itr = new StringTokenizer(FileBuffer,"\n");
		int k = 0;
		while(itr.hasMoreTokens())
		{
			StringTokenizer ite = new StringTokenizer(itr.nextToken().toString(),"\t");
			for(int i = 0; i <= 3;i++)
			{
				ite.nextToken();
			}
			
			UserId[k] = ite.nextToken();
			//System.out.println(UserId[k]);
			k++;
		}
		LeakNum = k;
	}
	
	//注册表风险
	public static void getRegRisklevel() throws IOException{
		String buf = ReadFile("Registe");
		StringTokenizer itr = new StringTokenizer(buf,"\n");
		int k = 0;
		while(itr.hasMoreTokens())
		{
			//System.out.println(itr.nextToken());
			StringTokenizer ite = new StringTokenizer(itr.nextToken().toString(),", ");
			int i;
			for(i = 0; i <= 3;i++)
			{
				ite.nextToken();
			}
			StringTokenizer iteval = new StringTokenizer(ite.nextToken().toString());
			iteval.nextToken();
			RegRisk[k] = iteval.nextToken();
			RegRiskSum += Integer.parseInt(RegRisk[k]);
			k++;
		}
		RegNum = k;
	}
	
	//文件行为风险
	public static void getFileRisklevel() throws IOException{
		String buf = ReadFile("file");
		StringTokenizer itr = new StringTokenizer(buf,"\n");
		int k = 0;
		while(itr.hasMoreTokens())
		{
			StringTokenizer ite = new StringTokenizer(itr.nextToken().toString(),", ");
			int i;
			for(i = 0; i <= 3;i++)
			{
				ite.nextToken();
			}
			StringTokenizer iteval = new StringTokenizer(ite.nextToken().toString());
			iteval.nextToken();
			RegRisk[k] = iteval.nextToken();
			//System.out.println(RegRisk[k]);
			FileRiskSum += Integer.parseInt(RegRisk[k]);
			k++;
		}
		FileNum = k;
	}

	//计算风险值并写入文件
	public static void calRisklevel() throws IOException{
		//读取各风险项目数据
		getLeakRisklevel();
		getRegRisklevel();
		getFileRisklevel();
		
//		String buffer = null;
//		File file=new File("/home/temana.txt");
//		if(!file.exists())
//			file.createNewFile();
//		FileOutputStream out=new FileOutputStream(file,true);          
//		//计算终端风险
//		int i;
//		for(i = 0;i <= LeakNum;i++)
//		{
//			double level = Integer.parseInt(risk[i])*0.8 + FileRiskSum/100 * 0.05 + RegRiskSum/100 * 0.15;
//			int lev = (int)level;
//			buffer = UserId[i];
//		
////			risk[k] = ite.nextToken();
////			System.out.println(risk[k]);//			String num = null;
//			buffer += "\t"+Integer.toString(lev);
//			System.out.println(buffer);
//			out.write(buffer.getBytes("utf-8"));
//		}
//		out.close();
		
		
	}
	
	//main函数
	public static void main(String[] args) throws IOException{
		calRisklevel();
	}

}
