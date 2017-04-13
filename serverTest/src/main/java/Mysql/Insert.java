package Mysql;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import CloudR.Resource;
import Config.SystemConfig;
import Docker.Docker;
import JobScheduler.Job;
import server.serverTest.Risk;

public class Insert {
	public static final String url = SystemConfig.url;  
    public static final String name = SystemConfig.name;  
    public static final String user = SystemConfig.user;  
    public static final String password = SystemConfig.password; 
    
//    public Connection conn;  
//    public PreparedStatement pst;
    
    public Insert() throws ClassNotFoundException, SQLException{
//    	Class.forName(name);
//    	this.conn = (Connection)DriverManager.getConnection(url, user, password);
//    	System.out.println(conn.toString());
    }
    
    /**
     * 
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int insertJobRisk(Risk risk) throws SQLException, ClassNotFoundException{
    	int i = 0;
    	Class.forName(name);
    	Connection conn = (Connection)DriverManager.getConnection(url, user, password);
        String sql = "insert into Risk (JobId,jobRisk,time) values(?,?,?)";
        PreparedStatement pst = conn.prepareStatement(sql);
    	pst.setLong(1,risk.getJobId());
    	pst.setFloat(2, risk.getJobRisk());
    	pst.setTimestamp(3, risk.getDateTime());
        i = pst.executeUpdate();
        pst.close();
//        conn.commit();
        conn.close();
        System.out.println(i);
        return i;
    }
    
    /**
     * 
     * @param job
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public int insertJob(Job job) throws ClassNotFoundException, SQLException{
    	int i = 0;
    	Class.forName(name);
    	Connection conn = (Connection)DriverManager.getConnection(url, user, password);
        String sql = "insert into Job (JobId,jobName,JobType,UserId,UserName,DesignatedArea,"+
        		"place,jobPriority,demoNum,priority,taskNum,taskRuntime,"+
        		"curTaskNum,DevideTaskSize,timeLimit,reserveTime,lastRunTime) values("+
        		"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pst = conn.prepareStatement(sql);
    	pst.setLong(1, job.getJobId());
    	pst.setString(2, job.getJobName());
    	pst.setInt(3, job.getJobType());
    	pst.setLong(4, job.getUserId());
    	pst.setString(5, job.getUserName());
    	pst.setBoolean(6, job.getDesignatedArea());
    	pst.setString(7, job.getPlace());
    	pst.setInt(8, job.getJobPriority());
    	pst.setInt(9, job.getDemoNum());
    	pst.setInt(10, job.getPriority());
    	pst.setLong(11, job.getTaskNum());
    	pst.setDouble(12, job.getTaskRunTime());
    	pst.setLong(13, job.getCurTaskNum());
    	pst.setLong(14, job.getDevideTaskSize());
    	pst.setFloat(15, job.getTimeLimit());
    	pst.setFloat(16, job.getReserveTime());
    	pst.setFloat(17, job.getLastRunTime());
        i = pst.executeUpdate();
        pst.close();
//        conn.commit();
        conn.close();
        return i;
    }
    
    /**
     * 
     * @param docker
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int insertDocker(Docker docker) throws SQLException, ClassNotFoundException{
    	int state = 0;
    	Class.forName(name);
    	Connection conn = (Connection)DriverManager.getConnection(url, user, password);
        String sql = "insert into Docker (dockerMd5,docker,Path,time) values(?,?,?,?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, docker.getDockerMd5());
        pst.setString(2, docker.getDocker());
        pst.setString(3, docker.getPath());
        pst.setString(4, docker.getTime());
        state = pst.executeUpdate();
        pst.close();
//        conn.commit();
        conn.close();
    	return state;
    }
    
    /**
     * 
     * @param re
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public int insertResource(Resource re) throws ClassNotFoundException, SQLException{
    	int state = 0;
    	Class.forName(name);
    	Connection conn = (Connection)DriverManager.getConnection(url, user, password);
        String sql = "insert into Resource (resourceId,resourcePlace,cpu,cpu_used,mem,mem_used,disk,disk_used,Systemload,tasksRunning ,executesRunning) values(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setLong(1, re.getResourceId());
        pst.setString(2, re.getResourcePlace());
        pst.setDouble(3, re.getCpu());
        pst.setDouble(4, re.getCpuUsed());
        pst.setDouble(5, re.getMem());
        pst.setDouble(6, re.getMemUsed());
        pst.setDouble(7, re.getDisk());
        pst.setDouble(8, re.getDiskUsed());
        pst.setDouble(9, re.getLoad());
        pst.setDouble(10, re.getTaskRunning());
        pst.setDouble(11, re.getExecutorsRunning());
        state = pst.executeUpdate();
        pst.close();
//        conn.commit();
        conn.close();
    	return state;	
    }

//    public int insert
    public static void main(String[] args) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException, InterruptedException{
    	Insert insert =new Insert();

    	for(int i = 0; i < 16;i ++){
//    		Job job = new Job();
//    		job.Init();
//    		Docker docker = new Docker();
//    		docker.Init();
    		Resource re = new Resource();
    		re.Init();
    		insert.insertResource(re);
    	}
    }
}
