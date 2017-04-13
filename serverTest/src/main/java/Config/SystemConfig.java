package Config;

public class SystemConfig {
	// mysql 数据库 配置
	public static final String url = "jdbc:mysql://192.168.205.128:3306/Scheduler";  
    public static final String name = "com.mysql.jdbc.Driver";  
    public static final String user = "root";  
    public static final String password = "root";
    
    //slave 节点的IP 文件路径 资源监控 配置
    public static final String slaveIpFile = "slaveIpFile.txt";
    public static final String slaveurllatter = ":5051/metrics/snapshot";
    
    // log 运行日志 配置
    public static final String baseFilePath = "/home/logs";
    
    
    
}
