package com.fdmgroup.agent.threads;

import com.fdmgroup.agent.Agent;
import com.fdmgroup.agent.actions.Action;
import com.fdmgroup.agent.objects.UseableObject;

public class PerformActionThread extends Thread {
	
	Agent performer;
	UseableObject usedObject;
	Action performedAction;
	
	public PerformActionThread(Agent performer, UseableObject usedObject, Action performedAction) {
		this.performer = performer;
		this.usedObject = usedObject;
		this.performedAction = performedAction;
	}

	public void run() {
		//hunger only now
		//System.out.println("Debug: decision making running");
		float change = performedAction.getPromises().getChange("FOOD");
		int numSteps = Math.round(Math.abs(change));
		//float hungerDownIV = performer.getIndivValues().getDownRate("FOOD");
		
		//performer.getIndivValues().setDownRate("FOOD", 0f); //temporarily stop being hungry
		performer.setActionStatus(performedAction.getName() + " using " + usedObject.getName());
		
		for(String needName : performedAction.getPromises().getChange().keySet()) {
			new SatietyThread(performer, needName, performedAction.getSatietyLength()).start();
		}
		
		for (int step = 0; step < numSteps; step++) {
			performer.getNeeds().changeNeed("FOOD", (change / numSteps));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//Thread satiety = new SatietyThread(performer, "FOOD", performedAction.getSatietyLength());
		//satiety.start();

		performer.setActionStatus("Finished");
	}

}
