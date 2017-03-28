package com.fdmgroup.agent.threads;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A Thread which is typically run alongside a PerformActionThread. It checks if an associated Agent's
 * needs fall below a critical level, and interrupts the currently performed action if they do.
 * @author Mikolaj.Gackowski
 *
 */
public class CriticalInterruptorThread extends Thread{
	
	static Logger log = LogManager.getLogger();
	PerformActionThread pairedPerformThread;

	/**
	 * @param pairedPerformThread The PerformActionThread for the action to interrupt
	 */
	public CriticalInterruptorThread(PerformActionThread pairedPerformThread) {
		this.pairedPerformThread = pairedPerformThread;
		this.setName(pairedPerformThread.performer.getName() + "'s interruptor thread");
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		log.debug("Interruptor started - No. " + this.getId());
		
		while (pairedPerformThread.isAlive()) {
			if (checkForCriticalNeeds() != null) {
				log.debug("Critical state detected, interrupting threads...");
				pairedPerformThread.interruptThreads();
				log.debug("Interruptor finished - No. " + this.getId());
				return;
			}
		}
		log.debug("Interruptor finished (no interruptions were necessary) - No. " + this.getId());
	}
	
	/**
	 * @return the name of a need below critical level, or null if all are above the critical level
	 */
	private String checkForCriticalNeeds() {
		for (String needName : pairedPerformThread.performer.getNeeds().getNeeds().keySet()) {
			if (isNeedCritical(needName)) {
				log.debug("Critical state detected");
				return needName;
			}
		}
		return null;
	}
	
	/**
	 * @param needName The name of the need. The suggested format is ALLCAPS e.g. "HUNGER".
	 * @return true if need level is 10 points or lower, and decreasing
	 */
	private boolean isNeedCritical(String needName) {
		return pairedPerformThread.performer.getNeeds().getNeed(needName) <= 10
				&& pairedPerformThread.performer.getIndivValues().getDownRate(needName) > 0;
	}

}
