/**
 * @author lgy
 * 
 */
package JobScheduler;

public class ComputationalLoad {
	public int computationalLoad;
	public float cpu;
	public int mem;
	public int net;
	public long disk;
	
	public ComputationalLoad(int computationalLoad){
		this.computationalLoad = computationalLoad;
	}
	
	public ComputationalLoad(){
		this.cpu = 0.5f;
		this.mem = 128;
		this.net = 10;
		this.disk = 1024;
	}

}
