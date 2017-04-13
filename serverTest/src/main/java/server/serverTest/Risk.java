package server.serverTest;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Risk {
	@XmlElement
	public float jobRisk ;
	@XmlElement
	public long JobId;
	@XmlElement
	public Timestamp datatime;
	
	public Risk(){}
	
	public Risk(long JobId,float jobRisk,Timestamp datatime){
		this.JobId = JobId;
		this.jobRisk = jobRisk;
		this.datatime = datatime;
	}
	
	public void setJobRisk(float jobRisk){
		this.jobRisk = jobRisk;
	}
	
	public void setJobId(long JobId){
		this.JobId = JobId;
	}
	
	public void setDataTime(Timestamp datatime){
		this.datatime = datatime;
	}
	
	public long getJobId(){
		return this.JobId;
	}
	
	public float getJobRisk(){
		return this.jobRisk;
	}
	
	public Timestamp getDateTime(){
		return this.datatime;
	}
}
