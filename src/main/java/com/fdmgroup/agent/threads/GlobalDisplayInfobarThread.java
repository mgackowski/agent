package com.fdmgroup.agent.threads;

import com.fdmgroup.agent.Agent;
import com.fdmgroup.agent.AgentPool;

public class GlobalDisplayInfobarThread extends Thread {

	public void run() {
		
		while(true) {
			
			for (int i=0; i<100; i++) {
				System.out.println("");
			}
			//System.out.println("DEBUG: Starting DisplayInfobar loop.");
			for (Agent thisAgent : AgentPool.getInstance().getAgents()) {
				displayAgentStatus(thisAgent);
			}
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				System.out.println("DEBUG: DisplayInfobar interrupted.");
				e.printStackTrace();
			}
			
		}
	
	}
	
	public void displayAgentStatus(Agent thisAgent) {
		System.out.println("");
		System.out.print(" ☻ " + thisAgent.getName());
		System.out.print("\nFOOD:  ");
		printPercentageBar(thisAgent.getNeeds().getNeed("FOOD"));
		System.out.print("\nSLEEP: ");
		printPercentageBar(thisAgent.getNeeds().getNeed("ENERGY"));
		System.out.print("\nACTION: " + thisAgent.getActionStatus());
		System.out.println("");
	}
	
	public void printPercentageBar(float percentage) {
		int blocks = Math.round(percentage/10);
		if (percentage < 0) {
			blocks = 0;
		}
		if (percentage > 100) {
			blocks = 10;
		}
		for (int i = 0; i < blocks; i++) {
			System.out.print("█");
		}
		for (int i = 0; i < (10 - blocks); i++) {
			System.out.print("░");
		}
		System.out.print(" " + Math.floor(percentage) + "%");
	}
	
}