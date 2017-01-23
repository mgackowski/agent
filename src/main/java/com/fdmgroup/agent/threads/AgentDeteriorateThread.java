package com.fdmgroup.agent.threads;

import java.util.Map;
import java.util.Queue;

import com.fdmgroup.agent.actions.Action;
import com.fdmgroup.agent.agents.Agent;

public class AgentDeteriorateThread extends Thread {
	
	Agent thisAgent;
	
	public AgentDeteriorateThread(Agent thisAgent) {
		this.thisAgent = thisAgent;
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
				//TODO: Log that deterioration has been interrupted.
				return;
			}
		}
	}
	
	public void satisfyCriticalState(String needName) {
		/* The only effect of this is interrupting and starting a new action every second,
		 * a better version must be implemented. But it does help with survival slightly.
		 */
		
		if (thisAgent.getCurrentAction() != null) {
			for (Thread runningThread : thisAgent.getCurrentAction().getThreads()) {
				if (!runningThread.isInterrupted()) {
					runningThread.interrupt();
				}
			}
		}
	}
}