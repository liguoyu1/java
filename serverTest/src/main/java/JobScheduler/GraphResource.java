package JobScheduler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GraphResource {
	List<VertexResource> R;
	List<ComputationalLoad> S;
	double[][] C;
	double[][] B;
	public String graphName;
	
	public GraphResource(String GraphName){
		this.graphName = GraphName;
		this.R = new ArrayList<VertexResource>();
		this.S = new ArrayList<ComputationalLoad>();
		this.B = null;
		this.C = null;
	}
	
	/**
	 * 构建job的拓扑图
	 * @param
	 * job 
	 */
	public void buildGraph(TopologyJob topologyJob){	
	}
	
	/**
	 * for test
	 */
	public void buildGraph(){
		for(int i = 0; i < 7;i++){
			R.add(new VertexResource(String.valueOf(i)));
		}
		
//		Random rand = new Random();
//		int num = rand.nextInt(4)+1;
//		num = num*8;
//		for(int i = 0;i < num;i++){
//		}
		VertexResource vi = R.get(0);
		LinkedList<EdgeResource> adjV = new LinkedList<EdgeResource>();
		EdgeResource e = new EdgeResource(R.get(1));
		adjV.add(e);
		e = new EdgeResource(R.get(2));
		adjV.add(e);
		e = new EdgeResource(R.get(3));
		adjV.add(e);
		e = new EdgeResource(R.get(5));
		adjV.add(e);
		vi.setListEdge(adjV);
		vi = R.get(1);
		adjV = new LinkedList<EdgeResource>();
		e = new EdgeResource(R.get(4));
		adjV.add(e);
		e = new EdgeResource(R.get(5));
		adjV.add(e);
		vi.setListEdge(adjV);
		vi = R.get(2);
		adjV = new LinkedList<EdgeResource>();
		e = new EdgeResource(R.get(6));
		adjV.add(e);
		vi.setListEdge(adjV);
		vi = R.get(3);
		adjV = new LinkedList<EdgeResource>();
		e = new EdgeResource(R.get(6));
		adjV.add(e);
		vi.setListEdge(adjV);
		vi = R.get(4);
		adjV = new LinkedList<EdgeResource>();
		e = new EdgeResource(R.get(7));
		adjV.add(e);
		vi.setListEdge(adjV);
		vi = R.get(5);
		adjV = new LinkedList<EdgeResource>();
		e = new EdgeResource(R.get(7));
		adjV.add(e);
		vi.setListEdge(adjV);
		vi = R.get(6);
		adjV = new LinkedList<EdgeResource>();
		e = new EdgeResource(R.get(7));
		adjV.add(e);
		vi.setListEdge(adjV);
		vi = R.get(7);
		vi.setListEdge(null);
		for(Iterator<VertexResource> iter = R.iterator();iter.hasNext();){
			iter.next().updateoutDegree();
		}
		C = new double[7][7];
		B = new double[7][7];
		for(int i = 0;i < 8;i++){
			for(int j = 0;j < 8;j++){
				B[i][j] = 0;
				C[i][j] = 0;
			}
		}
		for(Iterator<VertexResource> iterV = R.iterator();iterV.hasNext();){
			VertexResource vj = iterV.next();
			for(Iterator<EdgeResource> iterE = vj.getEdges().iterator();iterE.hasNext();){
				EdgeResource ei = iterE.next();
				int index = Integer.parseInt(vj.getVertexLable());
				int indey = Integer.parseInt(ei.endVertex.getVertexLable());
				Random rand = new Random();
				C[index][indey] = rand.nextInt(10);
				B[index][indey] = 1;
			}
		}
	}
	
	/**
	 * print graph
	 */

}
