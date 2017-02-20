package com.fdmgroup.agent.threads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.objects.ObjectAction;

/**
 * The Thread that "performs" an Action on behalf of an Agent. It starts a number of other Threads,
 * such as ChangeNeedThread, WaitThread and SatietyThread, to simulate the satisfaction of a need over time.
 * @author Mikolaj.Gackowski
 *
 */
public class PerformActionThread extends Thread {
	
	static Logger log = LogManager.getLogger();
	
	Agent performer;
	int requiredMinLength = 0;
	
	List<Thread> threads = Collections.synchronizedList(new ArrayList<Thread>());
	
	/**
	 * @param performer the Agent performing the Action
	 */
	public PerformActionThread(Agent performer) {
		this.performer = performer;
		this.setName(performer.getName() + "'s PerformActionThread");
	}
	
	/**
	 * @param performer the Agent performing the Action
	 * @param minLength a minimum length (in milliseconds) the Action should take
	 */
	public PerformActionThread(Agent performer, int minLength) {
		this.performer = performer;
		this.requiredMinLength = minLength;
		this.setName(performer.getName() + "'s PerformActionThread");
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		
		ObjectAction performedAction = performer.getActionQueue().peek();
		performedAction.getObject().setBeingUsed(true);
		performer.setCurrentAction(performedAction);
		
		for (String needName : performedAction.getAction().getConsequences().keySet()) {
			Thread changeNeed = new ChangeNeedThread(performer, needName, performedAction.getAction().getConsequence(needName).getChange());
			threads.add(changeNeed);
			changeNeed.start();
			new SatietyThread(performer, needName, changeNeed, performedAction.getAction().getConsequence(needName).getExtraSatietyLength()).start();
		}
		
		if (requiredMinLength > 0){
			Thread wait = new WaitThread(requiredMinLength);	//TODO: Speed
			threads.add(wait);
			wait.start();
		}
		
		for (Thread thisThread : threads) {
			try {
				thisThread.join();
			} catch (InterruptedException e) {
				log.debug("Thread interrupted: " + thisThread.getName());
				performedAction.getObject().setBeingUsed(false);
				performer.setCurrentAction(null);
				return;
			}
		}

		if (performedAction.getAction().getNextAction() != null) {
			performer.getActionQueue().add(new ObjectAction(performedAction.getObject(), performedAction.getAction().getNextAction()));
		}
		
		performer.getActionQueue().poll();
		performedAction.getObject().setBeingUsed(false);
		performer.setCurrentAction(null);
	}

	/**
	 * Accessor for the List of threads associated with the currently performed Action.
	 * @return a list of threads associated with the currently performed Action
	 */
	public List<Thread> getThreads() {
		return threads;
	}
	
	/**
	 * Interrupt all threads associated with the currently performed action,
	 * then interrupt this thread. Essentially cancels current action.
	 */
	public void interruptThreads() {
		log.debug(this.getName() + "'s threads are interrupting...");
		try {
			for (Thread thisThread : threads) {
				thisThread.interrupt();
			}
		}
		catch (ConcurrentModificationException e) {
			//TODO: Find a way to handle/fix ConcurrentModificationExceptions.
			log.error("Concurrent modification when interrupting threads.");
		}
	}
}