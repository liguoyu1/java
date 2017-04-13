package PaasListener;

import java.io.IOException;

import org.json.JSONException;

public class SlaveListenerThread extends Thread{
	@Override
	public void run() {
		try {
			System.out.println("listenning start ... ...");
			SlaverListener.slaveListen();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
