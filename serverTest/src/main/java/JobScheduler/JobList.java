/**
 * @author lgy
 * 
 */
package JobScheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
//import dealdata.MyRandom;

@SuppressWarnings("rawtypes")
class JobSort implements Comparator{
	public int compare(Object o1,Object o2){
		Job obj1 = (Job)o1;
		Job obj2 = (Job)o2;
		if(obj1.getPriority() < obj2.getPriority()){
			return 1;
		}
		else if(obj1.getPriority() == obj2.getPriority()){
			return 0;
		}
		else{
			return -1;
		}
	}
}


public class JobList{
	ArrayList<Job> jobList;
	public int priority;     // Job priority (0 --- 100)
	public String place;
//	public CalculateJobCost calJobCost;
//	public long 
	public JobList(){
		this.jobList = new ArrayList<Job>();
		this.place = "Local";
		this.priority = 0;   
	}
	
	public JobList(JobList jobs) {
		// TODO Auto-generated constructor stub
		this.jobList = jobs.getJobList();
		this.place = "local";
		this.priority = jobs.getJobListPriority();
	}

	public void setJobList(ArrayList<Job> jobList){
		this.jobList = jobList;
	}
	
	public void addJob(Job job){
		this.jobList.add(job);
	}
	
	public void setPriority(int priority){
		this.priority = priority;
	}
	
	public void setPlace(String place){
		this.place = place;
	}
	
	public ArrayList<Job> getJobList(){
		return this.jobList;
	}
	
	public Job removeJob(int index){
		return this.jobList.remove(index);
	}
	
	public int getJobListPriority(){
		return this.priority;
	}
	
	public String getPlace(){
		return this.place;
	}
	
	public int findJob(Job job){
		return this.jobList.indexOf(job);
	}
	
	@SuppressWarnings("unchecked")
	public void sortByPriority(){
		for(Iterator<Job> iter = jobList.iterator();iter.hasNext();){
			iter.next().updatePriority();
		}
		//job 按优先级排序
		Collections.sort(this.jobList,new JobSort());
	}

	/**
	 * Init 初始化JobList队列
	 * 仅适用于测试
	 */
	public void Init(int jobN){
//		MyRandom myrand = new MyRandom();
//		int jobNumber = myrand.randInt(40,100);
		int jobNumber = jobN;
		for(int i = 0;i < jobNumber;i++){
			Job job = new Job();
			job.Init();
			this.addJob(job);
		}
//		calJobCost.Init(this.jobList);
	}
	
	/**
	 * show 
	 */
	public void show(){
		System.out.println("Job list:");
		for(Iterator<Job> iter = this.jobList.iterator();iter.hasNext();){
			Job job = iter.next();
			job.show();
		}
	}
	
	/**
	 * 选择在截止时间完成不了的任务
	 * @return 输出无法按时完成的任务
	 */
	public void  selectRiskJob(){
		int cUnCount = 0;
		int rUnCount = 0;
		for(Iterator<Job> iter = this.jobList.iterator();iter.hasNext();){
			Job job = iter.next();
			if(job.finishedRisk() >= 7){
				System.out.println("极不可能完成任务：");
				job.show();
				rUnCount++;
			}
			else if(job.finishedRisk() >= 5){
				System.out.println("较不可能完成任务：");
				job.show();
				cUnCount++;
			}
//			else if(job.finishedRisk() >= 3.5){
//				System.out.println("有可能不能完成的任务：");
//				job.show();
//				count++;
//			}
			else{
				continue;
			}
		}
		System.out.println("调度任务总数："+this.jobList.size()+"极不可能完成任务数量："+rUnCount+"较不可能完成任务:"+cUnCount);
//		System.out.println(x);
		
	}
	
	public void showByPriority(){
		System.out.println("Job Priority list:");
		for(Iterator<Job> iter = this.jobList.iterator();iter.hasNext();){
			Job job = iter.next();
			job.showJobId();
		}
	}

	public void Scheduler() {
		Job job = this.jobList.get(0);
//		//调度Job的任务执行
//		boolean run = false;
//		for(Iterator<Job> iter = jobList.iterator();iter.hasNext();){
//			Job job = iter.next();
//		}
//		
//		
		job.updatelastTime();
	}

	public int getJobNum(){
		return this.jobList.size();
	}
	
	public void removeJob(JobList wrongJob) {
		// TODO Auto-generated method stub
		for(Iterator<Job> iter = wrongJob.getJobList().iterator();iter.hasNext();){
			this.removeJob(this.findJob(iter.next()));
		}
	}
	
	public static void main(String[] args){
		JobList jobs = new JobList();
		jobs.Init(20);
		jobs.show();
//		jobs.selectRiskJob();
	}
}
