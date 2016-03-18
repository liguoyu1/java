package RiskAnalysis;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class TerminalRiskAnalysis {
	static String HadoopFilePathInput = new String();
	static String HadoopFilePathOutPut = new String();
	@SuppressWarnings("deprecation")
	public static String ReadScanFile() throws IOException{
		String dst = "hdfs://127.0.0.1:9000/user/hadoop/Input//SystemLeak/Compute/SystemLeakB.txt";  
		Configuration conf = new Configuration();  
		FileSystem fs = FileSystem.get(URI.create(dst), conf);
		FSDataInputStream hdfsInStream = fs.open(new Path(dst));
		  
		String ioBuffer = null;
		String FileBuffer = null;
		ioBuffer = hdfsInStream.readLine();
		while(ioBuffer != null){
			if(FileBuffer == null)
				FileBuffer = ioBuffer;
			else
				FileBuffer += "\n"+ioBuffer;
			ioBuffer = hdfsInStream.readLine();
		}
		hdfsInStream.close();
		fs.close();
		return FileBuffer;
	}
	public static class TerminalRiskAnalysisMapper extends Mapper<Object, Text, Text, IntWritable>{
		//static String LeakBuffer = null;
		static String[] leakItem = new String[1000];
		String[] items = new String[10];
		static int num = 0;
		final IntWritable one = new IntWritable(1);
		public static void SetLeak(String item)
		{
			StringTokenizer itr = new StringTokenizer(item,"\n");
			int i = 0;
			while(itr.hasMoreTokens())
			{
				leakItem[i] = itr.nextToken().toString();
				
				//System.out.println(leakItem[i]);
				i++;
			}
			num = i;
		}
		 
			
		public void ExacWord(String record){
			StringTokenizer itr = new StringTokenizer(record,"\t");
			int i = 0;
			while(itr.hasMoreTokens())
			{
				items[i] = itr.nextToken().toString();
				i++;
			}
		}
		
		public void map(Object key,Text value,Context context){
			ExacWord(value.toString());
		//	System.out.println(leakItem[0]);
			for(int i = 0;i < num;i++)
			{
				if(items[0].length()  <= 3)
					continue;
				if(leakItem[i].contains(items[0]))
				{
					try {
						context.write(new Text(leakItem[i].toString()+"\t"+items[4]), one);
					} catch (IOException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static class TerminalRiskAnalysisReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
		public void reduce(Text key,Iterable<IntWritable> value,Context context) throws IOException, InterruptedException{
			int sum = 0;
			for(IntWritable val:value)
			{
				sum += val.get();
			}
			IntWritable res = new IntWritable(sum);
			context.write(key,res);
		}
	}
	
	//类run函数
		@SuppressWarnings("deprecation")
		public static void Run(String[] args)throws IOException,InterruptedException, ClassNotFoundException, URISyntaxException{
			if(args.length != 2){
				System.out.println("Please enter <Input> <Output>");
				System.exit(0);
			}
			
			HadoopFilePathInput = args[0];
			HadoopFilePathOutPut = args[1];
			
			
			Configuration conf = new Configuration();
			Job job = new Job(conf,"File action analysis");
			FileSystem fs = FileSystem.get(conf);
			fs.delete(new Path(args[1]+"/ActionAnalysis/Compute"));
			
			//设置job
			job.setJarByClass(TerminalRiskAnalysis.class);
			job.setMapperClass(TerminalRiskAnalysisMapper.class);
			job.setReducerClass(TerminalRiskAnalysisReducer.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);
			//设置输入输出路径]
			FileInputFormat.addInputPath(job,new Path(args[0]+"/SystemScanResult/Compute"));
			FileOutputFormat.setOutputPath(job, new Path(args[1]+"/ActionAnalysis/Compute/Risk"));
			
			String LeakBuffer = null;
			LeakBuffer = ReadScanFile();
			TerminalRiskAnalysisMapper.SetLeak(LeakBuffer);
			
			//启动任务
			if(job.waitForCompletion(true)){
				System.out.println("-----------------------------------------");
				System.out.println("Run finished");
			}
			else{
				System.out.println("-----------------------------------------");
				System.out.println("Run error");
			}
		}
		
		public static void main(String[] args)throws IOException,InterruptedException, ClassNotFoundException, URISyntaxException
		{
			Run(args);
		}

}
