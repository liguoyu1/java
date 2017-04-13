package dealdata;

import java.sql.Timestamp;
import java.text.*;
import java.util.Date;

public class PaasDate {
	 /**
	  * 获取现在时间
	  * 
	  * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	  */
	 public static Timestamp getNowDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH(hh):mm:ss");
		long times = System.currentTimeMillis();
//		System.out.println(times);
		Date date = new Date(times);
		
		String tim = sdf.format(date);
//		String datatime = tim.substring(4, 17)+tim.substring(21,27);
//		System.out.println(datatime);
		Timestamp ts = Timestamp.valueOf(tim);
		return ts;
	}
	 
	public static void main(String[] args){
		System.out.println(PaasDate.getNowDate());
	}
}
