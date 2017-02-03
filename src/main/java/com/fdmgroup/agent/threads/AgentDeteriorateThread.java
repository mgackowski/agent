package com.fdmgroup.agent.threads;

import java.util.Map;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fdmgroup.agent.actions.Action;
import com.fdmgroup.agent.agents.Agent;

public class AgentDeteriorateThread extends Thread {
	
	static Logger log = LogManager.getLogger();
	Agent thisAgent;
	
	public AgentDeteriorateThread(Agent thisAgent) {
		this.thisAgent = thisAgent;
		this.setName(thisAgent.getName() + "'s deterioration");
	}

	public void run() {
		
		while(thisAgent.isAlive()) {
			
			for(String needName : thisAgent.getNeeds().getNeeds().keySet()) {
				if (thisAgent.getNeeds().getNeed(needName) > 0) {
					thisAgent.getNeeds().changeNeed(needName, -1 * thisAgent.getIndivValues().getDownRate(needName));
				}
				else if (needName == "FOOD") {//TODO: make more general: define special behaviour class?
					thisAgent.getNeeds().setNeed("FOOD", 0f);
					thisAgent.kill();
				}
				else { //TODO: Temporary measure for dramatic effect
					thisAgent.getNeeds().setNeed(needName, 0f);
					thisAgent.kill();
				}
				
				if (thisAgent.getNeeds().getNeed(needName) < 10) {
					satisfyCriticalState(needName);
				}
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				log.debug("Deterioration thread for Agent " + thisAgent.getName() + " has been interrupted.");
				return;
			}
		}
	}
	
	public void satisfyCriticalState(String needName) {
		/* The only effect of this is interrupting and starting a new action every second,
		 * a better version must be implemented. But it does help with survival slightly.
		 */
		log.debug("satisfyCriticalState() of need " + needName + " called for Agent " + thisAgent.getName());
		if (thisAgent.getCurrentAction() != null) {
			for (Thread runningThread : thisAgent.getCurrentAction().getThreads()) {
				if (!runningThread.isInterrupted()) {
					runningThread.interrupt();
				}
			}
		}
	}
}