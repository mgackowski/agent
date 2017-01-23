package com.fdmgroup.agent.threads;

import com.fdmgroup.agent.agents.Agent;

public class ChangeNeedThread extends Thread {
	
	Agent targetAgent = null;
	float needChangeValue = 0f;
	String needName = "";
	
	public ChangeNeedThread(Agent targetAgent, String needName, float needChangeValue) {
		this.needChangeValue = needChangeValue;
		this.targetAgent = targetAgent;
		this.needName = needName;
	}

	public void run() {
		
		int numSteps = Math.round(Math.abs(needChangeValue));
		for (int step = 0; step < numSteps; step++) {
			if(targetAgent.getNeeds().getNeed(needName) >= 100) {
				targetAgent.getNeeds().setNeed(needName, 100f);
				return;
			}
			if(!targetAgent.isAlive()) {
				return;
			}
			targetAgent.getNeeds().changeNeed(needName, (needChangeValue / numSteps));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				//TODO: Log that changing needs has been interrupted;
				return;
			}
		}
	}

}
