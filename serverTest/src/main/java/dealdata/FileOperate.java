/**
 * @author lgy
 * 
 */
package dealdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import Config.SystemConfig;

public class FileOperate {
	public static String slaveIpFile = Config.SystemConfig.slaveIpFile;
    
	public String readFile(String fileName) {
    	String output = "";
        File file = new File(fileName);
        if(file.exists()){
        	if(file.isFile()){
        		try{
        			@SuppressWarnings("resource")
					BufferedReader input = new BufferedReader (new FileReader(file));
        			StringBuffer buffer = new StringBuffer();
        			String text;
        			while((text = input.readLine()) != null)
        				buffer.append(text);
        			output = buffer.toString();         
        		}
        		catch(IOException ioException){
        			System.err.println("File Error!");
        		}
        	}
        	else if(file.isDirectory()){
        		String[] dir = file.list();
        		output += "Directory contents:/n";
             
        		for(int i=0; i<dir.length; i++){
        			output += dir[i] +"/n";
        		}
        	}
        }
        else{
        	System.err.println("Does not exist!");
        }
        return output;
    }
    
    public ArrayList<String> readSlaveIPFile(){
        @SuppressWarnings("static-access")
		File file = new File(this.slaveIpFile);
        ArrayList<String> ip = null;
        if(file.exists()){
        	if(file.isFile()){
        		try{
					@SuppressWarnings("resource")
					BufferedReader input = new BufferedReader (new FileReader(file));
        			String text;
        			ip = new ArrayList<String>();
        			while((text = input.readLine()) != null){
        				ip.add(text);   
        			}
        		}
        		catch(IOException ioException){
        			System.err.println("File Error!");
        		}
        	}
        }
        else{
        	System.err.println(SystemConfig.slaveIpFile+" Does not exist!");
        }
        return ip;
    }
    
    public static void main(String[] args) {  
        FileOperate fp = new FileOperate();
        System.out.print(fp.readSlaveIPFile().toString());
    }  
}
