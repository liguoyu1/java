package Run;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.mail.MessagingException;

import AnalysisBehavior.ComputerBehavior;
import EarlyWarning.RiskWarning;
import RiskAnalysis.UserRiskAnalysis;
import UnCertainTarget.Analysis;
//import UnCertainTarget.Analysis.WarnThreaed;

public class RunAnalysis {
	
	public static void main(String[] args)throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException, MessagingException{
//		WarnThreaed task = new WarnThreaed("");
//		Thread thread = new Thread(task);
//		thread.start();
		while(true)
		{
			long Start = System.currentTimeMillis();
			//可疑行为分析(笔记本端)
			ComputerBehavior.run(args);
			//可疑目标分析
			Analysis.run(args);
			//风险评估
			UserRiskAnalysis.run(args);
			//风险预警(两种预警方式默认)
			RiskWarning.setRootPath(args[0]);
			RiskWarning.setRiskPath(args[1]);
			RiskWarning.SendWarnningNews(3);
			long End = System.currentTimeMillis();
			System.out.println("run time : "+(End - Start)/1000+"s");
			Thread.sleep(600000);
		}
	}

}
