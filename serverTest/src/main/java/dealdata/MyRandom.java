/**
 * @author lgy
 * 
 */
package dealdata;

import java.text.SimpleDateFormat;
import java.util.Random;

public class MyRandom {
	/**
	 * 默认生成0,1两个整数之一
	 * @return
	 */
	public  int randInt(){
		Random rand = new Random();
//		Random rand2 = new Random();
		return rand.nextInt(2);
	}
	
	/**
	 * 生成 0---endInt 内的整数
	 * @param endInt
	 * @return
	 */
	public  int randInt(int endInt){
		Random rand = new Random();
//		rand.nextInt();
		return rand.nextInt(endInt);
	}
	
	/**
	 * 生成 startInt ---- endInt 范围内的整数
	 * @param startInt   起始
	 * @param endInt     上限
	 * @return
	 */
	public  int randInt(int startInt,int endInt){
		Random rand = new Random();
//		rand.ints(1, startInt, endInt);
		return rand.ints(1, startInt, endInt).toArray()[0];
	}
	
	/**
	 * 生成小数
	 */
	//0----1范围内
	public  float randFloat(){
		Random rand = new Random(System.currentTimeMillis());
		return rand.nextFloat();
	}
	
	//0 ------ endFloat范围内
	public  double randFloat(double endFloat){
		Random rand = new Random();
		return rand.doubles(1,0.0, endFloat).toArray()[0];
	}
	
	// startFloat ------ endFloat 范围内
	public  double randFloat(double startFloat,double endFloat){
		Random rand = new Random();
		return rand.doubles(1,startFloat, endFloat).toArray()[0];
	}
	
	/**
	 * 生成指定长度的字符串
	 * @param length   字符串长度
	 * @return
	 */
	public  String randString(int length){
		if(length <= 0){
			return "";
		}
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";  
	    Random random = new Random();  
	    StringBuffer sb = new StringBuffer();  
	    for (int i = 0; i < length; i++) {
	    	int number = 0;
	    	if(i < length/3){
	    		number = random.nextInt(base.length()-10); 
	    	}
	    	else{
	    		number = random.nextInt(base.length());  
	    	}
	        sb.append(base.charAt(number));  
	    }  
	    return sb.toString();   
	}
	
	public  String randDateTime(){
		int days = randInt(1,300);
		long limitTime = System.currentTimeMillis();
		limitTime += (long)days*1000*60*60*24;
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return time.format(limitTime);
	}
	
	public static void main(String[] args){
		MyRandom myrand = new MyRandom();
		System.out.println(myrand.randFloat());
		System.out.println(myrand.randFloat(9.0));
		System.out.println(myrand.randFloat(12.0,19.0));
		
		System.out.println(myrand.randInt());
		System.out.println(myrand.randInt(10));
		System.out.println(myrand.randInt(11,20));
		
		System.out.println(myrand.randString(15));
		System.out.println(myrand.randDateTime());
	}
}
