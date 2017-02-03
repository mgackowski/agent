package com.fdmgroup.agent.threads;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fdmgroup.agent.agents.Agent;

public class SatietyThread extends Thread {
	
	static Logger log = LogManager.getLogger();
	Agent satedAgent;
	String needName;
	int millis;
	Thread pairedThread = null; 
	
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
	
	public SatietyThread(Agent satedAgent, String needName, Thread finishTogether) {
		this.satedAgent = satedAgent;
		this.needName = needName;
		this.millis = 0;
		this.pairedThread = finishTogether;
	}
	
	/* Run for the duration of the pairedThread (typically a PerformActionThread), if specified.
	 * Then, wait for a number of milliseconds, if specified.
	 * This prevents the needs from dropping while they're being satisfied, and using millis
	 * can extend this "invincibility" period to simulate satiety.
	 */
	public void run() {
		float originalDeteriorationRate = satedAgent.getIndivValues().getDownRate(needName);
		satedAgent.getIndivValues().setDownRate(needName, 0);
		try {
			if (pairedThread != null) {
				pairedThread.join();
			}
			Thread.sleep(millis);
		}
		catch (InterruptedException e) {
			//TODO: Log: This agent's satiety thread has been interrupted.
		}
		finally {
			satedAgent.getIndivValues().setDownRate(needName, originalDeteriorationRate);
		}
	}
}
