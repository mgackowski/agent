package com.fdmgroup.agent.threads;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fdmgroup.agent.agents.Agent;

public class CriticalInterruptorThread extends Thread{
	
	static Logger log = LogManager.getLogger();
	PerformActionThread pairedPerformThread;
	Agent thisAgent;

	public CriticalInterruptorThread(PerformActionThread pairedPerformThread, Agent thisAgent) {
		this.pairedPerformThread = pairedPerformThread;
		this.thisAgent = thisAgent;
		this.setName(thisAgent.getName() + "'s interruptor thread");
	}
	
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
		log.debug("Interruptor finished - No. " + this.getId());
	}
	
	private String checkForCriticalNeeds() {
		for (String needName : thisAgent.getNeeds().getNeeds().keySet()) {
			// If there is a need that's below critical level and not rising...
			if (thisAgent.getNeeds().getNeed(needName) <= 10 && thisAgent.getIndivValues().getDownRate(needName) > 0) {
				log.debug("Critical state detected");
				return needName;
			}
		}
		return null;
	}
	
	

}
