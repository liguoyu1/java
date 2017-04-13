/**
 * @author lgy
 * 
 */
package JobScheduler;

public class EdgeJob {
	public VertexJob endVertex;
	public int weight;
	
	public EdgeJob(VertexJob endVertex){
		this.endVertex = endVertex;
		this.weight = 0;
	}
	
	public void setWeight(int weight){
		this.weight = weight;
	}
}
