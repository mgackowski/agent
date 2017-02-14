package com.fdmgroup.agent.threads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.objects.ObjectAction;

public class PerformActionThread extends Thread {
	
	static Logger log = LogManager.getLogger();
	
	Agent performer;
	
	//UseableObject usedObject;
	//Action performedAction;
	
	int requiredMinLength = 0;
	
	List<Thread> threads = Collections.synchronizedList(new ArrayList<Thread>());
	
	public PerformActionThread(Agent performer) {
		super();
		this.performer = performer;
		this.setName(performer.getName() + "'s PerformActionThread");
	}
	
	public PerformActionThread(Agent performer, int minLength) {
		super();
		this.performer = performer;
		this.requiredMinLength = minLength;
		this.setName(performer.getName() + "'s PerformActionThread");
	}

	/*public PerformActionThread(Agent performer, UseableObject usedObject, Action performedAction) {
		this.performer = performer;
		this.usedObject = usedObject;
		this.performedAction = performedAction;
		this.setName(performedAction.getName() + " thread " + this.getId());
	}
	
	public PerformActionThread(Agent performer, UseableObject usedObject, Action performedAction, int requiredMinLength) {
		this.performer = performer;
		this.usedObject = usedObject;
		this.performedAction = performedAction;
		this.requiredMinLength = requiredMinLength;
		this.setName(performedAction.getName() + " thread " + this.getId());
	}*/

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
			Thread wait = new WaitThread(requiredMinLength);
			threads.add(wait);
			wait.start();
		}
		
		/* Wait for all threads to finish */
		for (Thread thisThread : threads) {
			try {
				thisThread.join();
			} catch (InterruptedException e) {
				// To interrupt an action, an interrupt will have to be invoked on every
				// single thread in the thread list - use interruptThreads() provided
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

	public List<Thread> getThreads() {
		return threads;
	}
	
	public synchronized void interruptThreads() {
		log.debug(this.getName() + "'s threads are interrupting...");
		try {
			for (Thread thisThread : threads) {
				thisThread.interrupt();
			}
		}
		catch (ConcurrentModificationException e) {
			//TODO: Graceful way of handling this.
			log.error("Concurrent modification when interrupting threads.");
		}

	}

}
