package com.fdmgroup.agent.threads;

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
				else {
					thisAgent.getNeeds().setNeed(needName, 0f);
				}
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("DEBUG: Deteriorate interrupted");
				e.printStackTrace();
			}
		}
	}
}