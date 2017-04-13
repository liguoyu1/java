/**
 * @author lgy
 * 
 */
package CloudR;

import java.util.Random;

import org.json.JSONObject;

import dealdata.IP2Long;
import dealdata.MyRandom;
import server.serverTest.JsonResource;

public class Resource {
	public String ip;
	public long reId;
	public double Cpu;
	public double Cpu_Speed;
	public double Mem;
	public double Disk;
	public double Net;
	public double Speedcalculate;//计算速度   未确定如何定义
	public double CpuUsed;
	public double MemUsed;
	public double DiskUsed;
	public double NetUsed;
	public String place;
	public double tasks_running;   // 节点执行的任务数
	public double load;   // 负载
	public double executors_running;   //执行器
	
	
	public void setTaskRunning(double taskRunning){
		this.tasks_running = taskRunning;
	}
	
	public void setLoad(double load){
		this.load = load;
	}
	
	public void setExecutorsRunning(double executors_running){
		this.executors_running = executors_running;
	}
	
	public void setResourcePlace(String place){
		this.place = place;
	}
	
	public Resource() throws InterruptedException{
		Random rand = new Random(System.currentTimeMillis());
		MyRandom myrand = new MyRandom();
		this.setreId(myrand.randInt(10000, 99999));
		this.setResourcePlace(myrand.randString(8));
		this.setCpu(rand.nextInt(128)+128);
		this.setDisk(1024*1024*1024);
		this.setMem(1024*1024);
		this.setNet(20);
		this.setSpeedCalculate(rand.nextInt(4));
		this.setCpuUsed(0);
		this.setMemUsed(0);
		this.setDiskUsed(0);
		this.setNetUsed(0);
		this.setCpuSpeed();
//		Thread.sleep(10);
	}
	
	public Resource(JSONObject jsonRe,String ip){
		try{
			this.ip = ip;
			this.Cpu = (Double) jsonRe.get("slave/cpus_total");
			this.CpuUsed = (Double) jsonRe.get("slave/cpus_used");
			this.Mem = (Double) jsonRe.get("slave/mem_total");
			this.MemUsed = (Double) jsonRe.get("slave/mem_used");
			this.Disk = (Double) jsonRe.get("slave/disk_total");
			this.DiskUsed = (Double) jsonRe.get("slave/disk_used");
			this.reId = IP2Long.ipToNumber(ip);
			this.place = IP2Long.GetAddressByIp(ip);
			this.load = (Double) jsonRe.get("system/load_1min");
			this.tasks_running = (Double) jsonRe.get("slave/tasks_running");
			this.executors_running = (Double) jsonRe.get("slave/executors_running");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Resource(double num_cpu,int num_mem,int num_disk,int num_net,int speed){
		this.setCpu(num_cpu);
		this.setDisk(num_disk);
		this.setMem(num_mem);
		this.setNet(num_net);
		this.setSpeedCalculate(speed);
		this.setCpuUsed(0);
		this.setMemUsed(0);
		this.setDiskUsed(0);
		this.setNetUsed(0);
		this.setCpuSpeed();
	}
	
	public Resource(Resource re) {
		// TODO Auto-generated constructor stub
		this.Cpu = re.getCpu();
		this.Cpu_Speed = re.getCpuSpeed();
		this.CpuUsed = re.getCpuUsed();
		this.Mem = re.getMem();
		this.Disk = re.getDisk();
		this.MemUsed = re.getMemUsed();
		this.DiskUsed = re.getDiskUsed();
		this.Net = re.getNet();
		this.NetUsed = re.getNetUsed();
		this.reId = re.getResourceId();
		this.place = re.getResourcePlace();
	}

	public Resource(JsonResource re){
		this.Cpu = re.getCpu();
		this.Cpu_Speed = re.getCpuSpeed();
		this.CpuUsed = re.getCpuUsed();
		this.Mem = re.getMem();
		this.Disk = re.getDisk();
		this.MemUsed = re.getMemUsed();
		this.DiskUsed = re.getDiskUsed();
		this.Net = re.getNet();
		this.NetUsed = re.getNetUsed();
		this.reId = re.getResourceId();
		this.place = re.getResourcePlace();
		this.Speedcalculate = re.getSpeedCalculate();
	}
	
	public void setResourceUsed(double cpu_used,double mem_used,double disk_used,double net_used){
		this.setCpuUsed(cpu_used);
		this.setMemUsed(mem_used);
		this.setDiskUsed(disk_used);
		this.setNetUsed(net_used);
		this.setCpuSpeed();
	}
	
	public void setCpu(double num_cpu){
		this.Cpu = num_cpu;
	}

	public void setreId(long resourceId){
		this.reId = resourceId;
	}
	
	public void setCpuSpeed(){
		Random rand = new Random(System.currentTimeMillis()+4);
		double speed[] = {2.1d,2.3d,2.7d,3.0d,3.2};
		this.Cpu_Speed = speed[rand.nextInt(5)];
	}
	
	public void setMem(double num_mem){
		this.Mem = num_mem;
	}
	
	public void setDisk(double num_disk){
		this.Disk = num_disk;
	}
	
	public void setNet(double num_net){
		this.Net = num_net;
	}
	
	public void setSpeedCalculate(double speed){
		this.Speedcalculate = speed;
	}
	
	public void setCpuUsed(double d){
		this.CpuUsed = d;
	}
	
	public void setMemUsed(double d){
		this.MemUsed = d;
	}
	
	public void setDiskUsed(double diskused){
		this.DiskUsed = diskused;
	}
	
	public void setNetUsed(double netused){
		this.NetUsed = netused;
	}
	
	public long getResourceId(){
		return this.reId;
	}
	
	public double getCpuUsed(){
		return this.CpuUsed;
	}
	
	public double getMemUsed(){
		return this.MemUsed;
	}
	
	public double getDiskUsed(){
		return this.DiskUsed;
	}
	
	public double getNetUsed(){
		return this.NetUsed;
	}
	
	public double getSpeedCalculate(){
		return this.Speedcalculate;
	}
	
	public double getCpu(){
		return this.Cpu;
	}
	
	public double getCpuSpeed(){
		return this.Cpu_Speed;
	}
	
	public double getMem(){
		return this.Mem;
	}
	
	public double getDisk(){
		return this.Disk;
	}
	
	public double getNet(){
		return this.Net;
	}
	
	public double getUnUseCpu(){
		return this.Cpu-this.CpuUsed;
	}
	
	public double getUnUseMem(){
		return this.Mem - this.MemUsed;
	}
	
	public double getUnUseNet(){
		return this.Net - this.NetUsed;
	}
	
	public String getResourcePlace(){
		return this.place;
	}

	public double getTaskRunning(){
		return this.tasks_running;
	}
	
	public double getLoad(){
		return this.load;
	}
	
	public double getExecutorsRunning(){
		return this.executors_running;
	}
	
	public void updateResource() {
		// TODO Auto-generated method stub
		this.setCpuUsed(0);
		this.setMemUsed(0);
	}
	
	public void Init(){
//		Random rand = new Random(System.currentTimeMillis());
//		double[] cpuSpeed = {2.1,2.3,2.5,2.7,3.0,3.1,3.3,3.7};
//		this.Resource(128d,1024*128,1024*1024*2,20,rand.nextInt(8));
	}
	
	/**
	 * @category 节点信息素的初始化
	 * @return pheromone 资源的初始信息素的值
	 */
	public double initPheromone(){
		double pheromone = 0;
		pheromone  = this.Cpu*this.Cpu_Speed + this.Net;
		return pheromone;
	}
	
	/**
	 * @category 资源节点的实时信息素
	 * @return curPheromone 节点的当前信息素
	 */
	public double currentPheromone(double alpha,double beta){
		double curPheromone = 0;
		double pheromone = this.initPheromone();
		curPheromone = Math.pow((this.Cpu-this.CpuUsed)*this.Cpu_Speed, alpha)*Math.pow(pheromone, beta);
		return curPheromone;
	}

	public void show() {
		// TODO Auto-generated method stub
		System.out.println("resourceId:"+this.reId+"\tpalce:"+this.place);
		System.out.println("Cpu:"+this.Cpu+"\tMem:"+this.Mem+"\tNet:"+this.Net+"\tDisk:"+this.Disk);
//		System.out.println("resourceId:"+this.reId+"\tpalce:"+this.place);
	}
	
}
