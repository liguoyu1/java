package server.serverTest;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JsonResourceList {
	@XmlElement
	ArrayList<JsonResource> relist;
	
	public JsonResourceList(){
		this.relist = new ArrayList<JsonResource>();
	}
	
	public JsonResourceList(ArrayList<JsonResource> relist){
		this.relist = relist;
	}
	
	public void addResource(JsonResource re){
		this.relist.add(re);
	}

	public ArrayList<JsonResource> getJsonResourceList(){
		return this.relist;
	}
}
