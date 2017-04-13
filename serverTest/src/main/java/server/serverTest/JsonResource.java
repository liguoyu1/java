package server.serverTest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Random;
import CloudR.Resource;

@XmlRootElement
public class JsonResource {
	@XmlElement
	public long reId;
	@XmlElement
	public double Cpu;
	@XmlElement
	public double Cpu_Speed;
	@XmlElement
	public double Mem;
	@XmlElement
	public double Disk;
	@XmlElement
	public double Net;
	@XmlElement
	public double Speedcalculate;//计算速度   未确定如何定义
	@XmlElement
	public double CpuUsed;
	@XmlElement
	public double MemUsed;
	@XmlElement
	public double DiskUsed;
	@XmlElement
	public double NetUsed;
	@XmlElement
	public String place;
	@XmlElement
	public double tasks_running;   // 节点执行的任务数
	@XmlElement
	public double load;   // 负载
	@XmlElement
	public double executors_running;   //执行器
	
	public JsonResource(){}
	
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
	
	public void setMemUsed(double l){
		this.MemUsed = l;
	}
	
	public void setDiskUsed(double diskused){
		this.DiskUsed = diskused;
	}
	
	public void setNetUsed(double netused){
		this.NetUsed = netused;
	}
	
	public void setTaskRunning(double taskRunning){
		this.tasks_running = taskRunning;
	}
	
	public void setLoad(double load){
		this.load = load;
	}
	
	public void setExecutorsRunning(double executors_running){
		this.executors_running = executors_running;
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
	
	public JsonResource(Resource re){
		this.Cpu = re.getCpu();
		this.Cpu_Speed = re.getCpuSpeed();
		this.CpuUsed = re.getCpuUsed();
		this.Mem = re.getMem();
		this.Disk = re.getDisk();
		this.MemUsed = re.getMemUsed();
		this.DiskUsed = re.getDiskUsed();
//		this.Net = re.getNet();
//		this.NetUsed = re.getNetUsed();
		this.reId = re.getResourceId();
		this.place = re.getResourcePlace();
		this.Speedcalculate = re.getSpeedCalculate();
		this.tasks_running = re.getTaskRunning();
		this.load = re.getLoad();
		this.executors_running = re.getExecutorsRunning();
	}
	
	@Override
	public String toString() {
		return this.toString();
	} 
}
