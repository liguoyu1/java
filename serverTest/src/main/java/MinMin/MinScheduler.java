package MinMin;

import java.util.Iterator;

import CloudR.Cloud;
import JobScheduler.JobList;
import JobScheduler.JobScheduler;
import JobScheduler.JobSchedulerList;
import TaskResourceTable.CalculateJobCost;

public class MinScheduler {
//	double ECTMatrix[][];
	CalculateJobCost jobCost;
	ETCMatrix etcM;
	
	//调度结果及统计数据
	JobSchedulerList jobSchedulerList;			// 调度任务队列
	double bestScheTime;						// 当前算法调度的运行时间
	
	public MinScheduler() throws InterruptedException{
		JobList jobs = new JobList();
		jobs.Init(20);
		Cloud cloud = new Cloud();
		cloud.Init();
		etcM = new ETCMatrix(jobs,cloud);
	}
	
	public MinScheduler(ETCMatrix etcM){
		this.etcM = new ETCMatrix(etcM);
//		this.
	}
	
	/**
	* @category 最小--最小调度算法
	 * @return  调度队列
	 * @throws InterruptedException 
	 */
	public JobSchedulerList minMinScheduler(){
		this.jobSchedulerList = new JobSchedulerList();
		ETCTime etc;
		while(!this.etcM.getEtcMatrix().isEmpty()){
			etc = this.etcM.findMinETCTime();
			if(this.etcM.updateECTMatrix(etc)){
				jobSchedulerList.add(new JobScheduler(etc));
			}
			if(this.etcM.getClolud().getResource().isEmpty()){
				System.out.println("资源已经分配完毕！本轮调度结束！");
				break;
			}
		}
		this.etcM.getEtcMatrix();
		if(this.etcM.getEtcMatrix().isEmpty()){
			System.out.println("任务列表所有任务均已调度完毕！调度结束！");
		}
		this.updateJobRunTime();
		return jobSchedulerList;
	}
	
	public void updateJobRunTime(){
		for(Iterator<JobScheduler> iter = this.jobSchedulerList.getJobSchedulerList().iterator();iter.hasNext();){
			this.bestScheTime += iter.next().getJobRunTime();
		}
	}
	
	public void show(){
		System.out.println("Min-Min调度方法调度任务执行时间和："+this.getBestSchedulerTime());
//		this.etcM.getJobs().show();
//		this.etcM.getClolud();
//		this.jobCost.show();
		this.jobSchedulerList.show();
	}
	
	public ETCMatrix getETCMtrix(){
		return this.etcM;
	}
	
	public static void main(String[] args) throws InterruptedException{
		MinScheduler mins = new MinScheduler();
		mins.minMinScheduler().show();
		mins.etcM.show();
	}
	
	public int getJobNum(){
		return this.jobSchedulerList.getJobSchedulerList().size();
	}

	public double getBestSchedulerTime(){
		return this.bestScheTime;
	}
}
