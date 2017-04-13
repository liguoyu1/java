/**
 * @author lgy
 * 
 */
package DescribeLiner;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


@SuppressWarnings("serial")
public class DrawPoint extends ApplicationFrame{
	
	public static String Describe;
	public static String X_Descirbe;
	public static String Y_Describe;
	public static String additoinDescribe;

 	public DrawPoint(String s,String x,String y,double[] X,double[] Y)
    {
        super(s);
        this.setXDescribe(x);
        this.setDescribe(s);
        this.setYDescribe(y);
        JPanel jpanel = createDemoPanel(X,Y);
        jpanel.setPreferredSize(new Dimension(2000, 800));
        setContentPane(jpanel);
    }
	
	public static DefaultXYDataset createXYPointData(double[] X,double[] Y){
		DefaultXYDataset xydata = new DefaultXYDataset();
	   	double[][] data = new double[2][X.length];
	   	data[0] = X;
	   	data[1] = Y;
	   	
	   	xydata.addSeries("line", data);
	   	return xydata;
	}
 
	public static DefaultXYDataset createXYLineDatasets(double[] X,double[] Y)
    {
    	DefaultXYDataset defaultkeyedvalues = new DefaultXYDataset();
	   	
	   	int mmm = X.length;
		int m = 3;
		double[] a = new double[X.length];
		double[] aa = PolyFitForcast.PolyFit(X, Y, mmm, a, m);
        int qiefen = 1;
        double increase = 1.0/qiefen;
		int length = (int) (X[X.length-1] * qiefen + 1);
		double[][] data = new double[2][length];
	   	for(int i = 0;0+i*increase <= X[X.length-1];i++){
	   		data[0][i] = 0 + i*increase;
	   		data[1][i] = PolyFitForcast.getY(0+i*increase, X, aa, m);
	   	}
	   	defaultkeyedvalues.addSeries("point", data);
        return defaultkeyedvalues;
    }
    
	public static JFreeChart createChart(DefaultXYDataset[] acategorydataset)
    {
        JFreeChart jfreechart = ChartFactory.createXYLineChart(Describe, X_Descirbe, Y_Describe,
        		acategorydataset[1], PlotOrientation.VERTICAL, true, true, false);
        jfreechart.addSubtitle(new TextTitle("By Programming Language"));
        jfreechart.addSubtitle(new TextTitle("As at 5 March 2016"));
        jfreechart.setBackgroundPaint(Color.white);
        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        xyplot.setNoDataMessage("no data!");  
        xyplot.setNoDataMessageFont(new Font("", Font.BOLD, 14));  
        xyplot.setNoDataMessagePaint(Color.red);  
        XYLineAndShapeRenderer lineandshaperenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();
        lineandshaperenderer.setSeriesLinesVisible(0, true);
        lineandshaperenderer.setSeriesLinesVisible(1, true);
        lineandshaperenderer.setSeriesShapesVisible(1, true);
        
      //修改范围轴。 我们将默认刻度值 （允许显示小数） 改成只显示整数的刻度值。
    	NumberAxis numberaxis = (NumberAxis) xyplot.getRangeAxis();
    	numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        xyplot.setDataset(1, acategorydataset[0]);
        return jfreechart;
    }
 
    @SuppressWarnings("deprecation")
	public static JFreeChart createChart(CategoryDataset[] categorydata){
    	JFreeChart jfreechart = ChartFactory.createLineChart(Describe, X_Descirbe, Y_Describe,
    			categorydata[0], PlotOrientation.VERTICAL, true, false, false);
    	jfreechart.setBackgroundPaint(Color.white);
    	CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
    	categoryplot.setBackgroundPaint(Color.white);
    	categoryplot.setRangeGridlinePaint(Color.black);
    	categoryplot.setRangeGridlinesVisible(true);

    	//修改范围轴。 我们将默认刻度值 （允许显示小数） 改成只显示整数的刻度值。
    	NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
    	numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    
    	CategoryAxis domainAxis = categoryplot.getDomainAxis();
    	domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90); // 设置X轴上的Lable让其45度倾斜     
    	domainAxis.setLowerMargin(0.0);		// 设置距离图片左端距离 
    	domainAxis.setUpperMargin(0.0);		// 设置距离图片右端距离 
    	LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot.getRenderer();
//    	categoryplot.setDataset()
    	lineandshaperenderer.setShapesVisible(true);
    	lineandshaperenderer.setDrawOutlines(true);
    	lineandshaperenderer.setUseFillPaint(true);
    	lineandshaperenderer.setBaseFillPaint(Color.white);
    	lineandshaperenderer.setStroke(new BasicStroke(1.0F));
    	lineandshaperenderer.setOutlineStroke( new BasicStroke(1.0F));
    	lineandshaperenderer.setShape( new Ellipse2D.Double(-0.0, -0.0,0.0, 0.0));
        return jfreechart;
    }
 
    @SuppressWarnings("deprecation")
	public static JFreeChart createChart(DefaultXYDataset xyData){
    	
    	JFreeChart jfreechart = ChartFactory.createXYLineChart(Describe, X_Descirbe, Y_Describe,
    			xyData, PlotOrientation.VERTICAL, true, true, false);
    	jfreechart.setBackgroundPaint(Color.white);
    	XYPlot categoryplot = (XYPlot) jfreechart.getPlot();
    	categoryplot.setBackgroundPaint(Color.white);
    	categoryplot.setRangeGridlinePaint(Color.black);
    	categoryplot.setRangeGridlinesVisible(true);
    
    	ValueAxis domainAxis = categoryplot.getDomainAxis();
    	domainAxis.setLowerMargin(0.0);		// 设置距离图片左端距离 
    	domainAxis.setUpperMargin(0.0);		// 设置距离图片右端距离 
    	XYLineAndShapeRenderer curxyItem = new XYLineAndShapeRenderer();
    	curxyItem.setShape(new Ellipse2D.Double(-1.0, -1.0,1.0, 1.0));
    	curxyItem.setSeriesLinesVisible(0,false);
    	curxyItem.setSeriesShapesVisible(1,false);
    	curxyItem.setSeriesLinesVisible(1,true);
    	curxyItem.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
    	categoryplot.setRenderer(curxyItem);
//    	categoryplot.setDataset(1,categorydata);
    	
    	//修改范围轴。 我们将默认刻度值 （允许显示小数） 改成只显示整数的刻度值。
    	NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
    	numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        return jfreechart;
    }
    
    public static CategoryDataset createLineCategoryDataset(double[] X,double[] Y){
    	DefaultCategoryDataset categoryData1 = new DefaultCategoryDataset();
    	
    	int mmm = X.length;
		int m = 15;
		double[] a = new double[X.length];
		double[] aa = PolyFitForcast.PolyFit(X, Y, mmm, a, m);

        int qiefen = 2;
        double increase = 1.0/qiefen;
		int length = (int) (X[X.length-1] * qiefen + 1);
	   	for(int i = 0;i < length;i++){
//	   		if((int)(0+i*increase) == 0+i*increase||i == 0){
//	   			categoryData1.addValue(Y[(int)(0+i*increase)], "real", String.valueOf(0+i*increase));
//	   		}
//	   		else{
//	   			double fk = (Y[(int)(0+i*increase)+1]-Y[(int)(0+i*increase)])/(X[(int)(0+i*increase)+1]-X[(int)(0+i*increase)]);
//	   			double fb = Y[(int)(0+i*increase)]- X[(int)(0+i*increase)]*fk;
//	   			categoryData1.addValue(fk*(0+i*increase)+fb, "real",String.valueOf(0+i*increase));
//	   		}
	   		categoryData1.addValue(PolyFitForcast.getY(0+i*increase, X, aa, m), String.valueOf(m)+"-predict", String.valueOf(0+i*increase));
	   	}
	   	
    	return categoryData1;
    }
    
    public static CategoryDataset createCategoryDataset(double[] X,double[] Y){
    	DefaultCategoryDataset categorydata = new DefaultCategoryDataset();
    	for(int i = 0;i < X.length;i++){
    		categorydata.addValue(Y[i], "point", String.valueOf(X[i]));
    	}
    	return categorydata;
    } 
    
    public static DefaultXYDataset createXYData(double[] X,double[] Y){
    	DefaultXYDataset xydata = new DefaultXYDataset();
	   	double[][] data1 = new double[2][X.length];
	   	data1[0] = X;
	   	data1[1] = Y;
	   	xydata.addSeries("point", data1);
	   	
	   	int mmm = X.length;
		int m = mmm/3 ;
		double[] a = new double[X.length];
		double[] aa = PolyFitForcast.PolyFit(X, Y, mmm, a, m);
        int qiefen = 10;
        double increase = 1.0/qiefen;
		int length = (int) (X[X.length-1] * qiefen + 1);
		double[][] data2 = new double[2][length];
	   	for(int i = 0;0+i*increase <= X[X.length-1];i++){
	   		data2[0][i] = 0 + i*increase;
	   		data2[1][i] = PolyFitForcast.getY(0+i*increase, X, aa, m);
	   	}
	   	xydata.addSeries("line", data2);
    	return xydata;
    }
    
    public static JPanel createDemoPanel(double[] X,double[] Y)
    {
	   	DefaultXYDataset xyData = createXYData(X,Y);  	
	   	JFreeChart jfreechart = createChart(xyData);
        return new ChartPanel(jfreechart);
    }
    
    public static JPanel drawLineAndPointGraph(double[] X,double[] Y){
    	DefaultXYDataset xyData = createXYData(X,Y);
    	JFreeChart chart = createChart(xyData);
    	JPanel showPanel = new ChartPanel(chart);
//    	RefineryUtilities.centerFrameOnScreen(showPanel);
//    	showPanel.setVisible(true);
    	return showPanel;
    }
    
    @SuppressWarnings("static-access")
	public void setDescribe(String describe){
    	this.Describe = describe;
    }
    
    @SuppressWarnings("static-access")
	public void setXDescribe(String X_describe){
    	this.X_Descirbe = X_describe;
    }
    
    @SuppressWarnings("static-access")
	public void setYDescribe(String Y_describe){
    	this.Y_Describe = Y_describe;
    }
    
    public static void main(String args[])
    {
    	double[] taskNum = {1000d,2000d,3000d,4000d,5000d,6000d,7000d};
		double[] TaskTime = {8.01,17.61,27.81,38.21,49.21,60.41,71.75};
    	DrawPoint paretochartdemo1 = new DrawPoint("draw","CPU","Time(s)",taskNum,TaskTime);
        paretochartdemo1.pack();
        RefineryUtilities.centerFrameOnScreen(paretochartdemo1);
        paretochartdemo1.setVisible(true);
    }

}
