package Docker;

import java.security.NoSuchAlgorithmException;

import dealdata.MD5Utils;
import dealdata.MyRandom;
import server.serverTest.JsonDocker;

public class Docker {
	public String dockerMd5;
	public String docker;
	public String Path;
	public String time;
	
	public Docker(JsonDocker docker){
		this.dockerMd5 = docker.getDockerMd5();
		this.docker = docker.getDocker();
		this.Path = docker.getPath();
		this.time = docker.getTime();
	}
	
	public Docker(){
		
	}
	
	public Docker(String md5,String docker,String path,String time){
		this.dockerMd5 = md5;
		this.docker = docker;
		this.Path = path;
		this.time = time;
	}
	
 	public void setDockerMd5(String md5){
		this.dockerMd5 = md5;
	}
	
	public void setDocker(String docker){
		this.docker = docker;
	}
	
	public void setPath(String Path){
		this.Path = Path;
	}
	
	public void setTime(String time){
		this.time = time;
	}
	
	public String getDockerMd5(){
		return this.dockerMd5;
	}
	
	public String getDocker(){
		return this.docker;
	}
	
	public String getPath(){
		return this.Path;
	}
	
	public String getTime(){
		return this.time;
	}

	public void Init() throws NoSuchAlgorithmException{
		MyRandom myrand = new MyRandom();
		this.dockerMd5 = MD5Utils.getMD5Utils(myrand.randString(8));
		this.docker = myrand.randString(10);
		this.Path = "/tmp/registry";
		this.time = myrand.randDateTime();
	}
	
	public void show(){
		System.out.println("MD5:"+this.dockerMd5+"\n\rdocker"+this.docker+"\n\rPath:"+this.Path+"\n\rtime:"+this.time);
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException{
		Docker docker = new Docker();
		docker.Init();
		docker.show();
		
		
	}
}