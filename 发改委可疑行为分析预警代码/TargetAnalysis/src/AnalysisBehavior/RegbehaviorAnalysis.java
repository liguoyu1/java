package AnalysisBehavior;
 
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.lib.MultipleOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Progressable;

public class RegbehaviorAnalysis {
	//注册表分析 map 类
	public static class RegAnaMap extends Mapper<Object, Text, Text, IntWritable>{
		private static final String[] Item = new String[6];
	
		private final static IntWritable one = new IntWritable(1);
		//提取记录中的数据项
		public void ItemExtractFrom(String Recode,String[] Item)
		{
			StringTokenizer itr = new StringTokenizer(Recode,", ");
			int i = 0;
			
			while(itr.hasMoreTokens()){
				Item[i] = itr.nextToken().toString();
				//Item[i] = Item[i].substring(0, Item[i].length()-1);
				i++;
			}
		}
		
		public void map(Object key,Text value,Context context)throws IOException,InterruptedException{
			//System.out.print(value.toString()+"\n");
			ItemExtractFrom(value.toString(),Item);
			context.write(new Text(Item[3]+" "+Item[4]),one);
		}
	}

	//注册表分析 reduce 类one
	public static class RegAnaReduce extends Reducer<Text, IntWritable, Text, IntWritable>{
//		@SuppressWarnings("null")
		 
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
	
	public static class MyOutpurFormatHadoop extends MultipleOutputFormat<Text, NullWritable>
	{
		 protected String generateFileNameForKeyValue(Text key,
                NullWritable value, String name) {
            //String sp[] = key.toString().split(",");
            String filename;
            filename="RegAnalysisR";
            return filename;
        }

		@Override
		protected org.apache.hadoop.mapred.RecordWriter<Text, NullWritable> getBaseRecordWriter(
				FileSystem arg0, JobConf arg1, String arg2, Progressable arg3)
				throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	//类主函数
	@SuppressWarnings("deprecation")
	public static void Run(String[] args)throws IOException,InterruptedException, ClassNotFoundException, URISyntaxException{
		if(args.length != 2){
			System.out.println("Please enter <Input> <Output>");
			System.exit(0);
		}
	
		Configuration conf = new Configuration();
		Job job = new Job(conf,"Reg analysis");
		URI url = new URI("hdfs://localhost:9000");
		FileSystem fs = FileSystem.get(url,conf);
		fs.delete(new Path(args[1]+"/RegAnalysisResult"));
	
		//设置job
		job.setJarByClass(RegbehaviorAnalysis.class);
		job.setMapperClass(RegAnaMap.class);
		job.setReducerClass(RegAnaReduce.class);
		//job.setOutputFormatClass(MyOutpurFormatHadoop.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		//设置输入输出路径]
		System.out.print(new Path(args[0]));
		FileInputFormat.addInputPath(job,new Path(args[0]+"/MalevolenceBehaviorFeature/RegP"));
		FileOutputFormat.setOutputPath(job, new Path(args[1]+"/RegAnalysisResult"));
		
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
