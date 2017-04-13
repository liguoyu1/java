package server.serverTest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import CloudR.Resource;
import JobScheduler.Job;
import dealdata.Time;

@XmlRootElement
public class JsonJobScheduler {
	@XmlElement
	JsonJob jsonjob;
	@XmlElement
	JsonResource jsonresource;
	@XmlElement
	String curtime;
	
	public JsonJobScheduler(){}
	
	public JsonJobScheduler(Job job,Resource re){
		this.jsonjob = new JsonJob(job);
		this.jsonresource = new JsonResource(re);
		this.curtime = Time.getCurrentTimeS();
	}
	
	public JsonJob getJsonJob(){
		return this.jsonjob;
	}
	
	public JsonResource getJsonResource(){
		return this.jsonresource;
	} 
	
	public void setJsonJob(Job job){
		this.jsonjob = new JsonJob(job);
	}
	
	public void setJsonResource(Resource resource){
		this.jsonresource = new JsonResource(resource);
	}
	
	@SuppressWarnings("unused")
	private void updatetime(){
		this.curtime = Time.getCurrentTimeS();
	}
	
}
