/**
 * @author lgy
 * 
 */
package DescribeLiner;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultXYDataset;

/**
 * 
 * @author paasu
 *
 */

public class graphData{
	DefaultXYDataset xyData;
	DefaultCategoryDataset categoryData;
	
	public graphData(DefaultXYDataset xydata,DefaultCategoryDataset categoryData){
		this.xyData = xydata;
		this.categoryData = categoryData;
	}
	
	public graphData(){
		this.categoryData = new DefaultCategoryDataset();
		this.xyData = new DefaultXYDataset();
	}
	
	public DefaultXYDataset getXYData(){
		return this.xyData;
	}
	
	public DefaultCategoryDataset getCategoryDataset(){
		return this.categoryData;
	}
}
