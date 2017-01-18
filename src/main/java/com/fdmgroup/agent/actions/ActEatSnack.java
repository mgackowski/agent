package com.fdmgroup.agent.actions;

import com.fdmgroup.agent.Agent;
import com.fdmgroup.agent.objects.UseableObject;
import com.fdmgroup.agent.threads.PerformActionThread;

public class ActEatSnack implements Action {
	
	private String name = "eat snack";
	private Promise advertisedPromise = new Promise();
	private Consequence consequences = new Consequence();
	private int satietyLength = 13000; // TODO: consider moving to Consequence
	private UseableObject tiedObject; // execute() could be moved to UseableObject, then this field becomes unnecessary

	public ActEatSnack(UseableObject tiedObject) {
		this.tiedObject = tiedObject;
	}

	public Promise getPromises() {
		advertisedPromise.getChange().put("FOOD", 15f);
		return advertisedPromise;
	}
	
	public Consequence getConsequences() {
		consequences.getAllChanges().put("FOOD", 15f);
		return consequences;
	}
	
	/* Refactor idea: move this to UseableObject */
	public Thread execute(Agent performer, UseableObject usedObject) {
		Thread actionExecution = new PerformActionThread(performer, usedObject, this, 13000);
		actionExecution.start();
		return actionExecution;
	}

	public String getName() {
		return this.name;
	}
	
	public UseableObject getTiedObject() {
		return this.tiedObject;
	}

	public int getSatietyLength() {
		return this.satietyLength;
	}

}