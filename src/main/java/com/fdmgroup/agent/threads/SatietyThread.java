package com.fdmgroup.agent.threads;

import com.fdmgroup.agent.agents.Agent;

public class SatietyThread extends Thread {
	
	Agent satedAgent;
	String needName;
	int millis;
	
	public SatietyThread(Agent satedAgent, String needName, int millis) {
		this.satedAgent = satedAgent;
		this.needName = needName;
		this.millis = millis;
	}
	
	public SatietyThread(Agent satedAgent, String needName) {
		this.satedAgent = satedAgent;
		this.needName = needName;
		this.millis = 5000;
	}
	
	public void run() {
		float originalDeteriorationRate = satedAgent.getIndivValues().getDownRate(needName);
		satedAgent.getIndivValues().setDownRate(needName, 0);
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			System.out.println("DEBUG: SatietyThread interrupted for agent " + satedAgent.getName());
			e.printStackTrace();
		}
		satedAgent.getIndivValues().setDownRate(needName, originalDeteriorationRate);
	}

}
