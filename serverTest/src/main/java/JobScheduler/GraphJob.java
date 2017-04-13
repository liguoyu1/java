/**
 * @author lgy
 * 
 */
package JobScheduler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GraphJob {
	List<VertexJob> V;
	List<ComputationalLoad> W;
	double[][] D;
	double[][] E;
	public String graphName;
	
	public GraphJob(String GraphName){
		this.graphName = GraphName;
		this.V = new ArrayList<VertexJob>();
		this.W = new ArrayList<ComputationalLoad>();
		this.D = null;
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
		for(int i = 0; i < 8;i++){
			V.add(new VertexJob(String.valueOf(i)));
		}
		
//		Random rand = new Random();
//		int num = rand.nextInt(4)+1;
//		num = num*8;
//		for(int i = 0;i < num;i++){
//		}
		VertexJob vi = V.get(0);
		LinkedList<EdgeJob> adjV = new LinkedList<EdgeJob>();
		EdgeJob e = new EdgeJob(V.get(0));
		adjV.add(e);
		e = new EdgeJob(V.get(1));
		adjV.add(e);
		e = new EdgeJob(V.get(2));
		adjV.add(e);
		e = new EdgeJob(V.get(3));
		adjV.add(e);
		e = new EdgeJob(V.get(5));
		adjV.add(e);
		vi.setListEdge(adjV);
		vi = V.get(1);
		adjV = new LinkedList<EdgeJob>();
		e = new EdgeJob(V.get(4));
		adjV.add(e);
		e = new EdgeJob(V.get(5));
		adjV.add(e);
		vi.setListEdge(adjV);
		vi = V.get(2);
		adjV = new LinkedList<EdgeJob>();
		e = new EdgeJob(V.get(6));
		adjV.add(e);
		vi.setListEdge(adjV);
		vi = V.get(3);
		adjV = new LinkedList<EdgeJob>();
		e = new EdgeJob(V.get(6));
		adjV.add(e);
		vi.setListEdge(adjV);
		vi = V.get(4);
		adjV = new LinkedList<EdgeJob>();
		e = new EdgeJob(V.get(7));
		adjV.add(e);
		vi.setListEdge(adjV);
		vi = V.get(5);
		adjV = new LinkedList<EdgeJob>();
		e = new EdgeJob(V.get(7));
		adjV.add(e);
		vi.setListEdge(adjV);
		vi = V.get(6);
		adjV = new LinkedList<EdgeJob>();
		e = new EdgeJob(V.get(7));
		adjV.add(e);
		vi.setListEdge(adjV);
		vi = V.get(7);
		vi.setListEdge(null);
		for(Iterator<VertexJob> iter = V.iterator();iter.hasNext();){
			iter.next().updateoutDegree();
		}
		D = new double[8][8];
		E = new double[8][8];
		for(int i = 0;i < 8;i++){
			for(int j = 0;j < 8;j++){
				D[i][j] = 0;
				E[i][j] = 0;
			}
		}
		for(Iterator<VertexJob> iterV = V.iterator();iterV.hasNext();){
			VertexJob vj = iterV.next();
			for(Iterator<EdgeJob> iterE = vj.getEdges().iterator();iterE.hasNext();){
				EdgeJob ei = iterE.next();
				int index = Integer.parseInt(vj.getVertexLable());
				int indey = Integer.parseInt(ei.endVertex.getVertexLable());
				Random rand = new Random();
				D[index][indey] = rand.nextInt(10);
				E[index][indey] = 1;
			}
		}
	}
	
	/**
	 * print graph
	 */
	
}
