package AnalysisBehavior;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Fileanalysis {
	public static class FileAnaMap extends Mapper<Object, Text, Text, IntWritable>{
		final IntWritable one = new IntWritable(1);
		Text Record = new Text();
		String[] Item = new String[7];
		//提取记录中的数据项
		public void ItemExtractFrom(String value ,String[] Item)
		{
			StringTokenizer itr = new StringTokenizer(value,",");
			int i = 0;
			//System.out.println(value);
			while(itr.hasMoreTokens()){
				Item[i] = itr.nextToken().toString();
				//System.out.println(Item[i]);
				Item[i] = Item[i].substring(0, Item[i].length());
				i++;
			}
		}
		public void map(Object key,Text value,Context context) throws IOException,InterruptedException{	
			//System.out.println(value.toString());
			ItemExtractFrom(value.toString(),Item);
			context.write(new Text(Item[1]+" "+Item[4]),one);
		}
	}
	//文件分析 reduce 类one
	public static class FileAnaReduce extends Reducer<Text, IntWritable, Text, IntWritable>{
		private IntWritable result = new IntWritable();
		
		public void reduce(Text key, Iterable<IntWritable> values,Context context) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			if(sum >= 20){
				result.set(sum);
				context.write(key, result);
			}
		}
	}
	
	//类主函数
		@SuppressWarnings("deprecation")
		public static void RunServer(String[] args)throws IOException,InterruptedException, ClassNotFoundException, URISyntaxException{
			if(args.length != 2){
				System.out.println("Please enter <Input> <Output>");
				System.exit(0);
			}
		
			Configuration conf = new Configuration();
			Job job = new Job(conf,"File analysis");
			URI url = new URI("hdfs://localhost:9000");
			FileSystem fs = FileSystem.get(url,conf);
			fs.delete(new Path(args[1]+"/FileAnalysisResult"));
		
			//设置job
			job.setJarByClass(Fileanalysis.class);
			job.setMapperClass(FileAnaMap.class);
			job.setReducerClass(FileAnaReduce.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);
			//设置输入输出路径]
			FileInputFormat.addInputPath(job,new Path(args[0]+"/MalevolenceBehaviorFeature/FileP"));
			FileOutputFormat.setOutputPath(job, new Path(args[1]+"/FileAnalysisResult"));
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
			RunServer(args);
		}
		
}
