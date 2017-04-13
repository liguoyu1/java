package ACOScheduler;

import java.util.ArrayList;
import java.util.Iterator;

import JobScheduler.Job;
import JobScheduler.JobList;
import JobScheduler.JobScheduler;
import JobScheduler.JobSchedulerList;
import MinMin.ETCMatrix;

public class JobAco {
	ETCMatrix etcM;
	
	//蚂蚁选择参数
	double alpha;          						// α 调节参数
	double beta;								// β 调节参数
	
	//调度的任务
	JobSchedulerList JobSchedulerList;			// 调度任务队列
	double bestScheTime;
	
	//蚁群参数
	double rho;                                // 信息素更新参数    公式如：A = （1-rho）*A + @delt*rho
	int Max_Gen;							   // 最大迭代次数
	int Min_Gen; 							   // 最小迭代次数
	int antNum ; 							   // 蚁群蚂蚁数量
	ArrayList<JobAnt> ants;
	int jobNum;								   // 调度任务数量
	int resourceNum;						   // 资源数量 （指集群个数/服务器数量（根据需要指定））
	
	double [][]pheromone;
	
	/**
	 * 初始化蚁群
	 * @param etcM   任务资源矩阵   数据结构见ETCMatrix 实现
	 * @param antNum 蚂蚁个数
	 * @param Max_Gen 迭代最大次数
	 * @param alpha  参数 α
	 * @param beta   参数 β
	 * @param rho   参数ρ （0,1）
	 * @throws InterruptedException 
	 * 
	 */
	public JobAco(ETCMatrix etcM,int antNum,int Max_Gen ,double alpha ,double beta ,double rho ) throws InterruptedException{
		this.etcM = new ETCMatrix(etcM);
		this.antNum = antNum;
		this.Max_Gen = Max_Gen;
		this.Min_Gen = 100;
		this.alpha = alpha;
		this.beta = beta;
		this.rho = rho;
		this.bestScheTime = Double.MAX_VALUE;
		this.jobNum = etcM.getJobs().getJobNum();
		this.resourceNum = etcM.getClolud().getResourceNum();
		this.ants = new ArrayList<JobAnt>();
		for(int i = 0;i < antNum;i++){
			this.ants.add(new JobAnt(etcM));
		}
		this.pheromone = new double[jobNum][resourceNum];
		JobList jobs = etcM.getJobs();
		for(int i = 0;i < jobNum;i++){
			Job job = jobs.getJobList().get(i);
			double G = (double)job.getJobPriority()*job.getReserveTime()/
					((double)job.getCurTaskNum()/job.getDemoNum());
//			System.out.println((double)job.getJobPriority());
			pheromone[i][0] = G;
			for(int j = 0; j < resourceNum;j++){
				pheromone[i][j] = pheromone[i][0];
//				System.out.print(pheromone[i][j]+" ");
			}
//			System.out.println();
		}
	}
	
	/**
	 * 更新信息素
	 */
	public void updatePheromone(){
		for(int i = 0;i < this.jobNum;i++){
			for(int j = 0;j < resourceNum;j++){
				pheromone[i][j] = pheromone[i][j]*(1 - rho);
			}
		}
		
		for(int i = 0;i < jobNum;i++){
			for(int j = 0;j < resourceNum;j++){
				for(int k = 0;k <  antNum;k++){
					pheromone[i][j] += ants.get(k).getDelt()[i][j]*rho;
				}
			}
		}
	}
	
	/**
	 * 蚁群算法任务调度
	 */
	public void ACOScheduler(){
		int cur_Gen = 0;
		double oldRuntime = this.bestScheTime;
		//蚁群迭代
		while(cur_Gen < Max_Gen){
			for(int i = 0;i < antNum;i++){
				JobAnt ant = ants.get(i);
				ant.selectJob(pheromone);
				//
				if(ant.getJobSchedulerTime() < this.bestScheTime){
					this.bestScheTime = ant.getJobSchedulerTime();
					this.JobSchedulerList = new JobSchedulerList(ant.getJobShcedulerList());
				}
			}
			cur_Gen++;
			if(Math.abs(oldRuntime-this.bestScheTime) > 0.01d&&cur_Gen>Min_Gen){
				break;
			}
			else{
				oldRuntime = this.bestScheTime;
			}
			this.updatePheromone();
		}
	}
	
	/**
	 * 打印调度结果
	 */
	public void printScheduler(){
		System.out.println("蚁群调度算法调度时间："+this.bestScheTime);
		this.JobSchedulerList.show();
	}
	
	public int getJobNum(){
		return this.jobNum;
	}
	
	public JobSchedulerList getJobShcedulerList(){
		return this.JobSchedulerList;
	}
	
	public void jobRunTime(){
		for(Iterator<JobScheduler> jobsch = this.getJobShcedulerList().getJobSchedulerList().iterator();jobsch.hasNext();){
			JobScheduler job = jobsch.next();
			this.bestScheTime += job.getJobRunTime();
		}
	}
	
	public double getSchedulerJobRuntime(){
		return this.bestScheTime;
	}
	
	public static void main(String[] args) throws InterruptedException{
		for(int i = 1;i < 11;i++){
			ETCMatrix etcM = new ETCMatrix(10*i);
			JobAco jobaco = new JobAco(etcM,100,200,0.5d,1.5d,0.5d);
			long startT = System.currentTimeMillis();
			jobaco.ACOScheduler();
			long endTime = System.currentTimeMillis();
			jobaco.printScheduler();
			System.out.println("调度"+jobaco.getJobNum()+"任务用时："+(endTime-startT)/1000+"s");
		}
	}
	
}
