package com.fdmgroup.agent.threads;

import java.util.ArrayList;
import java.util.List;

import com.fdmgroup.agent.Agent;
import com.fdmgroup.agent.actions.Action;
import com.fdmgroup.agent.objects.UseableObject;

public class PerformActionThread extends Thread {
	
	Agent performer;
	UseableObject usedObject;
	Action performedAction;
	int requiredMinLength = 0;
	
	public PerformActionThread(Agent performer, UseableObject usedObject, Action performedAction) {
		this.performer = performer;
		this.usedObject = usedObject;
		this.performedAction = performedAction;
	}
	
	public PerformActionThread(Agent performer, UseableObject usedObject, Action performedAction, int requiredMinLength) {
		this.performer = performer;
		this.usedObject = usedObject;
		this.performedAction = performedAction;
		this.requiredMinLength = requiredMinLength;
	}

	public void run() {
		
		usedObject.setBeingUsed(true);

		performer.setActionStatus(performedAction.getName() + " using " + usedObject.getName());
		
		List<Thread> threads = new ArrayList<Thread>();
		
		for (String needName : performedAction.getConsequences().getAllChanges().keySet()) {
			Thread changeNeed = new ChangeNeedThread(performer, needName, performedAction.getConsequences().getNeedChange(needName));
			threads.add(changeNeed);
			changeNeed.start();
			// TODO: single satiety value affecting all needs, possibly future change
			new SatietyThread(performer, needName, performedAction.getSatietyLength()).start();
		}
		
		if (requiredMinLength > 0){
			Thread wait = new WaitThread(requiredMinLength);
			threads.add(wait);
			wait.start();
		}
		
		//System.out.println("DEBUG:  threads " + threads.toString());
		
		/* Wait for all threads to finish */
		for (Thread thisThread : threads) {
			try {
				thisThread.join();
			} catch (InterruptedException e) {
				//TODO: free up object
				e.printStackTrace();
			}
		}

		if (performedAction.getConsequences().getNextAction() != null) {
			performer.getActionQueue().add(performedAction.getConsequences().getNextAction());
		}
		
		usedObject.setBeingUsed(false);

		performer.setActionStatus("Finished " + performedAction.getName() + " using " + usedObject.getName());
	}

}
