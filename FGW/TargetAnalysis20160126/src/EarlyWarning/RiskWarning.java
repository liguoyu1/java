package EarlyWarning;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.mail.MessagingException;

import FileOperator.HDFSFileRead;


public class RiskWarning {
	static String Riskfilepath = new String();
	static String filepath = new String();
	static ArrayList<String> RiskPeople = new ArrayList<String>();
	//读取预警用户的名单
	public static void ReadPeopleList(String path) throws IOException{
		RiskPeople = HDFSFileRead.readFileAll(path+"/UserRiskAnalysisResault/part-r-00000");
	}
	public static void ReadPeopleList() throws IOException{
		RiskPeople = HDFSFileRead.readFileAll(Riskfilepath+"/UserRiskAnalysisResault/part-r-00000");
	}
	//读取用户的信息
	public static String getPeopleNew(String people_Id) throws IOException{
//		System.out.println(people_Id);
		ArrayList<String> peoplelist = HDFSFileRead.readFileAll(filepath+"/People/people.txt");
		for(Iterator<String> iter = peoplelist.iterator();iter.hasNext();)
		{
			String line = iter.next();
			StringTokenizer itr = new StringTokenizer(line);
			if(itr.hasMoreTokens()){
				String peopleId = itr.nextToken().toString();
//				System.out.println(peopleId);
				if(peopleId.contains(people_Id)){
					itr.nextToken();
//					System.out.println(line);
					return line;
				}
			}
		}
		return null;
	}
	//读取用户使用的设备，提取设备ID
	public static ArrayList<String> getUserDevices(String people_id) throws IOException{
		ArrayList<String> device_list = new ArrayList<String>();
		device_list = HDFSFileRead.readFileAll(filepath+"/device/device.txt");
		ArrayList<String> user_devices = new ArrayList<String>();
		for(Iterator<String> iter = device_list.iterator();iter.hasNext();){
			String device = iter.next();
			StringTokenizer itr = new StringTokenizer(device);
			if(itr.hasMoreTokens()){
				String device_Id = itr.nextToken();
				if(device.contains(people_id)){
					device_Id = device_Id.substring(1, device_Id.length()-1);
					user_devices.add(device_Id);
				}
			}
		}
		return user_devices;
	}
	
	//提取笔记本终端文件行为记录
	public static String getComputeFileOperator(String device_Id) throws IOException{
		ArrayList<String> behaviorList = HDFSFileRead.readFileAll(filepath+"/Action/computer/File/S20150609fU.txt");
		ArrayList<String> blackList = HDFSFileRead.readFileAll(filepath+"/MalevolenceBehaviorFeature/FileP/20151222f.txt");
		String behavior = new String();
		for(Iterator<String> iter = behaviorList.iterator();iter.hasNext();){
			String line = iter.next();
			if(line.contains(device_Id)){
				String[] items = line.split(", ");
				for(Iterator<String> Biter = blackList.iterator();Biter.hasNext();){
					String Bline = Biter.next();
					if(Bline.contains(items[1])&&!behavior.contains(items[1])){
						behavior += "\t\t程序/应用" + items[0] + items[4]+" file " + items[1]+"\n";
					}
				}
			}
		}
		return behavior;
	}
	
	//提取笔记本终端注册表行为记录
	public static String getComputeRegOperator(String device_Id) throws IOException{
		ArrayList<String> behaviorList = HDFSFileRead.readFileAll(filepath+"/Action/computer/Reg/S20150610rU.txt");
		ArrayList<String> blackList = HDFSFileRead.readFileAll(filepath+"/MalevolenceBehaviorFeature/RegP/20150616r.txt");
		String behavior = new String();
		for(Iterator<String> iter = behaviorList.iterator();iter.hasNext();){
			String line = iter.next();
			if(line.contains(device_Id)){
				String[] items = line.split("\t");
				for(Iterator<String> Biter = blackList.iterator();Biter.hasNext();){
					String Bline = Biter.next();
					if(Bline.contains(items[0])&&!behavior.contains(items[0])){
						behavior += "\t\t程序/应用" + items[0] + items[4]+ items[3]+"\n";
					}
				}
			}
		}
		return behavior;
	}
	//提取笔记终端风险项目
	public static String getComputeSystemLeak(String device_Id) throws IOException{
		ArrayList<String> behaviorList = HDFSFileRead.readFileAll(filepath+"/Action/computer/ScanLeak/SystemLeakU.txt");
		ArrayList<String> blackList = HDFSFileRead.readFileAll(filepath+"/SystemLeak/Compute/SystemLeakB.txt");
		String behavior = new String();
		for(Iterator<String> iter = behaviorList.iterator();iter.hasNext();){
			String line = iter.next();
//			System.out.println(line);
			if(line.contains(device_Id)){
				String[] items = line.split("\t");
//				System.out.println(items[4]);
				for(Iterator<String> Biter = blackList.iterator();Biter.hasNext();){
					String Bline = Biter.next();
					if(Bline.contains(items[0])&&!behavior.contains(items[0])){
						behavior += "\t\t系统存在" + items[1] +"漏洞\n";
					}
				}
			}
		}
		return behavior;
	}
	
	//提取黑名单IP
	public static String getBlackIP(String device_Id) throws IOException{
		return null;
	}
	//提取黑名单URL
	public static String get(String device_Id) throws IOException{
		return null;
	}
	//提取黑名单APP
	
	//提取用户设备的危险行为
	public static String getDeviceBehavior(String device_id) throws IOException{
		String str = getComputeSystemLeak(device_id);
//		System.out.println(str);
		return getComputeFileOperator(device_id)+getComputeRegOperator(device_id)+str;
	}
	//建立笔记本终端预警消息
	public static String buildComputeMessage(String people_Id,String riskLevel) throws IOException{
		String message = new String("尊敬的");
//		String people_new = getPeopleNew(people_Id);
//		StringTokenizer itr = new StringTokenizer(people_new);
		message += people_Id;
		message += "女士/先生，您好！ 您目前设备综合风险等级为：" + riskLevel +" \n";
		//获取用户关联的设备
		ArrayList<String> dev_list = getUserDevices(people_Id);
		for(Iterator<String> iter = dev_list.iterator();iter.hasNext();){
			String device_id = iter.next();
			message += "\t您的设备" + device_id+"可能存在以下风险：\n";
			//获取设备危险行为信息
			String dev_op = getDeviceBehavior(device_id);
			message += dev_op;
		}
		return message;
	}
	public static void run(String[] args) throws IOException{
		filepath = args[0];
		ReadPeopleList(args[1]);
		for(Iterator<String> iter = RiskPeople.iterator();iter.hasNext();){
			String people_new = iter.next();
			StringTokenizer itr = new StringTokenizer(people_new);
			if(itr.hasMoreTokens()){
				String record = itr.nextToken();
				String RiskLevel = itr.nextToken();
				record = record.substring(1, record.length()-1);
				String message = buildComputeMessage(record,RiskLevel);
				System.out.println(message);
			}
		}
	}
	//短信预警
	public static void NewsWarning() throws IOException, InterruptedException{
		ReadPeopleList();
		for(Iterator<String> iter = RiskPeople.iterator();iter.hasNext();){
			String people_new = iter.next();
//			System.out.println(people_new);
			StringTokenizer itr = new StringTokenizer(people_new);
			String message = new String();
			String phoneNumber = new String();
			
			if(itr.hasMoreTokens()){
				String record = itr.nextToken();
				String RiskLevel = itr.nextToken();
				record = record.substring(1, record.length()-1);
				phoneNumber = getPeopleNew(record);
				phoneNumber = phoneNumber.split("\t")[2];
				phoneNumber = phoneNumber.substring(1,phoneNumber.length()-1);
//				System.out.println(phoneNumber);
				message = buildComputeMessage(record,RiskLevel);
//				System.out.println(message);
			}
//			int num = 0;
//			if(message.length()%60 == 0)
//			{
//				num = message.length()/60;
//			}
//			else{
//				num = message.length()/60+1;
//			}
//			for(int i = 0;i < num;i++)
//			{
//				if(i+1 < num){
//					News.sendMessage(phoneNumber, message.substring(i*60, (i+1)*60));
//				}
//				else{
//					News.sendMessage(phoneNumber, message.substring(i*60, message.length()));
//				}
//			}
			News.sendMessage(phoneNumber, message.split("\n")[0]+"详情请查看邮件！");
//			break;
		}
	}
	//邮件预警
	public static void EmailWarning() throws IOException, MessagingException{
		ReadPeopleList();
		for(Iterator<String> iter = RiskPeople.iterator();iter.hasNext();){
			String people_new = iter.next();
//			System.out.println(people_new);
			StringTokenizer itr = new StringTokenizer(people_new);
			String message = new String();
			String EmailAddress = new String();
			
			if(itr.hasMoreTokens()){
				String record = itr.nextToken();
				String RiskLevel = itr.nextToken();
				record = record.substring(1, record.length()-1);
				EmailAddress = getPeopleNew(record);
				EmailAddress = EmailAddress.split("\t")[3];
				EmailAddress = EmailAddress.substring(1,EmailAddress.length()-1);
//				System.out.println(phoneNumber);
				message = buildComputeMessage(record,RiskLevel);
				System.out.println(message);
			}
			Email email = new Email();
			email.sendMessage(EmailAddress, message);
//			break;
		}
	}
	
	//短信邮件预警
	public static void NewsAndEmailWarning() throws IOException, MessagingException, InterruptedException {
		ReadPeopleList();
		for(Iterator<String> iter = RiskPeople.iterator();iter.hasNext();){
			String people_new = iter.next();
//			System.out.println(people_new);
			StringTokenizer itr = new StringTokenizer(people_new);
			String message = new String();
			String EmailAddress = new String();
			String phoneNumber = new String();
			String peopleNews = new String();
			if(itr.hasMoreTokens()){
				String record = itr.nextToken();
				String RiskLevel = itr.nextToken();
				record = record.substring(1, record.length()-1);
				peopleNews = getPeopleNew(record);
				EmailAddress = peopleNews.split("\t")[3];
				EmailAddress = EmailAddress.substring(1,EmailAddress.length()-1);
				phoneNumber = peopleNews.split("\t")[2];
				phoneNumber = phoneNumber.substring(1,phoneNumber.length()-1);
//				System.out.println(phoneNumber);
				message = buildComputeMessage(record,RiskLevel);
				System.out.println(message);
			}
			Email email = new Email();
			email.sendMessage(EmailAddress, message);
			News.sendMessage(phoneNumber, message.split("\n")[0]+"详情请查看邮件！");
//			break;
		}
	}
	//设置预警相关文件根路径
	public static void setRootPath(String path){
		filepath = path;
	}
	//设置风险用户文件路径
	public static void setRiskPath(String path){
		Riskfilepath = path;
	}
	
	
	//预警推送   （1：短信预警，2 邮件预警；3两种预警方式均采用）
	public static void SendWarnningNews(int SendNewsType) throws IOException, InterruptedException, MessagingException{
		//短信预警
		if(SendNewsType == 1){
			NewsWarning();
			return ;
		}
		//邮件预警
		if(SendNewsType == 2){
			EmailWarning();
			return ;
		}
		//短信、邮件预警
		if(SendNewsType == 3){
			NewsAndEmailWarning();
			return ;
		}
	}
	public static void main(String[] args) throws IOException, InterruptedException, MessagingException{
		setRootPath(args[0]);
		setRiskPath(args[1]);
		SendWarnningNews(3);
//		SendWarnningNews(2);
	}
}
