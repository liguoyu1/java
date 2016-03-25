package FileOperator;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

@SuppressWarnings("unused")
public class HDFSFileWrite {
	public static void setPath(String path)
	{
		
	}
	public static void writeFile(String path,String context) throws IOException{
		Configuration conf = new Configuration();
//		conf.setBoolean("dfs.support.append", true);
		FileSystem fs = FileSystem.get(URI.create(path),conf);
		FSDataOutputStream out = null;
		out = fs.create(new Path(path));		
		out.writeBytes(context);
		out.close();
		fs.close();
	}
	
	public static void main(String[] args) throws IOException{
		for(int i = 0; i < 100 ; i ++){
			writeFile("hdfs://localhost:9000/user/hadoop/Output/TestRW/t1", "hello");
//			System.out.println(i);
		}
		System.out.println("End!");
	}

}
