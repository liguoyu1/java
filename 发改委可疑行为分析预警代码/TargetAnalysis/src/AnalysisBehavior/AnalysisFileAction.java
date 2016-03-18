package AnalysisBehavior;

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

public class AnalysisFileAction {
	static String HadoopFilePathInput = new String();
	static String HadoopFilePathOutPut = new String();
//	static{
//		URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
//	}
	
	public static class FileActionAnaMapper extends Mapper<Object, Text, Text, IntWritable>{
		final IntWritable one = new IntWritable(1);
		private static final String[] Item = new String[6];
		//提取分析注册表行为特征数据
		@SuppressWarnings("deprecation")
		public String ReadAnalysisFile() throws IOException
		{
			String dst = "hdfs://127.0.0.1:9000/user/hadoop/Output/FileAnalysisResult/part-r-00000";  
			Configuration conf = new Configuration();  
			FileSystem fs = FileSystem.get(URI.create(dst), conf);
			FSDataInputStream hdfsInStream = fs.open(new Path(dst));
			  
			String ioBuffer = null;
			String FileBuffer = null;
			ioBuffer = hdfsInStream.readLine();
			while(ioBuffer != null){
				if(FileBuffer == null)
					FileBuffer += ioBuffer;
				else
					FileBuffer += "\n"+ioBuffer;
				ioBuffer = hdfsInStream.readLine();
			}
			hdfsInStream.close();
			fs.close();
			return FileBuffer;
			
		}
		
		public void ExactWord(String Record,String[] Item)
		{
			StringTokenizer itr = new StringTokenizer(Record,",");
			int i = 0;
			while(itr.hasMoreTokens()){
				Item[i] = itr.nextToken().toString();
				i++;
			}
		}
	
		public void map(Object key,Text value,Context context) throws IOException,InterruptedException{
			String ResultBuffer = ReadAnalysisFile();
			System.out.println(value.toString()+" end");
			if(value.toString() != "" || value != null)
			{
				ExactWord(value.toString(),Item);
				//特征匹配
				if(ResultBuffer.contains(Item[1]) && ResultBuffer.contains(Item[4]))
				{
					//System.out.println("find risk operator!");
					context.write(value, one);
				}
			}
		}
	}
	
	public static class FileActionAnaReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
		public void reduce(Text key,Iterable<IntWritable> values,Context context) throws IOException,InterruptedException{
			int sum = 0;
			for(IntWritable val : values)
			{
				sum += val.get();
			}
			IntWritable res = new IntWritable(sum);
			//System.out.println(key);
			context.write(key, res);
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
		fs.delete(new Path(args[1]+"/ActionAnalysis/Compute/file"));
		
		//设置job
		job.setJarByClass(AnalysisFileAction.class);
		job.setMapperClass(FileActionAnaMapper.class);
		job.setReducerClass(FileActionAnaReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		//设置输入输出路径]
		FileInputFormat.addInputPath(job,new Path(args[0]+"/Action/computer/File"));
		FileOutputFormat.setOutputPath(job, new Path(args[1]+"/ActionAnalysis/Compute/file"));
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

