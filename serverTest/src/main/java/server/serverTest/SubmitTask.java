package server.serverTest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import JobScheduler.Job;
import Mysql.Insert;
import Mysql.Select;

@Path("/Job")
@Produces({MediaType.APPLICATION_JSON})
public class SubmitTask {
	/**
	 * 提交任务
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
	@Path("/submit")
    public boolean submit() throws ClassNotFoundException, SQLException {
		System.out.println("Submit Job!");
		Job job = new Job();
		Insert insert = new Insert();
		job.Init();
		job.show();
		try {
			if(insert.insertJob(job)<=0){
//				return new JsonJob(job);
//				return job;
				return false;
			}	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		return new JsonJob(job);
		return true;
//		return job;
    }
	
	/**
	 * 获取所有提交待调度任务
	 * @return
	 * @throws InterruptedException
	 */
	@GET
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/allJob")
	public JsonJobList getAllJob() throws InterruptedException{
		System.out.println("Get All Job!");
		ArrayList<Job> joblist = null;
		Select select = new Select();
		joblist = select.selectAllJob();
//		joblist.get(0).show();
		JsonJobList jsonjoblist = new JsonJobList();
		for(Iterator<Job> iter = joblist.iterator();iter.hasNext();){
			JsonJob jobll = new JsonJob(iter.next());
			jsonjoblist.addJsonJob(jobll);
		}
		return jsonjoblist;
	}
	
	/**
	 * @param jobName
	 * @return
	 * @throws InterruptedException
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/job/{jobName}")
	public JsonJob getJob(@PathParam("jobName") String jobName) throws InterruptedException{
		System.out.println("Get Job by jobName!");
		JsonJob jsonJob = null;
		Job job = null;
		Select select = new Select();
		try {
			job = select.selectJob(jobName);
			jsonJob = new JsonJob(job);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		job.show();
		return jsonJob;
	}
	
	/**
	 * 获取 job 的执行风险值
	 * @param jobId
	 * @return
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/jobRisk/{JobId}")
	public Risk getRisk(@PathParam("JobId") long jobId){
		System.out.println("Get Job Risk by Job Id!");
//		Risk re = new Risk(12313,"fsadfdsa");
		Select select = new Select();
		Risk risk = select.selectJobRisk(jobId);
		return risk;
	}
}
