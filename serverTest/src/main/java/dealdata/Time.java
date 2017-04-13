package dealdata;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
	
	/**
	 * 获取当前时间   yyyy-MM-dd HH:mm:ss
	 * @return  返回时间字符串
	 */
	public static  String getCurrentTimeS(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long times = System.currentTimeMillis();
		Date date = new Date(times);
		String tim = sdf.format(date);
		return tim;
	}
	
	/**
	 * 获取当前日期   yyyy-MM-dd
	 * @return 返回日期字符串
	 */
	public static String getCurrnetDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long times = System.currentTimeMillis();
		Date date = new Date(times);
		String tim = sdf.format(date);
		return tim;
	}
	
	public static long getCurrentTimeL(){
		return System.currentTimeMillis();
	}
	
}
