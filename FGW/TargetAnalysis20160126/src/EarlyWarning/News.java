package EarlyWarning;

//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStreamReader;

public class News {
	
	public News(){
		
	}
	
//	public News()
	
	
	public static boolean sendMessage(String Phone,String Message) throws IOException, InterruptedException{
		//System.out.println(Phone);
		String[] Order = new String[3];
		Order[0] = "/bin/sh";
		Order[1] = "-c";
		Order[2] = "sudo echo -n \""+Message+"\" | gnokii --sendsms "+Phone+" > /dev/null";
		
		System.out.println(Order[2]);
		
		Runtime rt = Runtime.getRuntime();  
		rt.exec(Order);
		//rt.getLocalizedInputStream("liguoyu");
        //执行  
		
        Process pro = null;  
        pro = rt.exec(Order);
        pro.waitFor();
        //System.out.println(Phone);
        return true;  
        
//        BufferedReader read = new BufferedReader(new InputStreamReader(pro.getInputStream()));  
//        String line = null;  
//        while((line = read.readLine())!=null){  
//            System.out.println(line);  
//        }
	}
	
	public static void main(String[] args)throws IOException, InterruptedException{
//		sendMessage("15615813042","您的设备“a8d41bbf06d55176bac1e5d99598216b”，存在危险，请及时使用杀毒软件查杀！以确保使用安全!");
		sendMessage(new String("15615813042"),"hello");
		
		
	}

}
