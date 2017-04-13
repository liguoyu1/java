/**
 * @author lgy
 * 
 */
package JobScheduler;

import java.util.ArrayList;

public class TaskPack {
	public long taskId;
	public String taskName;
	public int cpu;
	public int mem;
	public long num;
	public ArrayList<Task> taskList;
	//public double[][] resourceTimeAr;
	
	
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
	
	public long getTaskId(){
		return this.taskId;
	}
	
	public String getTaskName(){
		return this.taskName;
	}
	
	public int getTaskCpu(){
		return this.cpu;
	}
	
	public int getTaskMem(){
		return this.mem;
	}
	
//	public JSONObject getTaskJson(){
//		return this.taskJson;
//	}
//	
//	public JSONObject tranTaskJson(){
//		this.taskJson = new JSONObject();
//		taskJson.accumulate("taskId", this.taskId);
//		taskJson.accumulate("taskName", this.taskName);
//		taskJson.accumulate("Cpu", this.cpu);
//		taskJson.accumulate("Mem", this.mem);
//		taskJson.accumulate("Command", this.command);
//		return this.taskJson;
//	}
	
//	public void Init(JSONObject task){
//		Random rand = new Random(System.currentTimeMillis());
//		OptionalLong id = rand.longs(1, 100000000, 999999999).findFirst();
//		System.out.println(id.getAsLong());
//		this.setTaskId(id.getAsLong());
//		
//	}
	
	
	public void setTaskNumber(long num){
		this.num = num;
	}
	
	public void setTaskPack(ArrayList<Task> tasklist){
		this.taskList = tasklist;
	}
	
	public long getTaskNumber(){
		return this.num;
	}
	
	public ArrayList<Task> getTaskPack(){
		return this.taskList;
	}
}
