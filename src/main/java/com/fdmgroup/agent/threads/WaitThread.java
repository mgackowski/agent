package com.fdmgroup.agent.threads;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WaitThread extends Thread {
	
	static Logger log = LogManager.getLogger();
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
