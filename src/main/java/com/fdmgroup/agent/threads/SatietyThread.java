package com.fdmgroup.agent.threads;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fdmgroup.agent.agents.Agent;

/**
 * Satiety is a temporary state during which a need is not deteriorating.
 * It is intended to prevent Needs from dropping while they're being refilled, and can
 * simulate a period of "immunity", e.g. an Agent will not get hungry for a while after a tasty meal.
 * @author Mikolaj.Gackowski
 *
 */
public class SatietyThread extends Thread {
	
	static Logger log = LogManager.getLogger();
	Agent satedAgent;
	String needName;
	long millis;
	Thread pairedActionThread = null;
	
	/**
	 * @param satedAgent the Agent to assign a satiety state to
	 * @param needName the need which needs to stop deteriorating
	 * @param millis the extra amount of time during which satiety persists after a need is satisfied
	 */
	public SatietyThread(Agent satedAgent, String needName, long millis) {
		this.satedAgent = satedAgent;
		this.needName = needName;
		this.millis = millis;
	}
	
	/**
	 * @param satedAgent satedAgent the Agent to assign a satiety state to
	 * @param needName needName the need which needs to stop deteriorating
	 */
	public SatietyThread(Agent satedAgent, String needName) {
		this.satedAgent = satedAgent;
		this.needName = needName;
		this.millis = 0;
	}
	
	/**
	 * @param satedAgent satedAgent the Agent to assign a satiety state to
	 * @param needName needName the need which needs to stop deteriorating
	 * @param finishTogether the Thread which dictates how long satiety should last
	 */
	public SatietyThread(Agent satedAgent, String needName, Thread finishTogether) {
		this.satedAgent = satedAgent;
		this.needName = needName;
		this.millis = 0;
		this.pairedActionThread = finishTogether;
	}
	
	/**
	 * @param satedAgent satedAgent the Agent to assign a satiety state to
	 * @param needName needName the need which needs to stop deteriorating
	 * @param finishTogether the Thread which dictates how long satiety should last
	 * @param millis the extra amount of time during which satiety persists after a need is satisfied
	 */
	public SatietyThread(Agent satedAgent, String needName, Thread finishTogether, long millis) {
		this.satedAgent = satedAgent;
		this.needName = needName;
		this.millis = millis;
		this.pairedActionThread = finishTogether;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		float originalDeteriorationRate = satedAgent.getIndivValues().getDownRate(needName);
		satedAgent.getIndivValues().setDownRate(needName, 0);
		try {
			if (pairedActionThread != null) {
				pairedActionThread.join();
			}
			Thread.sleep(millis);	//TODO: speed
		}
		catch (InterruptedException e) {
			log.info(satedAgent.getName() + "'s satiety thread has been interrupted.");
		}
		finally {
			satedAgent.getIndivValues().setDownRate(needName, originalDeteriorationRate);
		}
	}
}
