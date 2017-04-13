package JobScheduler;

import java.util.ArrayList;
import java.util.Iterator;

public class JobSchedulerList {
	ArrayList<JobScheduler> jobSchedulerlist;
	
	public JobSchedulerList(){
		this.jobSchedulerlist = new ArrayList<JobScheduler>();
	}
	
	public JobSchedulerList(JobSchedulerList jobschedulerlist){
		this.jobSchedulerlist = new ArrayList<JobScheduler>();
		for(Iterator<JobScheduler> iter = jobschedulerlist.getJobSchedulerList().iterator();iter.hasNext();){
			this.jobSchedulerlist.add(new JobScheduler(iter.next()));
		}
	}
	
	public void add(JobScheduler jobScheduler){
		this.jobSchedulerlist.add(jobScheduler);
	}
	
	public JobScheduler getJobScheduler(int index){
		return this.jobSchedulerlist.get(index);
	}
	
	public ArrayList<JobScheduler> getJobSchedulerList(){
		return this.jobSchedulerlist;
	}	
	
	public void show(){
		System.out.println("任务调度如下：");
		for(Iterator<JobScheduler> iter = this.jobSchedulerlist.iterator();iter.hasNext();){
			iter.next().show();
		}
	}

}
