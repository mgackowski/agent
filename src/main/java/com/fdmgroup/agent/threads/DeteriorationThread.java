package com.fdmgroup.agent.threads;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fdmgroup.agent.agents.Agent;

/**
 * Deteriorates the Needs of the provided Agent over time.
 * @author Mikolaj.Gackowski
 */
public class DeteriorationThread extends Thread {
	
	static Logger log = LogManager.getLogger();
	Agent thisAgent;
	long stepMillis = 100;
	
	/**
	 * @param thisAgent the Agent whose needs to deteriorate
	 */
	public DeteriorationThread(Agent thisAgent) {
		this.thisAgent = thisAgent;
		this.setName(thisAgent.getName() + "'s deterioration");
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		
		while(thisAgent.isAlive()) {
			
			for(String needName : thisAgent.getNeeds().getNeeds().keySet()) {
				if (thisAgent.getNeeds().getNeed(needName) > 0) {
					thisAgent.getNeeds().changeNeed(needName, -1 * thisAgent.getIndivValues().getDownRate(needName));
				}
				else {
					thisAgent.getNeeds().setNeed(needName, 0f);
					thisAgent.kill();
				}
			}
			
			try {
				Thread.sleep(10 * stepMillis); // TODO: Speed
			} catch (InterruptedException e) {
				log.debug("Deterioration thread for Agent " + thisAgent.getName() + " has been interrupted.");
				return;
			}
		}
	}
	
}