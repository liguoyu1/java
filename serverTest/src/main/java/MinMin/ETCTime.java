package MinMin;

import java.util.Random;

import CloudR.Resource;
import JobScheduler.Job;

public class ETCTime {
	double time;
	Job job;
	CloudR.Resource re;
	
	/**
	 * @category for test
	 * @param job
	 * @param re
	 */
	public ETCTime(Job job,CloudR.Resource re,double taskTime){
//		job.show();
		this.job = new Job(job);
		this.re = new Resource(re);
//		Random rand = new Random(System.currentTimeMillis());
//		double tasktime = (rand.nextFloat()+0.001)*(rand.nextInt(10)+0.1);
		this.time = taskTime * job.getCurTaskNum();
	}
	
	public ETCTime(ETCTime etc){
		this.job = new Job(etc.getJob());
		this.re = new Resource(etc.getResource());
		this.time = etc.getECTTime();
	}
	
	public Job getJob(){
		return this.job;
	}
	
	public CloudR.Resource getResource(){
		return this.re;
	}
	
	public double getECTTime(){
		return this.time;
	}
	
	public void setECTTime(double time){
		this.time = time;
	}
	
	public void setJob(Job job){
		this.job = job;
	}
	
	public void setResource(CloudR.Resource re){
		this.re = re;
	}
	
	/**
	 * for test
	 * @throws InterruptedException 
	 */
	public ETCTime() throws InterruptedException{
		this.job = new Job();
		this.re = new Resource();
		Random rand = new Random(System.currentTimeMillis());
		double tasktime = (rand.nextFloat()+0.001)*(rand.nextInt(10)+0.1);
		this.time = tasktime * job.getCurTaskNum();
	}
	
	public void show(){
//		this.job.show();
		System.out.print("(job:"+job.getJobName()+", resource:"+re.getResourceId()+", etctime:"+time+" )");
	}
	
	public void Init(){
//		this.job.show();
		this.job.Init();
	}
	
	public void solve(){
		
	}

	public static void main(String[] args) throws InterruptedException{
		ETCTime etcTime = new ETCTime();
		etcTime.Init();
		etcTime.show();
	}
}
