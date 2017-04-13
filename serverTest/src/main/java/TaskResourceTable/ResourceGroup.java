/**
 * @author lgy
 * 
 */
package TaskResourceTable;

import JobScheduler.Job;
import dealdata.MyRandom;

public class ResourceGroup {
	public String jobName;
	public long jobId;
	public double[] CPUs;
	public int[] Mems;
	public int[] Nets;
	public int cpu_div;
	public int mem_div;
	public int net_div;
	
	double[][] CpuCombMemCost;
	double[][] CpuCombNetCost;
	
	public void setJob(Job job){
		this.jobName = job.getJobName();
		this.jobId = job.getJobId();
	}
	
	public String getJobName(){
		return this.jobName;
	}
	
	public long getJobId(){
		return this.jobId;
	}
	
	public void setCpus(double[] cpus){
		this.CPUs = cpus;
		this.cpu_div = cpus.length;
	}
	
	public void setMems(int[] mems){
		this.Mems = mems;
		this.mem_div = mems.length;
	}
	
	public void setNets(int[] nets){
		this.Nets = nets;
		this.net_div = nets.length;
	}
	
	public void InitCpuCombMemCost(double[][] cpuCombMemCost){
		this.CpuCombMemCost = cpuCombMemCost;
	}
	
	public double getCpu(int index){
		if(index < this.CPUs.length)
			return this.CPUs[index];
		return -1;
	}
	
	public int getMem(int index){
		if(index < this.Mems.length)
			return this.Mems[index];
		return -1;
	}
	
	public int getNets(int index){
		if(index < this.Nets.length){
			return this.Nets[index];
		}
		return -1;
	}
	
	public double[][] getCpuCombMeCost(){
		return this.CpuCombMemCost;
	}
	
	public double getMaxTime(){
//		System.out.println(this.CpuCombMemCost.length);
		return this.CpuCombMemCost[0][0];
	} 
	
	public double getMinTime(){
		return this.CpuCombMemCost[this.mem_div-1][this.cpu_div-1];
	}
	
	//for test
	public void setCpus(){
		this.cpu_div = 5;
		CPUs = new double[]{0.5,1,2,4,8};
	}
	
	public void setMems(){
		this.mem_div = 4;
		Mems = new int[]{512,1024,2048,4096};
	}
	
	public void setNets(){
		this.net_div = 5;
		Nets = new int[]{1,2,5,10,20};
	}
	
	public void InitCpuCombMemCost(){
		this.setCpus();
		this.setMems();
		this.setNets();
		this.CpuCombMemCost = new double[mem_div][cpu_div];
		MyRandom rand = new MyRandom();
		double MaxCost = rand.randFloat(16, 31);//小时 
		for(int i = 0;i < this.mem_div;i ++){
			double max_row = MaxCost;
			for(int j = 0;j < this.cpu_div;j++){
				this.CpuCombMemCost[i][j] = max_row;
				max_row /= 2;
			}
			MaxCost /= 2;
		}
	}

	
	public double getSuitTime() {
		// TODO Auto-generated method stub
		return this.CpuCombMemCost[mem_div/2][cpu_div/2];
//		return 0;
	}

	public void show(){
		System.out.println("jobName:"+this.jobName);
		System.out.println("Job Id:"+this.jobId);
		System.out.println("cost array:");
		for(int i = 0;i < this.mem_div;i++){
			for(int j = 0;j < this.cpu_div;j++){
				System.out.print(this.CpuCombMemCost[i][j]+"\t");
			}
			System.out.println();
		}
	}

	public double getFirstCpu() {
		// TODO Auto-generated method stub
		return this.CPUs[0];
	}

	public double getSuitCpu() {
		// TODO Auto-generated method stub
		return this.CPUs[this.cpu_div/2];
	}

	public double getLastCpu() {
		// TODO Auto-generated method stub
		return this.CPUs[this.cpu_div-1];
	}

	public int getFisrtMem() {
		// TODO Auto-generated method stub
		return this.Mems[0];
	}
	
	public int getSuitMem() {
		// TODO Auto-generated method stub
		return this.Mems[this.mem_div/2];
	}

	public int getLastMem() {
		// TODO Auto-generated method stub
		return this.Mems[mem_div-1];
	}
}
