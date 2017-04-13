package JobScheduler;

public class EdgeResource {
	public VertexResource endVertex;
	public int weight;
	
	public EdgeResource(VertexResource endVertex){
		this.endVertex = endVertex;
		this.weight = 0;
	}
	
	public void setWeight(int weight){
		this.weight = weight;
	}

}
