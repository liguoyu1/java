/**
 * @author lgy
 */
package JobScheduler;

import java.util.OptionalLong;
import java.util.Random;
import net.sf.json.JSONObject;

public class Task {
	public long taskId;
	public String taskName;
	public double cpu;
	public int mem;
	public String command;
	public JSONObject taskJson;
	public double timeRun;
	public int net;
	
	/**
	 * @category for test
	 * @param taskId
	 */
	public Task(long taskId){
		this.Init(taskId);
	}
	
	public Task(Task task){
//		this.taskId = task.getTaskId(
		
	}
	
	public Task(){
		
	}
	
 	public void setNet(int net){
		this.net = net;
	}
	
	public void setTimeRun(long time){
		this.timeRun = time;
	}
	
	public void setTaskId(long taskid){
		this.taskId = taskid;
	}
	
	public void setTaskName(String taskName){
		this.taskName = taskName;
	}
	
	public void setTaskCpu(int cpu){
		this.cpu = cpu;
	}
	
	public void setTaskMem(int mem){
		this.mem = mem;
	}
	
	public void setTaskCommand(String command){
		this.command = command;
	}
	
	public int getNet(){
		return this.net;
	}
	
	public long getTaskId(){
		return this.taskId;
	}
	
	public String getTaskName(){
		return this.taskName;
	}
	
	public double getTaskCpu(){
		return this.cpu;
	}
	
	public int getTaskMem(){
		return this.mem;
	}
	
	public String getTaskCommand(){
		return this.command;
	}	
	
	public void setTaskJson(JSONObject taskJson){
		this.taskJson = taskJson;
	}
	
	public JSONObject getTaskJson(){
		return this.taskJson;
	}
	
	public JSONObject tranTaskJson(){
		this.taskJson = new JSONObject();
		taskJson.accumulate("taskId", this.taskId);
		taskJson.accumulate("taskName", this.taskName);
		taskJson.accumulate("Cpu", this.cpu);
		taskJson.accumulate("Mem", this.mem);
		taskJson.accumulate("Command", this.command);
		return this.taskJson;
	}
	
	public void Init(JSONObject task){
		Random rand = new Random(System.currentTimeMillis());
		OptionalLong id = rand.longs(1, 100000000, 999999999).findFirst();
		System.out.println(id.getAsLong());
		this.setTaskId(id.getAsLong());
		
	}
	
	/**
	 * @category for test
	 * @param taskId
	 */
	public void Init(long taskId){
		Random rand = new Random(System.currentTimeMillis());
		double[] cpus = {0.5,1,1.5,2,2.5,3,3.5,4};
		int[] mems = {128,256,512};
		int[] nets = {1,2,4};
		this.taskId = taskId;
		this.cpu = cpus[rand.nextInt(8)];
		this.mem = mems[rand.nextInt(3)];
		this.command = "ping www.baidu.com";
		this.timeRun = (rand.nextFloat()+0.001)*(rand.nextInt(10)+0.1);
		this.net = nets[rand.nextInt(3)];
	}
	
	public double getTimeRun(){
		return this.timeRun;
	}
	
	public static void main(String[] args){
		Task task = new Task();
		task.Init(null);
	}
	
	
}
