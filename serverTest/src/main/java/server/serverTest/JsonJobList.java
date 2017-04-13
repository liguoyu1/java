package server.serverTest;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JsonJobList {
	@XmlElement
	ArrayList<JsonJob> list = null;
	
	public JsonJobList(){
		this.list = new ArrayList<JsonJob>();
	}
	
	public void setJsonJobList(ArrayList<JsonJob> jlist){
		this.list = jlist;
	}
	
	public void addJsonJob(JsonJob job){
		this.list.add(job);
	}
	
	public ArrayList<JsonJob> getJsonJobList(){
		return this.list;
	}
	
}
