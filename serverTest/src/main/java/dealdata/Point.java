/**
 * @author lgy
 * 
 */
package dealdata;

public class Point {
	double X;
	double Y;
	
	public Point(){
		
	}
	
	public Point(double x,double y){
		this.X = x;
		this.Y = y;
	}
	
	public void setPoint(double x,double y){
		this.X = x;
		this.Y = y;
	}
	
	public double getPoint_X(){
		return this.X;
	}
	
	public double getPoint_Y(){
		return this.Y;
	}
	
	public Point getPoint(){
		return this;
	}
}
