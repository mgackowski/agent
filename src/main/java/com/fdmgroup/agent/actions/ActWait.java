package com.fdmgroup.agent.actions;

import com.fdmgroup.agent.Agent;
import com.fdmgroup.agent.objects.UseableObject;
import com.fdmgroup.agent.threads.PerformActionThread;

public class ActWait implements Action {
	
	private String name = "wait";
	private Promise advertisedPromise = new Promise();
	private Consequence consequences = new Consequence();
	private int satietyLength = 0; // TODO: consider moving to Consequence
	private UseableObject tiedObject; // execute() could be moved to UseableObject, then this field becomes unnecessary

	public ActWait(UseableObject tiedObject) {
		this.tiedObject = tiedObject;
	}

	public Promise getPromises() {
		return advertisedPromise;
	}
	
	public Consequence getConsequences() {
		return consequences;
	}
	
	/* Refactor idea: move this to UseableObject */
	public Thread execute(Agent performer, UseableObject usedObject) {
		Thread actionExecution = new PerformActionThread(performer, usedObject, this, 5000);
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