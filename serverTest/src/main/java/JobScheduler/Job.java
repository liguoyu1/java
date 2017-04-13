/**
 * @author lgy
 * 
 */
package JobScheduler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import CloudR.Cloud;
import CloudR.Resource;
import dealdata.MyRandom;
import server.serverTest.JsonJob;

@XmlRootElement
public class Job {
	@XmlElement
	public long JobId;
	@XmlElement
	public String JobName;
	@XmlElement
	public long UserId;
	@XmlElement
	public String UserName;
	@XmlElement
	public ArrayList<TaskPack> TaskPackList;
	@XmlElement
	public ArrayList<Task> taskList;
	@XmlElement
	public boolean DesignatedArea;
	@XmlElement
	public String place;
	@XmlElement
	public int jobPriority;
	@XmlElement
	public int demoNum; // 开启的实例个数
	@XmlElement
	public int priority;
	@XmlElement
	public long taskNum ;
	@XmlElement
	public double taskRuntime;
	@XmlElement
	public long curTaskNum;
	@XmlElement
	public long DevideTaskSize = 100;
	@XmlElement
	public long timeLimit;					//finished data time    :day:
	@XmlElement
	public long reserveTime;                //reserve time          :zhouqi 
	@XmlElement
	public long lastRunTime;                // 上一次被调度时间
	@XmlElement
	public String docker;
	public int jobtype;
	
	public Job(){}
	
	public Job(Job job){
		this.JobId = job.getJobId();
		this.JobName = job.getJobName();
		this.UserId = job.getJobId();
		this.UserName = job.getUserName();
		this.TaskPackList = job.getTaskPack();
		this.DesignatedArea = job.getDesignatedArea();
		this.place = job.getPlace();
		this.jobPriority = job.getJobPriority();
		this.priority = job.getPriority();
		this.taskNum = job.getTaskNum();
		this.curTaskNum = job.getCurTaskNum();
		this.DevideTaskSize = job.getDevideTaskSize();
		this.timeLimit = job.getTimeLimit();
		this.reserveTime = job.getReserveTime();
		this.lastRunTime = job.getLastRunTime();
		this.demoNum = job.getDemoNum();
		this.taskList = job.getTaskList();
		this.taskRuntime = job.getTaskRunTime();
		this.docker = job.getDocker();
		this.jobtype = job.getJobType();
	}
	
	public Job(JsonJob job){
		this.JobId = job.getJobId();
		this.JobName = job.getJobName();
		this.UserId = job.getJobId();
		this.UserName = job.getUserName();
		this.DesignatedArea = job.getDesignatedArea();
		this.place = job.getPlace();
		this.jobPriority = job.getJobPriority();
		this.priority = job.getPriority();
		this.taskNum = job.getTaskNum();
		this.curTaskNum = job.getCurTaskNum();
		this.DevideTaskSize = job.getDevideTaskSize();
		this.timeLimit = job.getTimeLimit();
		this.reserveTime = job.getReserveTime();
		this.lastRunTime = job.getLastRunTime();
		this.demoNum = job.getDemoNum();
		this.taskRuntime = job.getTaskRunTime();
		this.docker = job.getDocker();
		this.jobtype = job.getJobType();
	}
	
	public void setDocker(String docker){
		this.docker = docker;
	}
	
	public void setReserveTime(long reserveTime){
		this.reserveTime = reserveTime;
	}
	
	public void setTaskRunTime(double taskRuntime){
		this.taskRuntime = taskRuntime;
	}
	
	public void setDemoNum(int demonum){
		this.demoNum = demonum;
	}
	
	public void setTaskList(ArrayList<Task> tasklist){
		this.taskList = tasklist;
	}
	
	public ArrayList<Task> getTaskList(){
		return this.taskList;
	}
	
	
	public void setLastRunTime(long lastRunTime){
		this.lastRunTime = lastRunTime;
	}
	
	public long getLastRunTime(){
		return this.lastRunTime;
	}
	
 	public long getTimeLimit(){
		return this.timeLimit;
	}
	
	public void setTimeLimit(long timeLimit){
		this.timeLimit = timeLimit;
	}
	
	public void setPlace(String place){
		this.place = place;
	}
	
	public String getPlace(){
		return this.place;
	}
	
	public void updatelastTime(){
		this.lastRunTime = System.currentTimeMillis();
	}
	
 	public void setTaskPack(ArrayList<Task> tasklist){
		long listSize = tasklist.size();
		for(int i = 0;i < listSize;i += taskNum){
			TaskPack taskPck = new TaskPack();
			if(i + taskNum <= listSize){
				taskPck.setTaskPack((ArrayList<Task>) tasklist.subList(i, (int) (i + taskNum)));
				taskPck.setTaskNumber(taskNum);
			}
			else{
				taskPck.setTaskPack((ArrayList<Task>) tasklist.subList(i, (int) listSize));
				taskPck.setTaskNumber(listSize-i);
			}
			TaskPackList.add(taskPck);
		}
	}
	
 	/**
 	 * @category config job divide task size 
 	 * @param devideTaskSize
 	 */
	public void setDevideTaskSize(long devideTaskSize){
		this.DevideTaskSize = devideTaskSize;
	}
	
	public void setJobPriority(int jobPriority){
		this.jobPriority = jobPriority;
	}
	
	public void setPriority(int priority){
		this.priority = priority;
	}
	
	public void setTaskNumber(long taskNum){
		this.taskNum = taskNum;
	}
	
	public void setCurrentTaskNumber(long curTaskNum){
		this.curTaskNum = curTaskNum;
	}
	
	public void setJobId(long jobid){
		this.JobId = jobid;
	}
	
	public void setJobName(String jobName){
		this.JobName = jobName;
	}
	
	public void setUserId(long userId){
		this.UserId = userId;
	}
	
	public void setUserName(String userName){
		this.UserName = userName;
	}
	
	public void setTimeLimit(String datatime) {
		// datatime :year-month-day    till the day 23:59:59 same till day+1
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			this.timeLimit = df.parse(datatime).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addTask(){
		
	}

	public void updateReserveTime(){
		this.reserveTime = this.timeLimit - System.currentTimeMillis();
	}
	
	public void updatePriority(){
		long days = 0;
		days = this.getReserveTime();///(1000*60*60*24);
		long stayTime = System.currentTimeMillis() - this.lastRunTime;
		this.priority = (int) (1*this.curTaskNum/this.DevideTaskSize/(days+1) + this.jobPriority+stayTime/1000);
	}
	
	public double getTaskRunTime(){
		return this.taskRuntime;
	}
	
	public int getDemoNum(){
		return this.demoNum;
	}

	public long getReserveTime(){
		this.updateReserveTime();
		return this.reserveTime;
	}
	
	public ArrayList<TaskPack> getTaskPack(){
		return this.TaskPackList;
	}
	
	public int getPriority(){
		return this.priority;
	}
	
	public long getTaskNum(){
		return this.taskNum;
	}
	
	public long getCurTaskNum(){
		return this.curTaskNum;
	}
	
	public long getJobId(){
		return this.JobId;
	}
	
	public String getJobName(){
		return this.JobName;
	}
	
	public long getUserId(){
		return this.UserId;
	}
	
	public String getUserName(){
		return this.UserName;
	}
	
	public long getDevideTaskSize(){
		return this.DevideTaskSize;
	}
	
	public int getJobPriority(){
		return this.jobPriority;
	}
	
 	public static void main(String[] args) {

		// define the job and tie it to our HelloJob class
// 		System.out.println(new Job().getDevideTaskSize());
 		Job job = new Job();
 		job.Init();
 		job.show();
    }
 	
 	public void setDesignatedArea(boolean designatedArea){
 		this.DesignatedArea = designatedArea;
 	}
 	
 	public boolean getDesignatedArea(){
 		return this.DesignatedArea;
 	}
 	
 	/**
 	 * @category 计算任务完成风险
 	 * @method  计算模型：
 	 * timeRun ：任务执行所需时间
 	 * remainTime : 任务执行剩余时间
 	 * jobPriority : 任务的优先级
 	 * @return
 	 */
 	public double finishedRisk(){
 		double jobFinishedRisk = 0.0d;
 		double remainTime = this.getReserveTime();   //任务剩余时间
 		//所需运行时间
 		double timeRun = this.curTaskNum*this.taskList.get(0).getTimeRun();
 		//Sigmoid函数的参数
 		double e_x = (timeRun - remainTime)/timeRun ;
 		//sigmoid函数计算任务风险值
 		jobFinishedRisk = 10/(Math.exp(-e_x)+1);
 		return jobFinishedRisk;
 	}
 	
 	//
 	public double getJobCpu(){
 		return this.getTaskList().get(0).getTaskCpu();
 	}
 	
 	public double getJobMem(){
 		return this.getTaskList().get(0).getTaskMem();
 	}
 	
 	public String getDocker(){
 		return this.docker;
 	}
 	
 	/**
 	 * Iint
 	 */
	public void Init() {
		// TODO Auto-generated method stub
//		this
		MyRandom myrand = new MyRandom();
		Random rand = new Random();
		this.setJobId(myrand.randInt( 100000001, 999999999));
		this.setJobName(myrand.randString(10));
		this.setUserId(myrand.randInt( 1000001, 9999999));
		this.setUserName(myrand.randString(14));
		this.setJobPriority(myrand.randInt(1, 10));
		this.setTaskNumber(myrand.randInt(10000,100000));
		this.setCurrentTaskNumber(this.getTaskNum());
		this.updatelastTime();
		this.setDemoNum(1);
		this.setJobType(rand.nextInt(4));
		this.setTaskRunTime((rand.nextFloat()+0.001)*(rand.nextInt(10)+0.1));
		this.taskList = new ArrayList<Task>();
//		System.out.println(this.taskNum);
		this.taskList.add(new Task(0));
		this.setDocker(myrand.randString(8));
		this.setTimeLimit(myrand.randDateTime());
//		for(int i = 0;i < this.taskNum;i++){
//			this.taskList.add(new Task(i));
//		}
	}
	
	public void setJobType(int nextInt) {
		// TODO Auto-generated method stub
		this.jobtype = nextInt;
	}

	public void setTask(long taskNum){
		this.taskList = new ArrayList<Task>();
		for(int i = 0; i < taskNum;i++){		
			this.taskList.add(new Task(i));
		}
	}
	
	public void setTask(){
		this.taskList = new ArrayList<Task>();
		for(int i = 0;i < this.taskNum;i++){
			this.taskList.add(new Task(i));
		}
	}

	public void show() {
		// TODO Auto-generated method stub
		System.out.println("Job Name:"+this.JobName);
		System.out.printf("JobId:%08d\n",this.JobId);
		System.out.printf("JobType:%d\n",this.jobtype);
//		System.out.println("task number:"+this.taskNum);
//		System.out.println("first task:"+this.taskList.get(0)+"job risk:"+this.finishedRisk());	
	}

	public void showJobId() {
		// TODO Auto-generated method stub
		System.out.print("Job Name:"+this.JobName+"\t");
		System.out.printf("JobId:%08d\t",this.JobId);
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.print("Job last run:"+time.format(this.lastRunTime)+"\t");
		System.out.println("Job priority:"+this.priority);
	}
	
	/**
	 * job的调度，负责分配执行的集群，和提交任务
	 * @param cloud   //云
	 * @param cpu     //单个任务的Cpu使用
	 * @param mem	  //单个任务的内存使用
	 * @param min_cur //任务的并发数量
	 * @return
	 */
	public boolean Scheduler(Cloud cloud, double cpu, long mem, long min_cur) {
		// TODO Auto-generated method stub
		double useableRe_Cpu;
		double useableRe_Mem;
		Resource re;
		if(this.DesignatedArea){
			re = cloud.getResource("Beijing");
			useableRe_Cpu = re.getCpu() - re.getCpuUsed();
			useableRe_Mem = re.getMem() - re.getMemUsed();
			if(useableRe_Mem >= mem*min_cur && useableRe_Cpu >= cpu*min_cur){
				re.setCpuUsed(re.getCpuUsed()+cpu*min_cur);
				re.setMemUsed(re.getMemUsed()+mem*min_cur);
				System.out.printf("Job %s schedule to %s succeed!\n",this.JobName,re.getResourcePlace());
				this.updatelastTime();
				return true;
			}
		}
		else{
			double usrCpu = cloud.getResourceCpu();
			long usrMem = cloud.getResourceMem();
			System.out.println(this.getJobName()+"最小并发数:"+min_cur);
			System.out.println("可用CPU:"+usrCpu+" 可用Mem："+usrMem);
			System.out.println("最小并发所需Cpu："+cpu*min_cur+"所需Mem:"+mem*min_cur);
			if(usrMem >= mem*min_cur && usrCpu >= cpu*min_cur){
				HashMap<Resource,Integer> resourceSch = cloud.CloudResourceScheduler(cpu,mem,min_cur);
				if(resourceSch.isEmpty()){
					return false;
				}
				for(Map.Entry<Resource,Integer> value : resourceSch.entrySet()){
					System.out.printf("Job %s run %d task On Place %s Cluster!\n",this.JobName,value.getValue(),value.getKey().getResourcePlace());
					this.taskScheduler(value.getValue());
				}
				this.updatelastTime();
				this.updatePriority();
//				this.updateReserveTime();
				return true;
			}
		}
		return false;
	}

	public ArrayList<TaskPack> taskScheduler(int curnum){
		ArrayList<TaskPack> taskPlist = new ArrayList<TaskPack>();
		for(int i = 0;i < curnum;i++){
//			TaskPack taskP = this.TaskPackList.get(0);
//			this.TaskPackList.remove(0);
//			taskPlist.add(taskP);
			this.curTaskNum -= this.DevideTaskSize;
			if(this.curTaskNum <= 0){
				break;
			}
		}
		return taskPlist;
	}

	
	public int getJobType() {
		// TODO Auto-generated method stub
		return this.jobtype;
	}
}
