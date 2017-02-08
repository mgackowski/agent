package com.fdmgroup.agent.threads;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fdmgroup.agent.agents.Agent;

public class CriticalInterruptorThread extends Thread{
	
	static Logger log = LogManager.getLogger();
	Thread pairedPerformThread;
	Agent thisAgent;

	public CriticalInterruptorThread(Thread pairedPerformThread, Agent thisAgent) {
		this.pairedPerformThread = pairedPerformThread;
		this.thisAgent = thisAgent;
		this.setName(getName() + "'s interruptor thread");
	}
	
	public void start() {
		log.debug("Interruptor started");
		
		String criticalNeed = checkForCriticalNeeds();
		while(checkForCriticalNeeds() == null) {

			criticalNeed = checkForCriticalNeeds();
			log.debug("In the loop");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				log.error("Critical need check interrupted");
				e.printStackTrace();
			} //speed
		}
		log.debug("Out of the loop, criticalNeed == ");
	}
	
	private String checkForCriticalNeeds() {
		for (String needName : thisAgent.getNeeds().getNeeds().keySet()) {
			// If there is a need that's below critical level and not rising...
			if (thisAgent.getNeeds().getNeed(needName) <= 10 && thisAgent.getIndivValues().getDownRate(needName) < 0) {
				log.debug("Critical state detected");
				return needName;
			}
		}
		return null;
	}
	
	

}
