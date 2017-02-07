package com.fdmgroup.agent.deprecated;

import com.fdmgroup.agent.actions.Action;
import com.fdmgroup.agent.actions.Consequence;
import com.fdmgroup.agent.actions.Promise;
import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.objects.UseableObject;
import com.fdmgroup.agent.threads.PerformActionThread;

/**
 * Deprecated. The AgentDecisionThread will now use WaitThread as fallback when
 * no beneficial actions are available.
 * @author Mikolaj.Gackowski
 *
 */
public class ActWait implements Action {
	
	private String name = "wait";
	private Promise advertisedPromise = new Promise();
	private Consequence consequences = new Consequence();
	private int satietyLength = 0;
	private UseableObject tiedObject;

	public ActWait(UseableObject tiedObject) {
		this.tiedObject = tiedObject;
	}

	public Promise getPromises() {
		return advertisedPromise;
	}
	
	public Consequence getConsequences() {
		return consequences;
	}
	
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