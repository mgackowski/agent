package com.fdmgroup.agent.threads;

import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.agents.AgentPool;
import com.fdmgroup.agent.objects.ObjectPool;
import com.fdmgroup.agent.objects.UseableObject;

/**
 * Written for the purpose of providing a visual, real-time representation
 * of Agents' needs in the Oracle Eclipse console.
 * This is written to implementation and will have to be updated as needs are extended.
 * @author Mikolaj Gackowski
 *
 */
public class GlobalDisplayInfobarThread extends Thread {

	public void run() {
		
		while(true) {
			
			for (int i=0; i<20; i++) { // Hacks.
				System.out.println("");
			}
			for (Agent thisAgent : AgentPool.getInstance().getAgents()) {
				displayAgentStatus(thisAgent);
			}
			System.out.println("\n◘ OBJECTS:");
			for (UseableObject thisObject : ObjectPool.getInstance().getObjects()) {
				displayObjectStatus(thisObject);
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void displayAgentStatus(Agent thisAgent) {
		System.out.println("");
		System.out.print(" ☻ " + thisAgent.getName());
		System.out.print("\nFOOD:  ");
		printPercentageBar(thisAgent.getNeeds().getNeed("FOOD"));
		System.out.print(" (down " + thisAgent.getIndivValues().getDownRate("FOOD") + "/s)");
		System.out.print("\nSLEEP: ");
		printPercentageBar(thisAgent.getNeeds().getNeed("ENERGY"));
		System.out.print(" (down " + thisAgent.getIndivValues().getDownRate("ENERGY") + "/s)");
		System.out.print("\nACTION: " + thisAgent.getActionStatus());
		System.out.println("");
	}
	
	public void displayObjectStatus(UseableObject thisObject) {
		System.out.print(thisObject.getName() + ": ");
		if (thisObject.isBeingUsed()) {
			System.out.println("USED");
		}
		else {
			System.out.println("FREE");
		}
		System.out.print("");
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