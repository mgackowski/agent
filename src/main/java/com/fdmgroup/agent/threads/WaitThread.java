package com.fdmgroup.agent.threads;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The wait thread, typically used to impose a minimum length of time for an action to take.
 * Other threads can use Thread.join() to wait for this thread.
 * @author Mikolaj.Gackowski
 *
 */
public class WaitThread extends Thread {
	
	static Logger log = LogManager.getLogger();
	long millis = 0;
	
	/**
	 * Create a new Wait Thread which lasts the specified number of milliseconds.
	 * @param millis lifetime of this thread in milliseconds
	 */
	WaitThread(long millis) {
		this.millis = millis;
		this.setName("wait " + millis + "s thread");
		this.setName("Wait thread");
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			log.debug("Wait thread has been interrupted.");
			return;
		}
	}
}
