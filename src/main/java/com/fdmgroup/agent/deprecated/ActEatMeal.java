package com.fdmgroup.agent.deprecated;

import java.util.Map;

import com.fdmgroup.agent.actions.Action;
import com.fdmgroup.agent.actions.Consequence;
import com.fdmgroup.agent.actions.Promise;
import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.objects.UseableObject;
import com.fdmgroup.agent.threads.PerformActionThread;

public class ActEatMeal implements Action {
	
	private String name = "eat meal";
	private Promise advertisedPromise = new Promise();
	private Consequence consequences = new Consequence();
	private int satietyLength = 45000; // TODO: consider moving to Consequence
	private UseableObject tiedObject; // execute() could be moved to UseableObject, then this field becomes unnecessary

	public ActEatMeal(UseableObject tiedObject) {
		this.tiedObject = tiedObject;
	}

	public Promise getPromises() {
		advertisedPromise.getChanges().put("FOOD", 60f);
		return advertisedPromise;
	}
	
	public Consequence getConsequences() {
		consequences.getAllChanges().put("FOOD", 60f);
		return consequences;
	}
	
	/* Refactor idea: move this to UseableObject */
	public PerformActionThread execute(Agent performer, UseableObject usedObject) {
		PerformActionThread actionExecution = new PerformActionThread(performer, usedObject, this, 45000);
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

	public Map<String, Consequence> getConsequences() {
		// TODO Auto-generated method stub
		return null;
	}

	public Consequence getConsequence(String needName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Action getNextAction() {
		// TODO Auto-generated method stub
		return null;
	}

}