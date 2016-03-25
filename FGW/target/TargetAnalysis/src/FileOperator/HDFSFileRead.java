package FileOperator;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HDFSFileRead {
	@SuppressWarnings("deprecation")
	public static ArrayList<String> readFileAll(String path) throws IOException{
		ArrayList<String> filecontent = new ArrayList<String>();
		Configuration conf = new Configuration();  
		FileSystem fs = FileSystem.get(URI.create(path), conf);
		FSDataInputStream hdfsInStream = fs.open(new Path(path));
		  
		String ioBuffer = null;
//		String FileBuffer = null;
		ioBuffer = hdfsInStream.readLine();
		while(ioBuffer != null)
		{
			filecontent.add(ioBuffer);
			ioBuffer = hdfsInStream.readLine();
		}
		return filecontent;
	}
	
	public static void main(String[] args) throws IOException{
//		System.out.println("请输入文件名:");
		String filename = new String("hdfs://localhost:9000/user/hadoop/Input/Blacklist/Url/urlblacklist.txt");
//		Scanner sc = new Scanner(System.in);
//		filename = sc.nextLine();
		System.out.println("Read file "+filename);
		ArrayList<String> str = new ArrayList<String>();
		str = readFileAll(filename);
		for(Iterator<String> iter = str.iterator();iter.hasNext();)
		{
			System.out.println(iter.next());
		}
		System.out.println("End!");
	}
}
