package com.fdmgroup.agent.actions;

import java.util.HashMap;
import java.util.Map;

import com.fdmgroup.agent.objects.ObjectAction;

public class BasicAction implements Action{

	protected String name = "";
	protected Promise advertisedPromise = new Promise();
	protected Map<String, Consequence> consequences = new HashMap<String, Consequence>();
	protected Action nextAction = null;

	public String getName() {
		return name;
	}

	public Promise getPromises() {
		return advertisedPromise;
	}

	public Map<String, Consequence> getConsequences() {
		return consequences;
	}

	public Consequence getConsequence(String needName) {
		return consequences.get(needName);
	}

	public Action getNextAction() {
		return nextAction;
	}

}