package com.fdmgroup.agent.threads;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fdmgroup.agent.actions.Action;
import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.objects.UseableObject;

public class PerformActionThread extends Thread {
	
	static Logger log = LogManager.getLogger();
	Agent performer;
	UseableObject usedObject;
	Action performedAction;
	int requiredMinLength = 0;
	
	List<Thread> threads = new ArrayList<Thread>();
	
	public PerformActionThread(Agent performer, UseableObject usedObject, Action performedAction) {
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
	}

	public void run() {
		
		usedObject.setBeingUsed(true);
		
		performer.setCurrentAction(this);
		performer.setActionStatus(performedAction.getName() + " using " + usedObject.getName() + " (id: " + this.getId() + ")");
		
		for (String needName : performedAction.getConsequences().getAllChanges().keySet()) {
			Thread changeNeed = new ChangeNeedThread(performer, needName, performedAction.getConsequences().getNeedChange(needName));
			threads.add(changeNeed);
			changeNeed.start();
			
			// TODO: single satiety value affecting all needs, possibly future change
			// TODO: this invocation will temporarily ignore millis to simplify the program
			new SatietyThread(performer, needName, this).start();
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
				//TODO: Log: one of this action's threads has been interrupted, action is cancelled 
				usedObject.setBeingUsed(false);
				performer.setCurrentAction(null);
				return;
			}
		}

		if (performedAction.getConsequences().getNextAction() != null) {
			performer.getActionQueue().add(performedAction.getConsequences().getNextAction());
		}
		
		usedObject.setBeingUsed(false);
		performer.setCurrentAction(null);
	}

	public List<Thread> getThreads() {
		return threads;
	}
	
	public void interruptThreads() {
		for (Thread thisThread : threads) {
			thisThread.interrupt();
		}
	}

}
