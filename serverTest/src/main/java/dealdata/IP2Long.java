package dealdata;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

/**
 * @author lgy
 *
 * 2016-9-14
 */
public class IP2Long {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String ipFrom = "192.168.1.1";
		String ipTo = "192.168.2.10";
		
		String ip = "192.168.2.1";
		
		if(ipToNumber(ip)>ipToNumber(ipFrom) &&  ipToNumber(ip)<ipToNumber(ipTo)){
			System.out.println("ip在范围内");
		}else{
			System.out.println("ip不在范围内");
		}
		
	}
	
	public static Long ipToNumber(String ip) {    
		Long ips = 0L; 
		String[] numbers = ip.split("\\.");
		//等价上面
		for (int i = 0; i < 4; ++i) {
			ips = ips << 8 | Integer.parseInt(numbers[i]);
		}
		return ips;   
	}    
	  
	public static String numberToIp(long number) {    
		//等价上面
		String ip = "";
		for (int i = 3; i >= 0; i--) {
			ip  += String.valueOf((number & 0xff));
			if(i != 0){
				ip += ".";
			}
			number = number >> 8;
		}
		return ip;	    
	}
	
	/**
	 * @param IP
     * @return
     */
    public static String GetAddressByIp(String IP){
        String resout = "";
        try{
         String str = getJsonContent("http://ip.taobao.com/service/getIpInfo.php?ip="+IP);
         
//         System.out.println(str);
          
         JSONObject obj = new JSONObject(str);
         JSONObject obj2 =  (JSONObject) obj.get("data");
         String code = String.valueOf(obj.get("code"));
         if(code.equals("0")){
          
             resout =  (String) obj2.get("city");
         }else{
             resout =  "IP地址有误";
         }
        }catch(Exception e){
             
            e.printStackTrace();
            resout = "获取IP地址异常："+e.getMessage();
        }
        return resout;
          
    }
    
    public static String getJsonContent(String urlStr)
    {
        try
        {// 获取HttpURLConnection连接对象
            URL url = new URL(urlStr);
            HttpURLConnection httpConn = (HttpURLConnection) url
                    .openConnection();
            // 设置连接属性
            httpConn.setConnectTimeout(3000);
            httpConn.setDoInput(true);
            httpConn.setRequestMethod("GET");
            // 获取相应码
            int respCode = httpConn.getResponseCode();
            if (respCode == 200)
            {
                return ConvertStream2Json(httpConn.getInputStream());
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return "";
    }

	private static String ConvertStream2Json(InputStream inputStream)
    {
        String jsonStr = "";
        // ByteArrayOutputStream相当于内存输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        // 将输入流转移到内存输出流中
        try
        {
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1)
            {
                out.write(buffer, 0, len);
            }
            // 将内存流转换为字符串
            jsonStr = new String(out.toByteArray());
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonStr;
    }
}

