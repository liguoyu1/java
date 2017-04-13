package PaasListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;
import CloudR.Resource;
import Config.SystemConfig;
import Mysql.Delete;
import Mysql.Insert;
import Mysql.Update;
import dealdata.FileOperate;

public class SlaverListener {
	public String urllatter = SystemConfig.slaveurllatter;
	
	/**
	 * 读page的内容
	 * @param rd
	 * @return 
	 * @throws IOException
	 */
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
	
	/**
	 * 获取url page 获取json数据对象
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
//		System.out.println(url);
		InputStream is = null;
		try{
			is = new URL(url).openStream();
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	    try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    	return null;
	    }
	    finally {
	    	is.close();
	    }
	}
	
	/**
	 * slave节点集的资源收集
	 * 当前能够收集到
	 * cpu 的总数和已使用的cpu数,
	 * mem 内存的size和已使用的内存size,
	 * disk 磁盘的大小和磁盘已使用size,
	 * taskNumber(并发任务数)，
	 * exectors_Running(执行器的个数)
	 * @throws JSONException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void slaveListen() throws JSONException, IOException, ClassNotFoundException{
		Delete delete = new Delete();
		delete.deleteAllResource();
		FileOperate fp = new FileOperate();
		ArrayList<String> ips = fp.readSlaveIPFile();
		Insert insert = null;
		Update update = null;
		int i = 0;
		while(true){
			i++;
//			System.out.println("第"+i+"次收集！"+Time.getCurrnetDate()+" "+Time.getCurrentTimeS());
			if(i >= 100){
				break;
			}
			try {
				insert = new Insert();
				update = new Update();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(Iterator<String> iter = ips.iterator();iter.hasNext();){
				String ip = iter.next();
				
				try {
					JSONObject jsonO = readJsonFromUrl("http://"+ip+SystemConfig.slaveurllatter);
					if(jsonO == null)
						continue;
					Resource re = new Resource(jsonO,ip);
					
					if(i <= 1){
						insert.insertResource(re);
					}
					else{
						update.updateResource(re);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
    
	public static void main(String[] aegs) throws ClassNotFoundException{
//		slaverListener sL = new slaverListener();
		try {
			System.out.println(SlaverListener.readJsonFromUrl("http://172.29.152.183:5051/metrics/snapshot").toString());
			SlaverListener.slaveListen();	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
