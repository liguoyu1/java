/**
 * @author lgy
 * 
 */
package CloudR;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;


@SuppressWarnings("rawtypes")
class ResourceCpuSort implements Comparator{
	public int compare(Object o1,Object o2){
		Resource obj1 = (Resource)o1;
		Resource obj2 = (Resource)o2;
		if(obj1.getUnUseCpu() < obj2.getUnUseCpu()){
			return 1;
		}
		else if(obj1.getUnUseCpu() == obj2.getUnUseCpu()){
			return 0;
		}
		else{
			return -1;
		}
	}
}

@SuppressWarnings("rawtypes")
class ResourceMemSort implements Comparator{
	public int compare(Object o1,Object o2){
		Resource obj1 = (Resource)o1;
		Resource obj2 = (Resource)o2;
		if(obj1.getUnUseMem() < obj2.getUnUseMem()){
			return 1;
		}
		else if(obj1.getUnUseMem() == obj2.getUnUseMem()){
			return 0;
		}
		else{
			return -1;
		}
	}
}



public class Cloud {
	public String roler;
	public int reNum;
	public ArrayList<Resource> resource;
	public int[][] bandWidth;    // communication bandWidth
	
	public Cloud(String roler){
		this.setRoler(roler);
		this.setResourceNum(6);
	}
	
	public Cloud(){
		this.setRoler("root");
		this.setResourceNum(16);
	}
	
	public Cloud(String roler,int reNum){	
		this.reNum = reNum;
		this.roler = roler;
	}
	
	public Cloud(Cloud cloud) {
		// TODO Auto-generated constructor stub
		this.roler = cloud.getRoler();
		this.reNum = cloud.getResourceNum();
		this.resource = new ArrayList<Resource>();
		for(Iterator<Resource> iter = cloud.getResource().iterator();iter.hasNext();){
			resource.add(new Resource(iter.next()));
		}
//		this.bandWidth = cloud.getBandWidth();
//		this.bandWidth = new int[reNum][reNum];
//		for(int i = 0;i < reNum;i++){
//			for(int j = 0;j < reNum;j++){
//				bandWidth[i][j] = cloud.getBandWidth()[i][j];
//			}
//		}
	}

	public Cloud(ArrayList<Resource> relist){
		this.resource = relist;
		this.reNum = relist.size();
		this.roler = "userTest";
	}
	
	/**
	 * for test
	 * @param reNum
	 * @throws InterruptedException 
	 */
	public void Init() throws InterruptedException{
		String[] places = {"shandong","Beijing",
				"Shanghai","Guangzhou","Tianjin","Chongqing"};
		this.bandWidth = new int[reNum][reNum];
		Random rand = new Random(System.currentTimeMillis());
		this.resource = new ArrayList<Resource>();
		for(int i = 0;i < reNum;i++){
			Resource re = new Resource();
			re.setResourcePlace(places[rand.nextInt(6)]);
			re.setreId(i);
			resource.add(re);
			for(int j = 0;j < i;j++){
				this.bandWidth[i][j] = (int) Math.min(resource.get(i).getNet(), resource.get(j).getNet());
				this.bandWidth[j][i] = (int) Math.min(resource.get(j).getNet(), resource.get(i).getNet());
			}
		}
	}
	
	public void setRoler(String roler){
		this.roler = roler;
	}
	
	public String getRoler(){
		return this.roler;
	}
	
	public void setResource(ArrayList<Resource> resource){
		this.resource = resource;
	}
	
	public ArrayList<Resource> getResource(){
		return this.resource;
	}
	
	public void setBandWidth(int[][] bandWidth){
		this.bandWidth = bandWidth;
	}
	
	public int[][] getBandWidth(){
		return this.bandWidth;
	}
	
	public void setResourceNum(int reNum){
		this.reNum = reNum;
	}
	
	public int getResourceNum(){
		return this.reNum;
	}
	
	public void show(){
		this.showResource();
		
		System.out.println("Communication bandWidth:");
		for(int i = 0;i < reNum;i++){
			for(int j = 0;j < reNum;j++){
//				DecimalFormat df = new DecimalFormat("")
				System.out.printf("%2d ",bandWidth[i][j]);
			}
			System.out.println();
		}
	}
	
	public void showResource(){
		System.out.println("Player:"+this.roler);
		System.out.println("Resource :");
		for(int i = 0;i < reNum;i++){
			System.out.println(i+" place:"+resource.get(i).getResourcePlace()
					+" CPU:"+resource.get(i).getCpu()+" USED:"+resource.get(i).getCpuUsed()
					+" Mem:"+resource.get(i).getMem()+" USED:"+resource.get(i).getMemUsed()
					+" Disk:"+resource.get(i).getDisk()+" USED:"+resource.get(i).getDiskUsed()
					+" Net:"+resource.get(i).getNet()+" USED:"+resource.get(i).getNetUsed());
		}
	}
	
	public static void main(String[] args) throws InterruptedException{
		Cloud cloud = new Cloud("root",6);
		cloud.Init();
		for(int i = 0;i < 5;i ++){
			Resource re = cloud.getResourceOne();
			re.setCpuUsed(re.getCpuUsed() + 50);
			cloud.getResourceOne();
			cloud.showResource();
		}
//		cloud.show();
	}

	
	public Resource getResource(String string) {
		// TODO Auto-generated method stub
		for(int i = 0;i < this.reNum;i++){
			if(resource.get(i).getResourcePlace() == string){
				return resource.get(i);
			}
		}
		return null;
	}
	
	public Resource getResource(int index){
		return this.resource.get(index);
	}

	@SuppressWarnings("unchecked")
	public Resource getResourceOne() {
		// TODO Auto-generated method stub
		Collections.sort(resource,new ResourceCpuSort());
		return resource.get(0);
	}
	
	public void test(){
		
	}

	public double getResourceCpu() {
		// TODO Auto-generated method stub
		double usrCpu = 0;
		for(Iterator<Resource> iter = this.resource.iterator();
				iter.hasNext();){
			usrCpu += iter.next().getUnUseCpu();
		}
		return usrCpu;
	}

	public long getResourceMem() {
		// TODO Auto-generated method stub
		long usrMem = 0;
		for(Iterator<Resource> iter = this.resource.iterator();
				iter.hasNext();){
			usrMem += iter.next().getUnUseMem();
		}
		return usrMem;
	}

	public HashMap<Resource, Integer> CloudResourceScheduler(double cpu,long mem,long curnum){
		HashMap<Resource,Integer> resourceSch = new HashMap<Resource,Integer>();
		double Cpu = cpu;
		long Mem = mem;
		long curNum = curnum;
		while(curNum != 0){
			Resource re = this.getResourceOne();
			double usrCpu = re.getUnUseCpu();
			double usrMem =  re.getUnUseMem();
			int curMaxNum = Math.min((int) (usrCpu/Cpu),(int) (usrMem/Mem));
			curMaxNum = Math.min(curMaxNum, (int)curNum);
			if(curMaxNum <= 0){
				break;
			}
			re.setCpuUsed(re.getCpuUsed()+Cpu*curMaxNum);
			re.setMemUsed(re.getMemUsed()+Mem*curMaxNum);
			resourceSch.put(re, new Integer(curMaxNum));
			System.out.printf("并发：%d \t可分配并发：%d\n",curNum,curMaxNum);
			curNum -= curMaxNum;
		}
		return resourceSch;
	}
	
	public void updateCloudResource() {
		// TODO Auto-generated method stub
		for(Iterator<Resource> iter = this.resource.iterator();iter.hasNext();){
			iter.next().updateResource();
		}
	}
}
