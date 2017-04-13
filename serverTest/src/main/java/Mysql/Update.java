package Mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

import CloudR.Resource;
import Config.SystemConfig;
import Docker.Docker;
import JobScheduler.Job;

public class Update {
	public static final String url = SystemConfig.url;  
    public static final String name = SystemConfig.name;  
    public static final String user = SystemConfig.user;  
    public static final String password = SystemConfig.password; 
    
    public Update(){}
    
    /**
     * 更新docker 
     * @param docker
     * @return 返回是否更新成功
     */
    public boolean updateDocker(Docker docker){
    	return true;
    }
    
    /**
     * 更新 job ,Job 表
     * @param job
     * @return 是否成功更新
     */
    public boolean updateJob(Job job){
        try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connection conn = null;
        try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //创建Statement对象
        try{
        	job.updateReserveTime();
	        String sql = "update Job set curTaskNum="+job.getCurTaskNum()
	        +",reserveTime="+job.getReserveTime()+",timeLimit="+job.getTimeLimit()+" where JobId="+job.getJobId();    //要执行的SQL
//	        ResultSet rs = stmt.executeQuery(sql);//创建数据对象
//	        rs.close();
	        PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            int result=ps.executeUpdate();//返回行数或者0
            if(result>0){
                return true;
            }
	        stmt.close();
	        conn.close();
        }
    	catch(Exception e)
        {
            e.printStackTrace();
        }
    	return false;
    }
    
    /**
     * 更新 Resource 
     * @param re
     * @return 是否更新成功
     */
    public boolean updateResource(Resource re){
    	try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connection conn = null;
        try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //创建Statement对象
        try{
	        String sql = "update Resource set cpu_used="+re.getCpuUsed()+",mem_used="+re.getMemUsed()
	        +",disk_used="+re.getDiskUsed()+",Systemload="+re.getLoad()+",tasksRunning="+re.getTaskRunning()
	        +",executesRunning="+re.getExecutorsRunning()+" where resourceId="+re.getResourceId();    //要执行的SQL
//	        ResultSet rs = stmt.executeQuery(sql);//创建数据对象
//	        rs.close();
	        PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            int result=ps.executeUpdate();//返回行数或者0
            if(result>0){
                return true;
            }
	        stmt.close();
	        conn.close();
        }
    	catch(Exception e)
        {
            e.printStackTrace();
        }
    	return false;
    }
    
    public static void main(String[] args){
    	
    	Update update = new Update();
//    	Job job = new Job();
//    	job.setJobId(500049955);
//    	job.setJobName("uwxs9w1hkc");
//    	job.setTimeLimit(1489650580000l+411867783l+45215621315l);
//    	if(update.updateJob(job)){
//    		System.out.println("update Job success!");
//    	}
    	
    	Resource re = null;
		try {
			re = new Resource();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		re.Init();
    	re.setreId(60408);
    	re.setCpuUsed(12);
    	re.setDiskUsed(24);
    	re.setMemUsed(32);
    	re.setNetUsed(4);
    	if(update.updateResource(re)){
    		System.out.println("Update resource success!");
    	}
    }
    
}
