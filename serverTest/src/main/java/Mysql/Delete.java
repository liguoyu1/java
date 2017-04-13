package Mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

import Config.SystemConfig;

public class Delete {
	public static final String url = SystemConfig.url;  
    public static final String name = SystemConfig.name;  
    public static final String user = SystemConfig.user;  
    public static final String password = SystemConfig.password; 
    
    public Delete(){
    	
    }
    
    /**
     * 删除 job 根据jobId
     * @param jobId
     * @return 返回是否删除成功
     */
    public boolean deleteJobById(long jobId){
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
	        String sql = "delete from Job where JobId="+jobId;    //要执行的SQL
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
     * 删除所有Job
     * @return 返回是否
     */
    public boolean deleteAllJob(){
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
	        String sql = "delete from Job";    //要执行的SQL
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
     * 删除docker 依据dockerMd5
     * @param dockerMd5
     * @return 返回删除状态，是否成功删除
     */
    public boolean deleteDockerByDockerMd5(String dockerMd5){
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
	        String sql = "delete from Docker where dockerMd5=\'"+dockerMd5+"\'";    //要执行的SQL
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
     * 删除所有Docker 
     * @param docker
     * @return 返回删除状态，是否删除成功
     */
    public boolean deleteDockerByDocker(String docker){
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
	        String sql = "delete from Docker where docker=\'"+docker+"\'";    //要执行的SQL
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
   
    public boolean deleteAllResource(){
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
	        String sql = "delete from Resource";    //要执行的SQL
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
}
