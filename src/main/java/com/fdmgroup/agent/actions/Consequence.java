package com.fdmgroup.agent.actions;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides a description of how an action will affect the needs of the agent.
 * Complex actions are broken down; getNextAction can provide the next link in the chain.
 * The class can and should be extended for more advanced object/action implementations.
 * @author Mikolaj Gackowski
 *
 */
public class Consequence {
	
	private Map<String,Float> change = new HashMap<String,Float>();
	private Action nextAction = null;
	
	public Consequence() {}
	
	public Consequence(Map<String, Float> change) {
		this.change = change;
	}
	
	public Consequence(Action nextAction) {
		this.nextAction = nextAction;
	}

	public Consequence(Map<String, Float> change, Action nextAction) {
		super();
		this.change = change;
		this.nextAction = nextAction;
	}

	public float getNeedChange(String needName){
		if (change.containsKey(needName)) {
			return change.get(needName);
		}
		else {
			return 0f;	//important to default to zero: decision thread depends on it
		}
	}

	public boolean setNeedChange(String needName, float newValue){
		if (change.containsKey(needName)) {
			change.put(needName, newValue);
			return true;
		}
		else {
			return false;
		}
	}

	public Map<String, Float> getAllChanges() {
		return change;
	}
	
	public Action getNextAction() {
		return nextAction;
	}
}
