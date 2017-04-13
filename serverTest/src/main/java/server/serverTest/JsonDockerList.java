package server.serverTest;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JsonDockerList {
	@XmlElement
	ArrayList<JsonDocker> jDockerList;
	
	public JsonDockerList(){
		this.jDockerList = new ArrayList<JsonDocker>();
	}
	
	public JsonDockerList(JsonDockerList jsonDockerList){
		this.jDockerList = jsonDockerList.getJsonResourceList();
		
	}
	
	public ArrayList<JsonDocker> getJsonResourceList(){
		return this.jDockerList;
	}
	
	public void addDocker(JsonDocker docker){
		this.jDockerList.add(docker);
	}

	public void show() {
		// TODO Auto-generated method stub
		System.out.println();
		jDockerList.get(0).show();
		
	}

}
