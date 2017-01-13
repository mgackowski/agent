package com.fdmgroup.agent.actions;

import java.util.HashMap;
import java.util.Map;

import com.fdmgroup.agent.Agent;
import com.fdmgroup.agent.objects.UseableObject;
import com.fdmgroup.agent.threads.PerformActionThread;

public class ActEatSnack implements Action {
	
	private UseableObject tiedObject;
	private Promise advertisedPromise = new Promise();
	private String name = "eat snack";
	
	public ActEatSnack(UseableObject tiedObject) {
		this.tiedObject = tiedObject;
	}

	public Promise getPromises() {
		//Map<String, Float> promiseList = new HashMap<String,Float>();
		advertisedPromise.getChange().put("FOOD", 15f);
		advertisedPromise.getChange().put("ENERGY", -2f);
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
		return 10000;
	}

}