package Mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

import CloudR.Resource;
import Config.SystemConfig;
import Docker.Docker;
import JobScheduler.Job;
import dealdata.MyRandom;
import dealdata.PaasDate;
import server.serverTest.Liner;
import server.serverTest.Risk;

public class Select {
	public static final String url = SystemConfig.url;  
    public static final String name = SystemConfig.name;  
    public static final String user = SystemConfig.user;  
    public static final String password = SystemConfig.password; 
    
    public Select(){
    	
    }
    
    /**
     * 根据jobName 查询Job
     * @param jobName
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Job selectJob(String jobName) throws ClassNotFoundException, SQLException{
    	Job job = null;
        Class.forName("com.mysql.jdbc.Driver");
		Connection conn;
        conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement(); //创建Statement对象
        try{
	        String sql = "select * from Job where JobName=\'"+jobName+"\'";    //要执行的SQL
	        ResultSet rs = stmt.executeQuery(sql);//创建数据对象
	        if (rs.next()){
	        	job = new Job();
	        	job.setJobId(rs.getLong(1));
	        	job.setJobName(rs.getString(2));
	        	job.setJobType(rs.getInt(3));
	        	job.setUserId(rs.getLong(4));
	        	job.setUserName(rs.getString(5));
	        	job.setDesignatedArea(rs.getBoolean(6));
	        	job.setPlace(rs.getString(7));
	        	job.setJobPriority(rs.getInt(8));
	        	job.setDemoNum(rs.getInt(9));
	        	job.setPriority(rs.getInt(10));
	        	job.setTaskNumber(rs.getLong(11));
	        	job.setTaskRunTime(rs.getDouble(12));
	        	job.setCurrentTaskNumber(rs.getLong(13));
	        	job.setDevideTaskSize(rs.getLong(14));
	        	job.setTimeLimit(rs.getLong(15));
	        	job.setReserveTime(rs.getLong(16));
	        	job.setLastRunTime(rs.getLong(17));
	        }
	        rs.close();
	        stmt.close();
	        conn.close();
        }
    	catch(Exception e)
        {
            e.printStackTrace();
        }
    	return job;
    }
    
    /**
     * 获取所有的提交任务
     * @return 返回 任务列表
     */
    public ArrayList<Job> selectAllJob(){
    	ArrayList<Job> joblist = new ArrayList<Job>();
        try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connection conn = null;
        try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //创建Statement对象
        try{
	        String sql = "select * from Job";    //要执行的SQL
	        ResultSet rs = stmt.executeQuery(sql);//创建数据对象
	        if(rs.wasNull()){
	        	return null;
	        }
	        while (rs.next()){
	        	Job job = new Job();
	        	job.setJobId(rs.getLong(1));
	        	job.setJobName(rs.getString(2));
	        	job.setJobType(rs.getInt(3));
	        	job.setUserId(rs.getLong(4));
	        	job.setUserName(rs.getString(5));
	        	job.setDesignatedArea(rs.getBoolean(6));
	        	job.setPlace(rs.getString(7));
	        	job.setJobPriority(rs.getInt(8));
	        	job.setDemoNum(rs.getInt(9));
	        	job.setPriority(rs.getInt(10));
	        	job.setTaskNumber(rs.getLong(11));
	        	job.setTaskRunTime(rs.getDouble(12));
	        	job.setCurrentTaskNumber(rs.getLong(13));
	        	job.setDevideTaskSize(rs.getLong(14));
	        	job.setTimeLimit(rs.getLong(15));
	        	job.setReserveTime(rs.getLong(16));
	        	job.setLastRunTime(rs.getLong(17));
	        	joblist.add(job);
	        }
	        rs.close();
	        stmt.close();
	        conn.close();
        }
    	catch(Exception e)
        {
            e.printStackTrace();
        }
        return joblist;
    }
    
    /**
     * 根据JobId 查询Job
     * @param jobId
     * @return
     */
    public Job selectJob(long jobId){
    	Job job = null;
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
	        String sql = "select * from Job where JobId="+jobId;    //要执行的SQL
	        ResultSet rs = stmt.executeQuery(sql);//创建数据对象
	        job = new Job();
	        if (rs.next()){
	        	job.setJobId(rs.getLong(1));
	        	job.setJobName(rs.getString(2));
	        	job.setJobType(rs.getInt(3));
	        	job.setUserId(rs.getLong(4));
	        	job.setUserName(rs.getString(5));
	        	job.setDesignatedArea(rs.getBoolean(6));
	        	job.setPlace(rs.getString(7));
	        	job.setJobPriority(rs.getInt(8));
	        	job.setDemoNum(rs.getInt(9));
	        	job.setPriority(rs.getInt(10));
	        	job.setTaskNumber(rs.getLong(11));
	        	job.setTaskRunTime(rs.getDouble(12));
	        	job.setCurrentTaskNumber(rs.getLong(13));
	        	job.setDevideTaskSize(rs.getLong(14));
	        	job.setTimeLimit(rs.getLong(15));
	        	job.setReserveTime(rs.getLong(16));
	        	job.setLastRunTime(rs.getLong(17));
	        }
	        rs.close();
	        stmt.close();
	        conn.close();
        }
    	catch(Exception e)
        {
            e.printStackTrace();
        }
    	return job;
    }
    
    /**
     * 获取所有资源
     * @return 返回 资源列表
     */
    public ArrayList<Resource> selectAllResouce(){
    	ArrayList<Resource> relist = new ArrayList<Resource>();
    	try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connection conn = null;
        try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //创建Statement对象
        try{
	        String sql = "select * from Resource";    //要执行的SQL
	        ResultSet rs = stmt.executeQuery(sql);//创建数据对象
//	        if(rs.wasNull()){
//	        	return null;
//	        }
	        System.out.println("Select Resource!");
	        while (rs.next()){
	        	Resource re = new Resource();
	        	re.setreId(rs.getLong(1));
	        	re.setResourcePlace(rs.getString(2));
	        	re.setCpu(rs.getDouble(3));
	        	re.setCpuUsed(rs.getDouble(4));
	        	re.setMem(rs.getInt(5));
	        	re.setMemUsed(rs.getInt(6));
	        	re.setDisk(rs.getInt(7));
	        	re.setDiskUsed(rs.getInt(8));
	        	re.setLoad(rs.getFloat(9));
	        	re.setTaskRunning(rs.getInt(10));
	        	re.setExecutorsRunning(rs.getInt(11));
	        	relist.add(re);
	        }
	        rs.close();
	        stmt.close();
	        conn.close();
        }
    	catch(Exception e)
        {
            e.printStackTrace();
        }
        return relist;
    }
    
    /**
     * 根据资源id 获取数据库中的资源信息
     * @param reId
     * @return
     */
    public Resource selectResource(long reId){
    	Resource re = null;
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
	        String sql = "select SQL_CACHE * from Resource where resourceId="+reId;    //要执行的SQL
	        ResultSet rs = stmt.executeQuery(sql);//创建数据对象
	        if (rs.next()){
	        	re = new Resource();
	        	re.setreId(rs.getLong(1));
	        	re.setResourcePlace(rs.getString(2));
	        	re.setCpu(rs.getDouble(3));
	        	re.setCpuUsed(rs.getDouble(4));
	        	re.setMem(rs.getInt(5));
	        	re.setMemUsed(rs.getInt(6));
	        	re.setDisk(rs.getInt(7));
	        	re.setDiskUsed(rs.getInt(8));
	        	re.setLoad(rs.getFloat(9));
	        	re.setTaskRunning(rs.getInt(10));
	        	re.setExecutorsRunning(rs.getInt(11));
	        }
	        rs.close();
	        stmt.close();
	        conn.close();
        }
    	catch(Exception e)
        {
            e.printStackTrace();
        }
    	return re;
    }
    
    /**
     * 根据资源place 获取数据库中的资源信息
     * @param place
     * @return
     */
    public ArrayList<Resource> selectResourceByPlace(String place){
    	ArrayList<Resource> relist = new ArrayList<Resource>();
    	try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connection conn = null;
        try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //创建Statement对象
        try{
	        String sql = "select * from Resource where resourcePlace=\'"+place+"\'";    //要执行的SQL
	        ResultSet rs = stmt.executeQuery(sql);//创建数据对象
	        if(rs.wasNull()){
	        	return null;
	        }
	        while (rs.next()){
	        	Resource re = new Resource();
	        	re.setreId(rs.getLong(1));
	        	re.setResourcePlace(rs.getString(2));
	        	re.setCpu(rs.getDouble(3));
	        	re.setCpuUsed(rs.getDouble(4));
	        	re.setMem(rs.getInt(5));
	        	re.setMemUsed(rs.getInt(6));
	        	re.setNet(rs.getInt(7));
	        	re.setNetUsed(rs.getInt(8));
	        	re.setDisk(rs.getInt(9));
	        	re.setDiskUsed(rs.getInt(10));
	        	re.setSpeedCalculate(rs.getInt(11));
	        	relist.add(re);
	        }
	        rs.close();
	        stmt.close();
	        conn.close();
        }
    	catch(Exception e)
        {
            e.printStackTrace();
        }
        return relist;
    }
    
    /**
     * 返回任务的执行风险
     * @param jobId
     * @return
     */
	public Risk selectJobRisk(long jobId){
    	Job job = new Job();
    	Risk risk = null;
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
	        String sql = "select * from Risk where JobId="+jobId;    //要执行的SQL
	        System.out.println(sql);
	        ResultSet rs = stmt.executeQuery(sql);//创建数据对象
	        if(rs.wasNull()){
	        	job = this.selectJob(jobId);
	        	if(job == null){
	        		return null;
	        	}
	        	job.setTask(1l);
	        	float riskValue = (float) job.finishedRisk();
	        	Timestamp date = PaasDate.getNowDate();
	        	risk = new Risk(jobId,riskValue,date);
	        	System.out.println(jobId+"\t"+riskValue+"\t"+date);
	        	Insert insert = new Insert();
	        	insert.insertJobRisk(risk);
	        }
	        else{
	        	rs.next();
	        	risk = new Risk(rs.getLong(1),rs.getFloat(2),rs.getTimestamp(3));
	        }
	        rs.close();
	        stmt.close();
	        conn.close();
        }
    	catch(Exception e)
        {
            e.printStackTrace();
        }
        return risk;
    }
    
	/**
	 * 数据库查询所有Docker镜像信息
	 * @return 镜像列表
	 */
	public ArrayList<Docker> selectAllDocker(){
		ArrayList<Docker> dockerList = null;
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
        	
	        String sql = "select * from Docker";    //要执行的SQL
	        ResultSet rs = stmt.executeQuery(sql);//创建数据对象
        	dockerList = new ArrayList<Docker>();
        	System.out.println("select docker!");
        	while(rs.next()){
        		Docker docker = new Docker();
        		docker.setDockerMd5(rs.getString(1));
        		docker.setDocker(rs.getString(2));
        		docker.setPath(rs.getString(3));
        		docker.setTime(rs.getString(4));
        		dockerList.add(docker);
        	}
	        rs.close();
	        stmt.close();
	        conn.close();
        }
    	catch(Exception e)
        {
            e.printStackTrace();
        }
		return dockerList;
	}
	
	/**
	 * 依据DockerMd5 获取docker
	 * @param dockerMd5
	 * @return
	 */
	public Docker selectDockerByMD5(String dockerMd5){
		Docker docker = null;
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
	        String sql = "select * from Docker where dockerMd5=\'"+dockerMd5+"\'";    //要执行的SQL
	        ResultSet rs = stmt.executeQuery(sql);//创建数据对象
        	docker = new Docker();
        	while(rs.next()){
        		docker.setDockerMd5(rs.getString(1));
        		docker.setDocker(rs.getString(2));
        		docker.setPath(rs.getString(3));
        		docker.setTime(rs.getString(4));
        	}
	        rs.close();
	        stmt.close();
	        conn.close();
        }
    	catch(Exception e)
        {
            e.printStackTrace();
        }
		return docker;
	}

	/**
	 * 依据dockerName 获取docker 信息
	 * @param dockerName
	 * @return
	 */
	public Docker selectDockerByName(String dockerName){
		Docker docker = null;
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
	        String sql = "select * from Docker where docker=\'"+dockerName+"\'";    //要执行的SQL
	        ResultSet rs = stmt.executeQuery(sql);//创建数据对象
        	docker = new Docker();
        	while(rs.next()){
        		docker.setDockerMd5(rs.getString(1));
        		docker.setDocker(rs.getString(2));
        		docker.setPath(rs.getString(3));
        		docker.setTime(rs.getString(4));
        	}
	        rs.close();
	        stmt.close();
	        conn.close();
        }
    	catch(Exception e)
        {
            e.printStackTrace();
        }
		return docker;
	}
	
	/**
	 * 依据 JobType 获取最有调度
	 * @param JobType
	 * @return 
	 */
	public Liner selectBestTestTimeArray(int JobType){
		Liner linerD = null;
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
	        //要执行的SQL
	        String sql = "select runTime,taskNum from taskTestResult where jobType="+JobType+" and resourceId="+this.selectReMinTime(JobType);
	        System.out.println(sql);
	        ResultSet rs = stmt.executeQuery(sql);//创建数据对象
	        if(rs.next()){
	        	linerD = new Liner();
	        	linerD.setPoint(rs.getInt(2),rs.getFloat(1));
	        	while(rs.next()){
	        		System.out.println(rs.getInt(2)+","+rs.getFloat(1));
	        		linerD.setPoint(rs.getInt(2),rs.getFloat(1));
	        	}
	        }
	        rs.close();
	        stmt.close();
	        conn.close();
        }
    	catch(Exception e)
        {
            e.printStackTrace();
        }
		return linerD;
	}
	
	/**
	 * 获取最小执行时间资源节点Id
	 * @param JobType
	 * @return 资源Id  
	 */
	public long selectReMinTime(int JobType){
		long reId = 0;
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
	        String sql = "select min(runTime),jobType,resourceId from taskTestResult group by jobType";    //要执行的SQL
	        ResultSet rs = stmt.executeQuery(sql);//创建数据对象
//	        int jobT = -1;
	        while(rs.next()){
	        	if(rs.getInt(2) == JobType){
	        		reId = rs.getLong(3);
	        	}
	        }
	        rs.close();
	        stmt.close();
	        conn.close();
        }
    	catch(Exception e)
        {
            e.printStackTrace();
        }
        return reId;
	}
	
	public void testMysql(long fin){
		ArrayList<Resource> relist = this.selectAllResouce();
		int len = relist.size();
		long start = System.currentTimeMillis();
		MyRandom myrand = new MyRandom();
		for(int i = 0;i <= 50000*fin;i++){
			int index = myrand.randInt(0, len);
			long reid = relist.get(index).getResourceId();
			for(Iterator<Resource> iter = relist.iterator();iter.hasNext();){
				Resource re = iter.next();
				if(reid == re.getResourceId()){
					re.show();
					break;
				}
			}
//			this.selectResource(reid);
			
		}
		long end = System.currentTimeMillis();
		System.out.println((end-start)/1000+"s");
	}
	
    public static void main(String[] args) throws ClassNotFoundException, SQLException{
    	Select select = new Select();
//    	Risk job = select.selectJobRisk(500049955);
//    	if(job == null){
//    		return ;
//    	}
//    	System.out.println(job.getJobId()+"\t"+job.getJobRisk()+"\t"+job.getDateTime());
//    	System.out.println("Start ... ...");
//    	select.testMysql(1);
//    	System.out.println("End");
    	Job job = select.selectJob(796242843);
    	job.show();
    	long reId = select.selectReMinTime(job.getJobType());
    	System.out.println(reId+","+job.toString());
    	Liner line = select.selectBestTestTimeArray(job.getJobType());
    	line.setParams();
    	line.show();
    }
}
