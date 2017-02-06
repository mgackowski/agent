package com.fdmgroup.agent.actions;

import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.objects.UseableObject;
import com.fdmgroup.agent.threads.PerformActionThread;

public class ActRead implements Action {
	
	private String name = "read";
	private Promise advertisedPromise = new Promise();
	private Consequence consequences = new Consequence();
	private int satietyLength = 35000; // TODO: consider moving to Consequence
	private UseableObject tiedObject; // execute() could be moved to UseableObject, then this field becomes unnecessary

	public ActRead(UseableObject tiedObject) {
		this.tiedObject = tiedObject;
	}

	public Promise getPromises() {
		advertisedPromise.getChanges().put("FUN", 50f);
		return advertisedPromise;
	}
	
	public Consequence getConsequences() {
		consequences.getAllChanges().put("FUN", 50f);
		return consequences;
	}
	
	/* Refactor idea: move this to UseableObject */
	public PerformActionThread execute(Agent performer, UseableObject usedObject) {
		PerformActionThread actionExecution = new PerformActionThread(performer, usedObject, this, 5000);
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