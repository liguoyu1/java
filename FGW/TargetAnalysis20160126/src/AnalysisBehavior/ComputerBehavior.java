package AnalysisBehavior;

import java.io.IOException;
import java.net.URISyntaxException;
import AnalysisBehavior.RegbehaviorAnalysis;

public class ComputerBehavior {
	//注册表分析
	public static void RunRegAnalysis(String[] args) throws ClassNotFoundException, IOException, InterruptedException, URISyntaxException
	{
		//注册表行为分析
		long Start = System.currentTimeMillis();
		RegbehaviorAnalysis.Run(args);
		AnalysisRegAction.Run(args);
		long End = System.currentTimeMillis();
		System.out.println("注册表分析用时:"+(End-Start)/1000+"s");
	}
	
	//文件行为分析
	public static void RunFileAnalysis(String[] args) throws ClassNotFoundException, IOException, InterruptedException, URISyntaxException
	{
		long Start = System.currentTimeMillis();
		Fileanalysis.RunServer(args);
		AnalysisFileAction.Run(args);
		long End = System.currentTimeMillis();
		System.out.println("文件分析用时:"+(End-Start)/1000+"s");
	}
	
	public static class AnalysisThreaed implements Runnable {
		@SuppressWarnings("unused")
		private String name;
		String[] args;
		public  AnalysisThreaed(String[] args) {
			this.args = args;
		}
		
	    public void run() {
	    	long Start = System.currentTimeMillis();
			try {
				Fileanalysis.RunServer(args);
			} catch (ClassNotFoundException | IOException
					| InterruptedException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				AnalysisFileAction.Run(args);
			} catch (ClassNotFoundException | IOException
					| InterruptedException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long End = System.currentTimeMillis();
			System.out.println("文件分析用时:"+(End-Start)/1000+"s");
	    }
	}
	//执行函数run
	public static void run(String[] args) throws ClassNotFoundException, IOException, InterruptedException, URISyntaxException{
		RunFileAnalysis(args);
		RunRegAnalysis(args);
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException, URISyntaxException{
		run(args);
	}
	
	

}
