package MinMin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import CloudR.Cloud;
import CloudR.Resource;
import JobScheduler.Job;
import JobScheduler.JobList;


/**
 * 
 * @author lgy
 * @category Min-Min 任务调度算法
 * {@value etcMatrix ETC 时间矩阵}
 * {@value jobs 任务队列}
 * {@value cloud 资源池}
 */
public class ETCMatrix {
	ArrayList<ArrayList<ETCTime>>  etcMatrix;
	JobList jobs;
	Cloud cloud;
	
	public ETCMatrix(JobList jobs,Cloud cloud){
		this.jobs = new JobList(jobs);
		this.cloud = new Cloud(cloud);
		int row = this.jobs.getJobList().size();
		int cow = this.cloud.getResourceNum();
		System.out.println(row+" , "+cow);
		etcMatrix = new ArrayList<ArrayList<ETCTime>>();
		Random rand = new Random(System.currentTimeMillis());
		for(int i = 0;i < row;i++){
			ArrayList<ETCTime> JobEtc = new ArrayList<ETCTime>();
			for(int j = 0;j < cow;j++){
//				System.out.println(jobs.getJobList().get(i)+" , "+cloud.getResource(j));
				double tasktime = (rand.nextFloat()+0.001)*(rand.nextInt(10)+0.1);
				JobEtc.add(new ETCTime(jobs.getJobList().get(i),cloud.getResource(j),tasktime));
			}
			this.etcMatrix.add(JobEtc);
		}
	}

	/**
	 * for test
	 * @throws InterruptedException
	 */
	public ETCMatrix() throws InterruptedException{
		this.jobs = new JobList();
		this.jobs.Init(20);
		this.cloud = new Cloud();
		this.cloud.Init();
		int row = this.jobs.getJobList().size();
		int cow = this.cloud.getResourceNum();
		System.out.println(row+" , "+cow);
		etcMatrix = new ArrayList<ArrayList<ETCTime>>();
		Random rand = new Random(System.currentTimeMillis());
		for(int i = 0;i < row;i++){
			ArrayList<ETCTime> JobEtc = new ArrayList<ETCTime>();
			for(int j = 0;j < cow;j++){
//				System.out.println(jobs.getJobList().get(i)+" , "+cloud.getResource(j));
				double tasktime = (rand.nextFloat()+0.001)*(rand.nextInt(10)+0.1);
				JobEtc.add(new ETCTime(jobs.getJobList().get(i),cloud.getResource(j),tasktime));
			}
			this.etcMatrix.add(JobEtc);
		}
	}
	
	public ETCMatrix(int jobNum) throws InterruptedException{
		this.jobs = new JobList();
		this.jobs.Init(jobNum);
		this.cloud = new Cloud();
		this.cloud.Init();
		int row = this.jobs.getJobList().size();
		int cow = this.cloud.getResourceNum();
		System.out.println(row+" , "+cow);
		etcMatrix = new ArrayList<ArrayList<ETCTime>>();
		Random rand = new Random(System.currentTimeMillis());
		for(int i = 0;i < row;i++){
			ArrayList<ETCTime> JobEtc = new ArrayList<ETCTime>();
			for(int j = 0;j < cow;j++){
//				System.out.println(jobs.getJobList().get(i)+" , "+cloud.getResource(j));
				double tasktime = (rand.nextFloat()+0.001)*(rand.nextInt(10)+0.1);
				JobEtc.add(new ETCTime(jobs.getJobList().get(i),cloud.getResource(j),tasktime));
			}
			this.etcMatrix.add(JobEtc);
		}
	}
	
	public ETCMatrix(ETCMatrix etcM) {
		// TODO Auto-generated constructor stub
//		this.etcMatrix = etcM.getEtcMatrix();
		this.jobs = new JobList(etcM.getJobs());
		this.cloud = new Cloud(etcM.getClolud());
		this.etcMatrix = new ArrayList<ArrayList<ETCTime>>();
		for(Iterator<ArrayList<ETCTime>> iterA = etcM.getEtcMatrix().iterator();iterA.hasNext();){
			ArrayList<ETCTime> line = iterA.next();
			ArrayList<ETCTime> lineC = new ArrayList<ETCTime>();
			for(Iterator<ETCTime> iter = line.iterator();iter.hasNext();){
				lineC.add(new ETCTime(iter.next()));
			}
			this.etcMatrix.add(lineC);
		}
	}
	
	public JobList getJobs(){
		return this.jobs;
	}
	
	public Cloud getClolud(){
		return this.cloud;
	}

	/**
	 * @author lgy
	 * @category 更新选择矩阵
	 * @param cindex
	 */
	public boolean updateECTMatrix(int rindex ,int cindex){
		ETCTime etc = this.etcMatrix.get(rindex).get(cindex);
//		etc.show();
//		etc.getJob().show();
		Job job = etc.getJob();
		this.etcMatrix.remove(rindex);
		for(Iterator<ArrayList<ETCTime>> iter = this.etcMatrix.iterator();iter.hasNext();){
			ETCTime etcj = iter.next().get(cindex);
			Resource re = etcj.getResource();
//			System.out.println(job.getTaskList().isEmpty());
			if(re.getCpuUsed()+job.getTaskList().get(0).getTaskCpu()*job.getDemoNum() >= re.getCpu()){
				etcj.setECTTime(Double.MAX_VALUE);
				return false;
			}
			if(re.getMemUsed()+ job.getTaskList().get(0).getTaskMem()*job.getDemoNum() >= re.getMem())
			{
				etcj.setECTTime(Double.MAX_VALUE);
				return false;
			}
			re.setCpuUsed(re.getCpuUsed()+job.getTaskList().get(0).getTaskCpu()*job.getDemoNum());
			re.setMemUsed(re.getMemUsed()+ job.getTaskList().get(0).getTaskMem()*job.getDemoNum());
			if(re.getCpuUsed()/(double)re.getCpu() >= 0.85||re.getMemUsed()/(double)re.getMem() >= 0.85){
				etcj.setECTTime(Double.MAX_VALUE);
			}
//			else if(re.getCpuUsed()/(double)re.getCpu() >= 0.70||re.getMemUsed()/(double)re.getMem() >= 0.85){
//				etcj.setECTTime(etcj.getECTTime()*1.8);
//			}
			else{
				etcj.setECTTime(etcj.getECTTime());
			}
			for(Iterator<Resource> iterRe = cloud.getResource().iterator();iterRe.hasNext();){
				Resource tRe = iterRe.next();
				if(tRe.getResourceId() == re.getResourceId()){
					tRe.setCpuUsed(re.getCpuUsed());
					tRe.setMemUsed(re.getMemUsed());
					if(tRe.getCpuUsed()/(double)tRe.getCpu() >= 0.85||tRe.getMemUsed()/(double)tRe.getMem() >= 0.85){
//						.remove(tRe);
						iterRe.remove();
					}
				}
			}
		}
		return true;
	}
	
	public boolean updateECTMatrix(ETCTime etcindex){
		int rindex = 0;
		int cindex = 0;
		for(Iterator<ArrayList<ETCTime>> iter = this.etcMatrix.iterator();iter.hasNext();){
			if(etcindex.getJob().getJobId() == iter.next().get(0).getJob().getJobId()){
				break;
			}
			else{
				rindex++;
			}
		}
		
		for(Iterator<ETCTime> iter = this.etcMatrix.get(0).iterator();iter.hasNext();){
			if(etcindex.getResource().getResourceId() == iter.next().getResource().getResourceId()){
				break;
			}
			else{
				cindex++;
			}
		}
		return this.updateECTMatrix(rindex, cindex);
	}
	
	public boolean updateECTMatrixACO(int rindex,int cindex){
		ETCTime etc = this.etcMatrix.get(rindex).get(cindex);
		Job job = etc.getJob();
		for(Iterator<ArrayList<ETCTime>> iter = this.etcMatrix.iterator();iter.hasNext();){
			ETCTime etcj = iter.next().get(cindex);
			Resource re = etcj.getResource();
			if(re.getCpuUsed()+job.getTaskList().get(0).getTaskCpu()*job.getDemoNum() >= re.getCpu()){
				etcj.setECTTime(Double.MAX_VALUE);
				return false;
			}
			if(re.getMemUsed()+ job.getTaskList().get(0).getTaskMem()*job.getDemoNum() >= re.getMem())
			{
				etcj.setECTTime(Double.MAX_VALUE);
				return false;
			}
			re.setCpuUsed(re.getCpuUsed()+job.getTaskList().get(0).getTaskCpu()*job.getDemoNum());
			re.setMemUsed(re.getMemUsed()+ job.getTaskList().get(0).getTaskMem()*job.getDemoNum());
			for(Iterator<Resource> iterRe = cloud.getResource().iterator();iterRe.hasNext();){
				Resource tRe = iterRe.next();
				if(tRe.getResourceId() == re.getResourceId()){
					tRe.setCpuUsed(re.getCpuUsed());
					tRe.setMemUsed(re.getMemUsed());
					if(tRe.getCpuUsed()/(double)tRe.getCpu() >= 0.85||tRe.getMemUsed()/(double)tRe.getMem() >= 0.85){
						iterRe.remove();
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * @category 找到最小的ECT time 
	 * @return 最小的ECT 任务和资源组合
	 */
 	public ETCTime findMinETCTime(){
		ETCTime etc = this.etcMatrix.get(0).get(0);
		for(Iterator<ArrayList<ETCTime>> iter = this.etcMatrix.iterator();iter.hasNext();){
			ArrayList<ETCTime> lineETC = iter.next();
			
			if(etc.getECTTime() > lineETC.get(0).getECTTime()){
				etc = lineETC.get(0);
			}
			double o_cpu = etc.getResource().getCpuUsed()/etc.getResource().getCpu()+1;
			double o_mem = etc.getResource().getMemUsed()/etc.getResource().getMem()+1;
			double o_p = Math.max(o_cpu, o_mem);
			double pold = etc.getECTTime()*(1/o_p);
			for(Iterator<ETCTime> iterT = lineETC.iterator();iterT.hasNext();){
				
				ETCTime ectN = iterT.next();
				double n_cpu = ectN.getResource().getCpuUsed()/ectN.getResource().getCpu()+1;
				double n_mem = ectN.getResource().getMemUsed()/ectN.getResource().getMem()+1;
				double n_p = Math.max(n_cpu, n_mem);
				double pnew = ectN.getECTTime()*(1/n_p);
				if(pold > pnew){
					etc = ectN;
				}
			}
		}
		return etc;
	}
 	
 	public ETCTime findMinETCTime(int index){
 		int cindex = 0;
 		for(int i = 0;i < this.etcMatrix.get(index).size();i++){
 			if(this.etcMatrix.get(index).get(cindex).getECTTime() > this.etcMatrix.get(index).get(i).getECTTime()){
 				cindex = i;
 			}
 		}
 		return this.etcMatrix.get(index).get(cindex);
 	}

 	/**
 	 * 算法选择概率模型距离参数
 	 * @param rindex
 	 * @param cindex
 	 * @return
 	 */
 	public double getEtcTimeDistance(int rindex,int cindex){
 		ETCTime etcm = this.etcMatrix.get(rindex).get(cindex);
 		double cpuP = etcm.getResource().getCpuUsed()/etcm.getResource().getCpu()+1;
 		double memP = etcm.getResource().getMemUsed()/etcm.getResource().getMem()+1;
 		double pro =Math.max(cpuP, memP);
 		pro = etcm.getECTTime()*(1/pro);
 		return etcm.getECTTime()*pro;
 	}

 	/**
 	 * @category 判断是否有可用资源
 	 * @return   false 空 true 有可用资源
 	 */ 
 	public boolean HasResource(){
 		for(Iterator<ArrayList<ETCTime>> iterM = this.etcMatrix.iterator();iterM.hasNext();){
 			for(Iterator<ETCTime> iter = iterM.next().iterator();iter.hasNext();){
 				if(iter.next().getECTTime() != Double.MAX_VALUE){
 					return true;
 				}
 			}
 		}
 		return false;
 	}
 	
	public void show(){
//		for(Iterator<ArrayList<ETCTime>> iterL = etcMatrix.iterator();iterL.hasNext();){
//			for(Iterator<ETCTime> iterE = iterL.next().iterator();iterE.hasNext();){
//				iterE.next().show();
//				System.out.print(", ");
//			}
//			System.out.println();
//		}
		
		System.out.println("云平台个节点资源使用情况");
		for(Iterator<Resource> iter = cloud.getResource().iterator();iter.hasNext();){
			Resource re = iter.next();
			System.out.print("资源:"+re.getResourceId()+"\t");
			System.out.print("Cpu\t已使用:"+re.getCpuUsed()+"\tCPU核数："+re.getCpu() +"\t");
			System.out.println("内存使用："+re.getMemUsed()+"\tMem总计："+re.getMem());
		}
	}
	
	//获取时间矩阵
	public ArrayList<ArrayList<ETCTime>> getEtcMatrix(){
		return this.etcMatrix;
	}
	
	//获取当前节点
	public ETCTime getEtcTime(int rindex,int cindex){
//		System.out.println("x:"+rindex+"y:"+cindex);
		return this.etcMatrix.get(rindex).get(cindex);
	}

	public static void main(String[] args) throws InterruptedException{
		ETCMatrix etcM = new ETCMatrix();
		etcM.show();
	}
}
