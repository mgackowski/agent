package com.fdmgroup.agent.actions;

import com.fdmgroup.agent.Agent;
import com.fdmgroup.agent.objects.UseableObject;
import com.fdmgroup.agent.threads.PerformActionThread;

public class ActTakeNap implements Action {
	
	private String name = "take nap";
	private Promise advertisedPromise = new Promise();
	private Consequence consequences = new Consequence();
	private int satietyLength = 10000; // TODO: consider moving to Consequence
	private UseableObject tiedObject; // execute() could be moved to UseableObject, then this field becomes unnecessary

	public ActTakeNap(UseableObject tiedObject) {
		this.tiedObject = tiedObject;
	}

	public Promise getPromises() {
		advertisedPromise.getChange().put("ENERGY", 20f);
		return advertisedPromise;
	}
	
	public Consequence getConsequences() {
		consequences.getAllChanges().put("ENERGY", 20f);
		return consequences;
	}
	
	/* Refactor idea: move this to UseableObject */
	public Thread execute(Agent performer, UseableObject usedObject) {
		Thread actionExecution = new PerformActionThread(performer, usedObject, this);
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