/**
 * @author lgy
 * 
 */
package TaskResourceTable;

import java.util.ArrayList;
import java.util.Iterator;

import JobScheduler.Job;
import JobScheduler.JobList;

public class CalculateJobCost {
	public ArrayList<ResourceGroup> rgs;
	
	public CalculateJobCost(){
		
	}
	
	public long getSizerg(){
		return this.rgs.size();
	}
	/**
	 * 计算时间Job的时间消耗
	 * @param job
	 * @param type_cost :1是最大耗费时间,2:最小耗费时间,3:最适资源配比时间消耗
	 * @return
	 */
	public long calJobTimeCost(Job job,int type_cost){
		double time = 0;
		ResourceGroup rg = this.getResourceGroup(job);
		if(type_cost == 1)
			time = rg.getMaxTime();
		else if(type_cost == 2)
			time = rg.getMinTime();
		else
			time = rg.getSuitTime();
		long taskNum = job.getCurTaskNum();
		System.out.println("task time :"+time+" 任务数量："+taskNum);
		time = time * taskNum/job.getDevideTaskSize();
		return (long)time;
	}
	
	/**
	 * 计算job的当前时间下的最小并发数
	 * @param job
	 * @param type_cost
	 * @return
	 */
	public long calTaskMinConcurrency(Job job,int type_cost){
		long jobCost = this.calJobTimeCost(job, type_cost);
		long reserveTime = job.getReserveTime()/(1000*60*60);
		long currentTask = 0;
		double cur = (double)jobCost/(reserveTime+1);
		System.out.println("Cost time :"+jobCost+" 剩余时间："+(reserveTime+1));
//		System.out.println("计算最小并发值为："+cur);
		if(cur == (long)cur){
			currentTask = (long)cur;
		}
		else{
			currentTask = (long)cur +1;
		}
		if(currentTask <= 0){
			currentTask += 1;
		}
		return currentTask;
	}
	
	public ResourceGroup getResourceGroup(Job job){
//		ResourceGroup rg;
		String jobName = job.getJobName();
		long jobId = job.getJobId();
//		System.out.println("Job name :"+jobName+"job Id :"+ jobId);
//		job.show();
//		this.show();
		for(Iterator<ResourceGroup> iter = rgs.iterator();iter.hasNext();){
			ResourceGroup rg = iter.next();
//			System.out.println("rg Job name :"+rg.getJobName()+"rg job Id :"+ rg.getJobId());
			if(jobName == rg.getJobName() && jobId == rg.getJobId()){
				return rg;
			}
		}
		return null;
	}
	
	/**
	 * 初始化资源和job执行时间表
	 * @param jobList
	 */
	public void Init(JobList jobList){
		System.out.println("fdsa");
		this.rgs = new ArrayList<ResourceGroup>();
		for(Iterator<Job> iter = jobList.getJobList().iterator();
				iter.hasNext();){
			Job job = iter.next();
			ResourceGroup rg = new ResourceGroup();
			rg.setJob(job);
			rg.InitCpuCombMemCost();
			rgs.add(rg);
		} 
	}
	
	/**
	 * 主要调试使用
	 * 显示任务的最小并发
	 */
	public void show(){
		System.out.println("Resource Group:");
		for(Iterator<ResourceGroup> iter = rgs.iterator(); iter.hasNext();){
			ResourceGroup rg = iter.next();
//			System.out.println()
			rg.show();
		}
		
		
	}
	
	public static void main(String[] args){
		JobList jobList = new JobList();
		jobList.Init(10);
		CalculateJobCost calculJobCost = new CalculateJobCost();
		calculJobCost.Init(jobList);
//		System.out.println(jobList.getJobList().size());
//		System.out.println(calculJobCost.getSizerg());
		System.out.println(calculJobCost.calTaskMinConcurrency(jobList.getJobList().get(0), 2));
	}

	public double getResourceCpu(Job job, int i) {
		// TODO Auto-generated method stub
		ResourceGroup rg = this.getResourceGroup(job);
		if(i == 1){
			return rg.getFirstCpu();
		}
		else if(i == 2){
			return rg.getSuitCpu();
		}
		else{
			return rg.getLastCpu();
		}
	}
	
	public int getResourceMem(Job job ,int i){
		ResourceGroup rg = this.getResourceGroup(job);
		if(i == 1){
			return rg.getFisrtMem();
		}
		else if(i == 2){
			return rg.getSuitMem();
		}
		else{
			return rg.getLastMem();
		}
	}

//	public static void main(String[] args){
//		
//	} 
}
