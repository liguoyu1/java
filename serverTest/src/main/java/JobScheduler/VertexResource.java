package JobScheduler;

import java.util.LinkedList;

public class VertexResource {
	public String vertexLable; //顶点标识
	public LinkedList<EdgeResource> adjEdges;
	public int outDegree; //该顶点的出度
	
	public VertexResource(String vertexLable){
		this.vertexLable = vertexLable;
		outDegree = 0;
		adjEdges = new LinkedList<EdgeResource>();
	}
	
	public void setListEdge(LinkedList<EdgeResource> adjEdges){
		this.adjEdges = adjEdges;
	}
	
	public void setoutDegree(int outDegree){
		this.outDegree = outDegree;
	}
	
	public void updateoutDegree(){
		this.outDegree = this.adjEdges.size();
	}
	
	public LinkedList<EdgeResource> getEdges(){
		return this.adjEdges;
	}
	
	public String getVertexLable(){
		return this.vertexLable;
	}
}
