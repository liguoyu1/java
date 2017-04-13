/**
 * @author lgy
 * 
 */
package JobScheduler;

import java.text.SimpleDateFormat;
import java.util.Iterator;

import TaskResourceTable.CalculateJobCost;
import CloudR.Cloud;

public class Scheduler {
	public Cloud cloud;
	public JobList jobList;
	public CalculateJobCost calJobCost;
	
	
	public void show(){
		this.jobList.showByPriority();
		this.cloud.show();
	}
	
	public boolean hasJob(){
		if(this.jobList.getJobList().isEmpty()){
			return false;
		}
		return true;
	}
	
	/**
	 * Scheduler Init
	 * @throws InterruptedException 
	 */
	public void Init() throws InterruptedException{
		cloud = new Cloud();
		cloud.Init();
		cloud.show();
		jobList = new JobList();
		jobList.Init(10);
		jobList.show();
		calJobCost = new CalculateJobCost();
		calJobCost.Init(jobList);
	}
	
	public void taskScheduler(){
		System.out.println("Scheduler start ...");
		this.jobList.sortByPriority();
		this.cloud.updateCloudResource();
		String failJobName = null;
		int count = 0;
		JobList finishedJob = new JobList();
		JobList WrongJob = new JobList();
		for(Iterator<Job> iter = jobList.getJobList().iterator();iter.hasNext();){
			Job job = iter.next();
			if(job.getCurTaskNum() <= 0){
				finishedJob.addJob(job);
				System.out.printf("Job %s run finnished!\n", job.getJobName());
				continue;
			}
			long min_cur = calJobCost.calTaskMinConcurrency(job, 2);
			double cpu = calJobCost.getResourceCpu(job,2);
			long mem = calJobCost.getResourceMem(job, 2);
			if(job.Scheduler(cloud,cpu,mem,min_cur)){
				System.out.printf("Job %s Scheduler succeed!\n",job.getJobName());
			}
			else{
				System.out.printf("Job %s Scheduler failed!\n",job.getJobName());
				if(failJobName == job.getJobName()){
					count += 1;
				}
				else{
					count = 1;
				}
				failJobName = job.getJobName();
				if(count>=3){
					count = 0;
					System.out.printf("Job %s Scheduler can deal!\n",job.getJobName());
//					jobList.removeJob(jobList.findJob(job));
					WrongJob.addJob(job);
				}
				break;
			}
		}
		
		//清除无效的Job
		this.jobList.removeJob(finishedJob);
		this.jobList.removeJob(WrongJob);
		System.out.println("Scheduler finished!");
	}
	
	public static void main(String[] args) throws InterruptedException{
		Scheduler scheduler = new Scheduler();
		scheduler.Init();
		scheduler.show();
		Thread.sleep(1*1000);
		while(true){
			System.out.printf("Scheduler time %s\n",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
			scheduler.taskScheduler();
			Thread.sleep(500);
			if(!scheduler.hasJob()){
				System.out.println("No Job can be scheduler !");
			}
		}
	}

}
