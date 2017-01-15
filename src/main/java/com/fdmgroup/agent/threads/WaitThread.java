package com.fdmgroup.agent.threads;

public class WaitThread extends Thread {
	
	long millis = 0;
	
	WaitThread(long millis) {
		this.millis = millis;
	}
	
	public void run() {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
