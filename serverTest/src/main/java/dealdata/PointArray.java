/**
 * @author lgy
 * 
 */
package dealdata;

import java.util.ArrayList;
import java.util.Iterator;

public class PointArray {
	public ArrayList<Point> pointArray;
	public String describe;
	public String X_describe;
	public String Y_descirbe;
	public String addation_descirbe;
	
	public PointArray(){
		pointArray = new ArrayList<Point>();
	}
	
	public void addPoint(Point point){
		pointArray.add(point);
	}
	
	public void setDescribe(String describe){
		this.describe = describe;
	}
	
	public void setAddationDescirbe(String other){
		this.addation_descirbe = other;
	}
	
	public ArrayList<Point> getPointArray(){
		return this.pointArray;
	}
	
	public String getDescribe(){
		return this.describe;
	}
	
	public String getAddationDescirbe(){
		return this.addation_descirbe;
	}
	
	public String getXDescribe(){
		return this.X_describe;
	}
	
	public void setXDescribe(String XDescribe){
		this.X_describe = XDescribe;
	}
	
	public String getYDescirbe(){
		return this.Y_descirbe;
	}
	
	public void setYDescribe(String YDescribe){
		this.Y_descirbe = YDescribe;
	}
	
	public double[][] getDPointArray(){
		double[][] PointXY = new double[2][this.pointArray.size()];
		int i = 0;
		for(Iterator<Point> iter = this.pointArray.iterator();
				iter.hasNext();){
			Point t = iter.next();
			PointXY[0][i] = t.getPoint_X();
			PointXY[1][i] = t.getPoint_Y();
			i ++;
		}
		return PointXY;
	}
	
	public void show(){
		System.out.println(this.describe);
		System.out.println(this.X_describe+"\t\t\t"+this.Y_descirbe);
//		System.out.println(this.pointArray.size());
		for(Iterator<Point> iter = this.pointArray.iterator();
				iter.hasNext();){
			Point p = iter.next();
			System.out.println(String.valueOf(p.getPoint_X())+"\t"+
			String.valueOf(p.getPoint_Y()));
		}
	}
}
