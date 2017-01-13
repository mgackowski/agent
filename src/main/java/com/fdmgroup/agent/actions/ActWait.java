package com.fdmgroup.agent.actions;

import java.util.HashMap;
import java.util.Map;

import com.fdmgroup.agent.Agent;
import com.fdmgroup.agent.objects.UseableObject;
import com.fdmgroup.agent.threads.PerformActionThread;

public class ActWait implements Action {
	
	private UseableObject tiedObject;
	private Promise advertisedPromise = new Promise();
	private String name = "wait";
	
	public ActWait(UseableObject tiedObject) {
		this.tiedObject = tiedObject;
	}

	public Promise getPromises() {
		return advertisedPromise;
	}

	public String getName() {
		return this.name;
	}
	
	public UseableObject getTiedObject() {
		return this.tiedObject;
	}

	public Thread execute(Agent performer, UseableObject usedObject) {
		Thread actionExecution = new PerformActionThread(performer, usedObject, this);
		actionExecution.start();
		return actionExecution;
	}

	public int getSatietyLength() {
		return 0;
	}

}