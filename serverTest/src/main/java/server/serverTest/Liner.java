package server.serverTest;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import DescribeLiner.PolyFitForcast;

@XmlRootElement
public class Liner {
	@XmlElement
	ArrayList<JsonDocker> jDockerList;
	@XmlElement
	public ArrayList<Double> params;
	@XmlElement
	public ArrayList<Integer> X = null;
	@XmlElement
	public String x_Lab = "taskSize(.)";
	@XmlElement
	public ArrayList<Double> Y;
	@XmlElement
	public String y_Lab = "runtime(s)";
	@XmlElement
	public String title;
	
	public Liner(){}
	
	/**
	 * 设置拟合曲线标题
	 * @param title  标题名
	 */
	public void setTitle(String title){
		this.title = title;
	}
	
	/**
	 * 初始化曲线拟合的拟合点
	 * @param x   任务量
	 * @param y   执行时间
	 */
	public void setPoint(int x,double y){
		if(this.X == null)
			this.X = new ArrayList<Integer>();
		if(this.Y == null){
			this.Y = new ArrayList<Double>();
		}
		this.X.add(new Integer(x));
		this.Y.add(new Double(y));
		if(this.params == null){
			this.params = new ArrayList<Double>();
		}
	}
	
	/**
	 * 计算估计时间曲线参数
	 */
	public void setParams(){
		int len = this.X.size();
		if(len <= 0){
			return ;
		}
		double[] x = new double[len];
		double[] y = new double[len];
		for(int i = 0;i < len;i++){
			x[i] = (double)this.X.get(i);
			y[i] = this.Y.get(i);
		}
		int highm = (int)(len/3);
		double[] para = new double[highm+1];
		PolyFitForcast.PolyFit(x, y, len, para,highm);
		if(this.params == null){
			this.params = new ArrayList<Double>();
		}
		for(int i = 0;i < highm;i++){
			this.params.add(new Double(para[i]));
		}
	}

	
	public void show() {
		// TODO Auto-generated method stub
		System.out.println(this.title);
		System.out.println(this.X.toString());
		System.out.println(Y.toString());
		System.out.println(this.params.toString());
	}

}
