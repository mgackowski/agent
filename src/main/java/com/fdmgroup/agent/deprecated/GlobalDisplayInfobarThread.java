package com.fdmgroup.agent.deprecated;

import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.agents.AgentPool;
import com.fdmgroup.agent.objects.ObjectPool;
import com.fdmgroup.agent.objects.UseableObject;

/**
 * Written for the purpose of providing a visual, real-time representation
 * of Agents' needs in the Oracle Eclipse console.
 * @author Mikolaj Gackowski
 *
 */
public class GlobalDisplayInfobarThread extends Thread {

	public void run() {
		
		while(true) {
			
			for (int i=0; i<25; i++) { // Eclipse clear screen hack
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
		//TODO: Nested for loops
		System.out.println("");
		System.out.print(" ☻ " + thisAgent.getName());
		for(String thisNeed : thisAgent.getNeeds().getNeeds().keySet()) {
			System.out.print("\n");
			int charLimit = 7;
			for (int i=0; i<thisNeed.toCharArray().length; i++) {
				System.out.print(thisNeed.toCharArray()[i]);
			}
			for (int i=0; i<(charLimit - thisNeed.toCharArray().length); i++) {
				System.out.print(" ");
			}
			System.out.print(": ");
			printPercentageBar(thisAgent.getNeeds().getNeed(thisNeed));
			System.out.print(" (down " + thisAgent.getIndivValues().getDownRate(thisNeed) + "/s)");
		}
		System.out.print("\nACTION : " + thisAgent.getActionStatus());
		if (thisAgent.getCurrentAction() != null) {
			System.out.print("\nTHREAD : ");
			for (Thread thisThread : thisAgent.getCurrentAction().getThreads()) {
				System.out.print("[" + thisThread.getName() + "]");
			}
		}
		
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
			if (percentage > 10) {
				System.out.print("█");
			}
			else {
				System.out.print("▓");
			}
		}
		for (int i = 0; i < (10 - blocks); i++) {
			System.out.print("░");
		}
		System.out.print(" " + String.format("%.1f", percentage) + "%");
	}
	
}