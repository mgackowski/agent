package com.fdmgroup.agent.threads;

import com.fdmgroup.agent.Agent;

public class AgentDeteriorateThread extends Thread {
	
	Agent thisAgent;
	
	public AgentDeteriorateThread(Agent thisAgent) {
		this.thisAgent = thisAgent;
	}

	public void run() {
		
		while(thisAgent.isAlive()) {
			
			for(String needName : thisAgent.getNeeds().getNeeds().keySet()) {
				//System.out.println("DEBUG: Deterioration loop begin");
				if (thisAgent.getNeeds().getNeed(needName) > 0) {
					//System.out.println("Deteriorating " + needName + " at " + thisAgent.getIndivValues().getDownRate(needName));
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
			
			/*if(thisAgent.getHunger() > 0) {
				thisAgent.setHunger(thisAgent.getHunger() - (1 * thisAgent.getIndivValues().getHungerDownRate()));
			}
			else {
				thisAgent.setHunger(0);
				thisAgent.kill();
			}
			if(thisAgent.getEnergy() > 0) {
				thisAgent.setEnergy(thisAgent.getEnergy() - (1 * thisAgent.getIndivValues().getEnergyDownRate()));
			}
			else{
				thisAgent.setEnergy(0);
				thisAgent.setActionStatus("Sleep deprived.");
			}*/
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("DEBUG: Deteriorate interrupted");
				e.printStackTrace();
			}
		}
	}
}