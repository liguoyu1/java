package Run;

import java.io.IOException;
import java.net.URISyntaxException;

import UnCertainTarget.Analysis;
import UnCertainTarget.Analysis.WarnThreaed;

public class RunAnalysis {
	
	public static void main(String[] args)throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException{
		WarnThreaed task = new WarnThreaed("");
		Thread thread = new Thread(task);
		thread.start();
		while(true)
		{
			long Start = System.currentTimeMillis();
			//可疑行为分析
			
			//可疑目标分析
			Analysis.main(args);
			long End = System.currentTimeMillis();
			System.out.println("run time : "+(End - Start)/1000+"s");
			Thread.sleep(60000);
		}
	}

}
