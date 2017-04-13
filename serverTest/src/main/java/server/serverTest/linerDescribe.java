
package server.serverTest;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import JobScheduler.Job;
import Mysql.Select;
import PaasListener.SlaverListener;

@Path("line")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class linerDescribe {
	/**
	 * 获取任务的执行时间估计拟合曲线参数
	 * 获取拟合曲线的训练点集合
	 * @param jobId  任务Id
	 * @return 拟合曲线参数，点集合
	 */
	@GET
	@Path("/jobline/{jobId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Liner getJobRunTimeLine(@PathParam("jobId") long jobId){
		System.out.println("Get Job Run Time Line!");
		System.out.println(jobId);
		Select select = new Select();
		Job job = select.selectJob(jobId);
		System.out.println(job.getJobType());
		Liner liner = select.selectBestTestTimeArray(job.getJobType());
		liner.setParams();
		liner.setTitle("Job "+job.getJobName()+"runtime liner");
		liner.show();
		return liner;
	}	
	
	public static void main(){
		try {
			System.out.println(SlaverListener.readJsonFromUrl("http://localhost:8080/Server/line/jobline/796242843").toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
