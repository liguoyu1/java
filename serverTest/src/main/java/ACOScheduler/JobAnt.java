package ACOScheduler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import JobScheduler.JobScheduler;
import JobScheduler.JobSchedulerList;
import MinMin.ETCMatrix;
import MinMin.ETCTime;

public class JobAnt {
	ETCMatrix EtcM;								// ETC 任务最短时间矩阵
	double [][] time_Run;   					// distance 矩阵 任务资源执行时间矩阵
	double [][] delt; 							// 更新信息素的增量τ
	int resourceCount;  						// 资源数
	int jobCount;								// 任务数量
	
	//蚂蚁选择参数
	double alpha;          						// α 调节参数
	double beta;								// β 调节参数
	//调度的任务
	JobSchedulerList JobSchedulerList;			// 调度任务队列
	double bestScheTime;
	
	/**
	 * 初始化
	 * @param etcM etc 时间矩阵
	 * @throws InterruptedException
	 */
	public JobAnt(ETCMatrix etcM) throws InterruptedException{
		EtcM = new ETCMatrix(etcM);
		this.resourceCount = etcM.getClolud().getResourceNum();
		this.jobCount = etcM.getJobs().getJobNum();
		this.time_Run = new double[jobCount][resourceCount];
		int i = 0;
		//初始化时间矩阵
		for(Iterator<ArrayList<ETCTime>> iterE = etcM.getEtcMatrix().iterator();iterE.hasNext();i++){
			int j = 0;
			for(Iterator<ETCTime> iter = iterE.next().iterator();iter.hasNext();j++){
				time_Run[i][j] = iter.next().getECTTime();
			}
		}
		//初始化delt矩阵
		this.delt = new double[jobCount][resourceCount];
		for(i = 0;i < jobCount;i++){
			for(int j = 0;j < resourceCount;j++){
				delt[i][j] = 0.0d;
			}
		}
		//初始化调度job表和已调度job 表
//		this.AllowJob = new ArrayList<Job>();
//		for(Iterator<Job> iter = etcM.getJobs().getJobList().iterator();iter.hasNext();){
//			this.AllowJob.add(iter.next());
//		}
		this.JobSchedulerList = new JobSchedulerList();	
	}

	public void setFirstJob(int index){
//		this.jobIndexFirst = index;
//		this.jobIndexCur = index;
//		this.AllowJob.remove(index);
//		ETCTime etcTime = this.EtcM.findMinETCTime(index);
//		JobScheduler jobscheduler = new JobScheduler(etcTime.getJob(),etcTime.getResource());
//		while(this.EtcM.updateECTMatrix(etcTime)){
//			this.JobSchedulerList.add(jobscheduler);
//			if(this.EtcM.getClolud().getResource().isEmpty()){
//				System.out.println("无资源可分配！");
//				return;
//			}
//		}
	}
	
	public void getTimeRun(){
		int i = 0,j = 0;
		for(Iterator<ArrayList<ETCTime>> iterEM = this.EtcM.getEtcMatrix().iterator();iterEM.hasNext();){
			for(Iterator<ETCTime> iter = iterEM.next().iterator();iter.hasNext();){
				this.time_Run[i][j] = iter.next().getECTTime();
				j++;
			}
			i++;
		}
	}
	
	public void setETCTime(ETCMatrix etcM){
		this.EtcM = etcM;
	}
	
	public void setTimeRun(double[][] timeRun){
		this.time_Run = timeRun;
	}
	
	public void setParam(double alpha,double beta){
		this.alpha = alpha;
		this.beta = beta;
	}
	
	/**
	 * 任务调度及资源分配
	 * @param pheromone 信息素
	 */
	public void selectJob(double[][] pheromone){
		//求解可选节点的被选值和
		ETCMatrix etcMa = new ETCMatrix(this.EtcM);
		double[][] pher = new double[jobCount][resourceCount];
		for(int i = 0;i <  jobCount;i++){
			for(int j = 0;j < resourceCount;j++){
				pher[i][j] = pheromone[i][j];
			}
		}
		int[] rindexs = new int[jobCount];
		int[] cindexs = new int[jobCount];
		int in = 0;
		
		double[][] p = new double[jobCount][resourceCount];
		double sum = 0.0f;
		
//		int reson = 0;     
		//1:当前无资源分配！
		//2：任务调度完毕！
		
		while(true){
			if(etcMa.getClolud().getResource().isEmpty()){
//				System.out.println("当前无资源分配！");
//				reson = 1;
				break;
			}
			sum = 0.0f;
			for(int i = 0;i < jobCount;i++){
				for(int j = 0;j <  resourceCount;j++){
					//可以根据不同模型，修改
//					System.out.print(etcMa.getEtcTimeDistance(i, j));
					p[i][j] = pher[i][j]/etcMa.getEtcTimeDistance(i, j);
					sum += p[i][j];
				}
//				System.out.println();
			}
//			System.out.println();
			
			if(sum == 0.0d){
//				System.out.println("任务调度完毕！");
//				reson = 2;
				break;
			}
			
			//求各个节点被选概率
			for(int i = 0;i < jobCount;i++){
				for(int j = 0;j <  resourceCount;j++){
					p[i][j] = p[i][j]/sum;
				}
			}
			//循环轮转算法，选择下一个城市节点
			Random rand = new Random();
			double selectP = rand.nextDouble();
			double selectSum = 0.0d;
			int rindex = 0,cindex = 0;
			boolean flag = false;
			for(rindex = 0;rindex <  jobCount;rindex++){
				for(cindex = 0;cindex < resourceCount;cindex++){
					selectSum += p[rindex][cindex];
					if(selectSum > selectP){
						break;
					}
				}
				if(selectSum > selectP){
					if(etcMa.updateECTMatrixACO(rindex,cindex)){
						for(int k = 0;k < resourceCount;k++){
							pher[rindex][k] = 0.0d;
						}
					}else{
						pher[rindex][cindex] = 0.0d;
						flag = true;
					}
					break;
				}
			}
			//如果该次调度不能分配资源成功，进行下一次调度
			if(flag){
				continue;
			}
			//记录修改的任务和资源组合的索引位置
			rindexs[in] = rindex;
			cindexs[in] = cindex;
			in++;
			this.JobSchedulerList.add(new JobScheduler(this.EtcM.getEtcTime(rindex,cindex)));
		}
		this.updateBestScheTime();
		this.updateDelt(in, rindexs, cindexs);
		p = null;
		etcMa = null;
		pher = null;
		rindexs = null;
		cindexs = null;
	}
	
	/**
	 * 更新信息素
	 * @param rindexs
	 * @param cindexs
	 * @param in
	 */
	public void updateDelt(int in,int[] rindexs,int[] cindexs){
		double bestJobRuntime = this.getJobSchedulerTime();
		for(int i = 0;i < in;i++){
			int x = rindexs[i];
			int y = cindexs[i];
			this.delt[x][y] = 1/bestJobRuntime;
		}
	}
	
	/**
	 * 计算当前调度下任务执行时间
	 * @see 作为算法最有结果评价值
	 * @return
	 */
	public double getJobSchedulerTime(){
		return this.bestScheTime;
	}
	
	public void updateBestScheTime(){
		for(Iterator<JobScheduler> iter = this.JobSchedulerList.getJobSchedulerList().iterator();iter.hasNext();){
			this.bestScheTime += iter.next().getJobRunTime();
		}
	}

	public JobSchedulerList getJobShcedulerList(){
		return this.JobSchedulerList;
	}
	
	public double[][] getDelt(){
		return this.delt;
	}
	
	public void show(){
		for(Iterator<JobScheduler> iter = this.JobSchedulerList.getJobSchedulerList().iterator();iter.hasNext();){
			iter.next().show();
		}
	}

}
