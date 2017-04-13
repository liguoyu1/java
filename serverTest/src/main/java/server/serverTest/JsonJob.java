package server.serverTest;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import JobScheduler.Job;

@XmlRootElement
public class JsonJob {
	@XmlElement
	public long JobId;
	@XmlElement
	public String JobName;
	@XmlElement
	public long UserId;
	@XmlElement
	public String UserName;
	@XmlElement
	public boolean DesignatedArea;
	@XmlElement
	public String place;
	@XmlElement
	public int jobPriority;
	@XmlElement
	public int demoNum; // 开启的实例个数
	@XmlElement
	public int priority;
	@XmlElement
	public long taskNum ;
	@XmlElement
	public double taskRuntime;
	@XmlElement
	public long curTaskNum;
	@XmlElement
	public long DevideTaskSize = 100;
	@XmlElement
	public long timeLimit;					//finished data time    :day:
	@XmlElement
	public long reserveTime;                //reserve time          :zhouqi 
	@XmlElement
	public long lastRunTime;                // 上一次被调度时间
	@XmlElement
	public String docker;
	@XmlElement
	public int jobType;
	
	
	public JsonJob(){}
	
	public JsonJob(Job job){
		this.JobId = job.getJobId();
		this.JobName = job.getJobName();
		this.UserId = job.getUserId();
		this.UserName = job.getUserName();
		this.DesignatedArea = job.getDesignatedArea();
		this.place = job.getPlace();
		this.jobPriority = job.getJobPriority();
		this.demoNum = job.getDemoNum();
		this.priority = job.getPriority();
		this.taskNum = job.getTaskNum();
		this.taskRuntime = job.getTaskRunTime();
		this.curTaskNum = job.getCurTaskNum();
		this.DevideTaskSize = job.getDevideTaskSize();
		this.timeLimit = job.getTimeLimit();
		this.reserveTime = job.getReserveTime();
		this.lastRunTime = job.getLastRunTime();
		this.docker = job.getDocker();
		this.jobType = job.getJobType();
	}
	
	@XmlAttribute 
	public double getTaskRunTime(){
		return this.taskRuntime;
	}
	
	@XmlAttribute 
	public int getDemoNum(){
		return this.demoNum;
	}

	@XmlAttribute 
	public long getReserveTime(){
		return this.reserveTime;
	}
	
	@XmlAttribute 
	public int getPriority(){
		return this.priority;
	}
	
	@XmlAttribute 
	public long getTaskNum(){
		return this.taskNum;
	}
	
	@XmlAttribute 
	public long getCurTaskNum(){
		return this.curTaskNum;
	}
	
	@XmlAttribute 
	public long getJobId(){
		return this.JobId;
	}
	
	@XmlAttribute 
	public String getJobName(){
		return this.JobName;
	}
	
	@XmlAttribute 
	public long getUserId(){
		return this.UserId;
	}
	
	@XmlAttribute 
	public String getUserName(){
		return this.UserName;
	}
	
	@XmlAttribute 
	public long getDevideTaskSize(){
		return this.DevideTaskSize;
	}
	
	@XmlAttribute 
	public int getJobPriority(){
		return this.jobPriority;
	}
	
	@XmlAttribute 
	public boolean getDesignatedArea(){
 		return this.DesignatedArea;
 	}
 	
	@XmlAttribute 
 	public String getDocker(){
 		return this.docker;
 	}

	@XmlAttribute 
 	public String getPlace(){
		return this.place;
	}
 
	@XmlAttribute 
	public long getLastRunTime(){
		return this.lastRunTime;
	}
	
	@XmlAttribute 
 	public long getTimeLimit(){
		return this.timeLimit;
	}

	public int getJobType(){
		return this.jobType;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.toString();
	} 
}
