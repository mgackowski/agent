package com.fdmgroup.agent.threads;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fdmgroup.agent.agents.Agent;

public class ChangeNeedThread extends Thread {
	
	static Logger log = LogManager.getLogger();
	
	Agent targetAgent;
	float needChangeValue;
	String needName;
	
	public ChangeNeedThread(Agent targetAgent, String needName, float needChangeValue) {
		this.needChangeValue = needChangeValue;
		this.targetAgent = targetAgent;
		this.needName = needName;
		this.setName(needName + " +" + needChangeValue);
	}

	public void run() {
		log.debug("Started changing " + needName + " of " + targetAgent.getName() + " by " + needChangeValue);
		
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
				Thread.sleep(200);	//TODO: Speed!
			} catch (InterruptedException e) {
				log.debug("Need change thread for Agent " + targetAgent.getName() + " has been interrupted.");
				return;
			}
		}
		log.debug("Finished changing " + needName + " of " + targetAgent.getName() + " by " + needChangeValue);
	}

}
