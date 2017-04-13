package server.serverTest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import Docker.Docker;

@XmlRootElement
public class JsonDocker {
	@XmlElement
	public String dockerMd5;
	@XmlElement
	public String docker;
	@XmlElement
	public String Path;
	@XmlElement
	public String time;
	
	public JsonDocker(){}
	
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
	
	public JsonDocker(Docker docker){
		this.dockerMd5 = docker.getDockerMd5();
		this.docker = docker.getDocker();
		this.Path = docker.getPath();
		this.time = docker.getTime();
	}

	
	public void show() {
		// TODO Auto-generated method stub
		System.out.println("docker Md5 :"+this.dockerMd5);
		System.out.println("docker :"+ this.docker);
//		System.out.println("");
		
	}
}
