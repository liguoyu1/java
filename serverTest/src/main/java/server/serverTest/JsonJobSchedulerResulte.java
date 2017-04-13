package server.serverTest;

import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import JobScheduler.JobScheduler;

@XmlRootElement
public class JsonJobSchedulerResulte {
	@XmlElement
	public ArrayList<JsonJob> unfinishedlist;
	@XmlElement
	public ArrayList<JsonJobScheduler> schedulerlist;
	
	public JsonJobSchedulerResulte(){}
	
	/**
	 * 将分析的无法完成的任务
	 */
	public void setUnFinishedlist(ArrayList<JsonJob> list){
		this.unfinishedlist = list;
	}
	
	/**
	 * 调度队列和资源组合
	 * @param list
	 */
	public void setSchedulerJobList(ArrayList<JsonJobScheduler> list){
		this.schedulerlist = list;
	}

	public void setSchedulerJobLists(ArrayList<JobScheduler> list){
		this.schedulerlist = new ArrayList<JsonJobScheduler>();
		for(Iterator<JobScheduler> iter = list.iterator();iter.hasNext();){
			JobScheduler jobS = iter.next();
			this.schedulerlist.add(new JsonJobScheduler(jobS.getJob(),jobS.getResource()));
		}
	}
	
	public ArrayList<JsonJob> getUnFinishedlist(){
		return this.unfinishedlist;
	}
	
	public ArrayList<JsonJobScheduler> getJsonJobSchedulerList(){
		return this.schedulerlist;
	}
}
