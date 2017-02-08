package com.fdmgroup.agent.threads;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fdmgroup.agent.agents.Agent;

public class SatietyThread extends Thread {
	
	static Logger log = LogManager.getLogger();
	Agent satedAgent;
	String needName;
	long millis;
	Thread pairedActionThread = null;	// This can be a PerformAction or ChangeNeed thread, different behaviours
	
	public SatietyThread(Agent satedAgent, String needName, long millis) {
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
		this.pairedActionThread = finishTogether; //pair it with the perform action thread
	}
	
	public SatietyThread(Agent satedAgent, String needName, Thread finishTogether, long millis) {
		this.satedAgent = satedAgent;
		this.needName = needName;
		this.millis = millis;
		this.pairedActionThread = finishTogether;
	}
	
	/* Run for the duration of the pairedActionThread (typically a PerformActionThread), if specified.
	 * Then, wait for a number of milliseconds, if specified.
	 * This prevents the needs from dropping while they're being satisfied, and using millis
	 * can extend this "invincibility" period to simulate satiety.
	 */
	public void run() {
		float originalDeteriorationRate = satedAgent.getIndivValues().getDownRate(needName);
		satedAgent.getIndivValues().setDownRate(needName, 0);
		try {
			if (pairedActionThread != null) {
				pairedActionThread.join();
			}
			Thread.sleep(millis);
		}
		catch (InterruptedException e) {
			log.info(satedAgent.getName() + "'s satiety thread has been interrupted.");
		}
		finally {
			satedAgent.getIndivValues().setDownRate(needName, originalDeteriorationRate);
		}
	}
}
