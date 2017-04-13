/**
 * @author lgy
 */
package JobScheduler;

import java.util.LinkedList;

public class VertexJob {
	public String vertexLable; //顶点标识
	public LinkedList<EdgeJob> adjEdges;
	public int outDegree; //该顶点的出度
	
	public VertexJob(String vertexLable){
		this.vertexLable = vertexLable;
		outDegree = 0;
		adjEdges = new LinkedList<EdgeJob>();
	}
	
	public void setListEdge(LinkedList<EdgeJob> adjEdges){
		this.adjEdges = adjEdges;
	}
	
	public void setoutDegree(int outDegree){
		this.outDegree = outDegree;
	}
	
	public void updateoutDegree(){
		this.outDegree = this.adjEdges.size();
	}
	
	public LinkedList<EdgeJob> getEdges(){
		return this.adjEdges;
	}
	
	public String getVertexLable(){
		return this.vertexLable;
	}

}
