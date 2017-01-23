package com.fdmgroup.agent.threads;

public class WaitThread extends Thread {
	
	long millis = 0;
	
	WaitThread(long millis) {
		this.millis = millis;
		this.setName("wait " + millis + "s thread");
	}
	
	public void run() {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			//TODO: Log: The wait thread has been interrupted.
			return;
		}
	}
}
