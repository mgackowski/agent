package com.fdmgroup.agent.threads;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fdmgroup.agent.agents.Agent;

public class DeteriorationThread extends Thread {
	
	static Logger log = LogManager.getLogger();
	Agent thisAgent;
	
	public DeteriorationThread(Agent thisAgent) {
		this.thisAgent = thisAgent;
		this.setName(thisAgent.getName() + "'s deterioration");
	}

	public void run() {
		
		while(thisAgent.isAlive()) {
			
			for(String needName : thisAgent.getNeeds().getNeeds().keySet()) {
				if (thisAgent.getNeeds().getNeed(needName) > 0) {
					thisAgent.getNeeds().changeNeed(needName, -1 * thisAgent.getIndivValues().getDownRate(needName));
				}
				else { //TODO: Temporary measure for dramatic effect
					thisAgent.getNeeds().setNeed(needName, 0f);
					//thisAgent.kill(); TODO
				}
				
				if (thisAgent.getNeeds().getNeed(needName) < 10) {
					//satisfyCriticalState(needName);
				}
			}
			
			try {
				Thread.sleep(1000); // Tick should be speed-dependent
			} catch (InterruptedException e) {
				log.debug("Deterioration thread for Agent " + thisAgent.getName() + " has been interrupted.");
				return;
			}
		}
	}
	
}