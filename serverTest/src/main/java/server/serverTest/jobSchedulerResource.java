package server.serverTest;

import java.util.ArrayList;
import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ACOScheduler.JobAco;
import CloudR.Cloud;
import CloudR.Resource;
import JobScheduler.Job;
import JobScheduler.JobList;
import JobScheduler.JobSchedulerList;
import MinMin.ETCMatrix;
import MinMin.MinScheduler;
import Mysql.Select;


@Path("/scheduler")
public class jobSchedulerResource {
	/**
	 * 极小-极小算法的调度
	 * @return 任务调度结果（任务资源组合）
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/mini")
	public JsonJobSchedulerResulte getMiniSchedulerResult(){
		System.out.println("Min-Min Scheduler");
		JsonJobSchedulerResulte jjsr = new JsonJobSchedulerResulte();
		Select select = new Select();
		ArrayList<Job> jobli = select.selectAllJob();
		System.out.println("任务量："+jobli.size());
		if(jobli.size() == 0){
			return null;
		}
		ArrayList<JsonJob> unfinished = new ArrayList<JsonJob>();
		ArrayList<Job> schJobs = new ArrayList<Job>();
		//预处理任务调度队列任务
		//找出队列的按当前调度一定不能够按时完成的任务
		for(Iterator<Job> iter = jobli.iterator();iter.hasNext();){
//			System.out.println("Min-Min Scheduler");
			Job j = iter.next();
			j.setTask(1);
			System.out.println(j.getCurTaskNum()+"\t time:"+j.getTaskList().get(0).getTimeRun());
			if(j.finishedRisk() >= 5.0){
				System.out.println("JobName:"+j.getJobName()+"jobRisk:"+j.finishedRisk());
				unfinished.add(new JsonJob(j));
			}
			else{
				schJobs.add(new Job(j));
			}
		}
		jjsr.setUnFinishedlist(unfinished);
		//极小-极小算法调度资源组合
		JobList joblist = new JobList();
		joblist.setJobList(schJobs);
		ArrayList<Resource> relist = select.selectAllResouce();
		System.out.println("资源节点个数："+relist.size());
		Cloud cloud = new Cloud(relist);
		ETCMatrix etcm = new ETCMatrix(joblist,cloud);
		MinScheduler mins = new MinScheduler(etcm);
		JobSchedulerList jsl = mins.minMinScheduler();
		jjsr.setSchedulerJobLists(jsl.getJobSchedulerList());
		return jjsr;
	}
	
	/**
	 * 蚁群算法任务调度
	 * @return 任务调度结果及任务资源组合
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/aco")
	public JsonJobSchedulerResulte getACOSchedulerResult(){
			System.out.println("ACO Scheduler");
			JsonJobSchedulerResulte jjsr = new JsonJobSchedulerResulte();
			Select select = new Select();
			ArrayList<Job> jobli = select.selectAllJob();
			System.out.println("任务量："+jobli.size());
			if(jobli.size() == 0){
				return null;
			}
			ArrayList<JsonJob> unfinished = new ArrayList<JsonJob>();
			ArrayList<Job> schJobs = new ArrayList<Job>();
			//预处理任务调度队列任务
			//找出队列的按当前调度一定不能够按时完成的任务
			for(Iterator<Job> iter = jobli.iterator();iter.hasNext();){
				System.out.println("ACO Scheduler");
				Job j = iter.next();
				j.setTask(1);
				System.out.println(j.getCurTaskNum()+"\t time:"+j.getTaskList().get(0).getTimeRun());
				if(j.finishedRisk() >= 5.0){
					System.out.println("JobName:"+j.getJobName()+"jobRisk:"+j.finishedRisk());
					unfinished.add(new JsonJob(j));
				}
				else{
					schJobs.add(new Job(j));
				}
			}
			jjsr.setUnFinishedlist(unfinished);
			if(schJobs.isEmpty()){
//				jjsr.setSchedulerJobLists(schJobs);
				return jjsr;
			}
			//极小-极小算法调度资源组合
			JobList joblist = new JobList();
			joblist.setJobList(schJobs);
			ArrayList<Resource> relist = select.selectAllResouce();
			System.out.println("资源节点个数："+relist.size());
			Cloud cloud = new Cloud(relist);
			ETCMatrix etcm = new ETCMatrix(joblist,cloud);
			int antNum = joblist.getJobList().size()/3;
			int Max_Gen = 100;
			float alpha = 0.5f,beta = 1.5f,rho = 0.5f;
			JobAco acos = null;
			try {
				acos = new JobAco(etcm,antNum,Max_Gen,alpha,beta,rho);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			acos.ACOScheduler();
			JobSchedulerList jsl = acos.getJobShcedulerList();
			jjsr.setSchedulerJobLists(jsl.getJobSchedulerList());
			return jjsr;
	}
	public static void main(String[] args){
		jobSchedulerResource jsr = new jobSchedulerResource();
		JsonJobSchedulerResulte jjsr = jsr.getACOSchedulerResult();
		System.out.println(jjsr.toString());
	}

}
