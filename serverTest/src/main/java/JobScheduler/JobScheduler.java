package JobScheduler;

import CloudR.Resource;
import MinMin.ETCTime;

public class JobScheduler {
	Job job;
	Resource re;
	double timeRun;
	public JobScheduler(ETCTime etc){
		this.job = new Job(etc.getJob());
		this.re = new Resource(etc.getResource());
		this.timeRun = etc.getECTTime()*this.job.curTaskNum/this.job.demoNum;
	}
	
	public JobScheduler(JobScheduler jobscheduler){
		this.job = new Job(jobscheduler.getJob());
		this.re = new Resource(jobscheduler.getResource());
		this.timeRun = jobscheduler.getJobRunTime();
	}
	
	public Job getJob(){
		return job;
	}
	
	public Resource getResource(){
		return re;
	}
	
	public double getJobRunTime(){
		return this.timeRun;
	}
	
	public void show(){
		System.out.println("Job Name:"+this.job.getJobName()+" Job Id"+this.job.getJobId()+" 分配到资源-id:"+this.re.getResourceId()+" , place:"+this.re.getResourcePlace()+"\t任务执行时间："+this.timeRun);
	}
}
