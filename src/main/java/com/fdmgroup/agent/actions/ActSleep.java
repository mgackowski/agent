package com.fdmgroup.agent.actions;

import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.objects.UseableObject;
import com.fdmgroup.agent.threads.PerformActionThread;

public class ActSleep implements Action {
	
	private String name = "sleep";
	private Promise advertisedPromise = new Promise();
	private Consequence consequences = new Consequence();
	private int satietyLength = 40000; // TODO: consider moving to Consequence
	private UseableObject tiedObject; // execute() could be moved to UseableObject, then this field becomes unnecessary

	public ActSleep(UseableObject tiedObject) {
		this.tiedObject = tiedObject;
	}

	public Promise getPromises() {
		advertisedPromise.getChanges().put("ENERGY", 80f);
		return advertisedPromise;
	}
	
	public Consequence getConsequences() {
		consequences.getAllChanges().put("ENERGY", 80f);
		return consequences;
	}
	
	/* Refactor idea: move this to UseableObject */
	public PerformActionThread execute(Agent performer, UseableObject usedObject) {
		PerformActionThread actionExecution = new PerformActionThread(performer, usedObject, this, 40000);
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