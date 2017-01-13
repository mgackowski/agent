package com.fdmgroup.agent.actions;

import java.util.HashMap;
import java.util.Map;

public class Promise {
	
	private Map<String,Float> change = new HashMap<String,Float>();

	public float getChange(String needName){
		if (change.containsKey(needName)) {
			//System.out.println("DEBUG: Promise change contains key " + needName);
			return change.get(needName);
		}
		else {
			return 0f;	//important to default to zero: decision thread depends on it
		}
	}

	public boolean setChange(String needName, float newValue){
		if (change.containsKey(needName)) {
			change.put(needName, newValue);
			return true;
		}
		else {
			System.out.println("DEBUG: Promise setChange No such need: " + needName);
			return false;
		}
	}

	public Map<String, Float> getChange() {
		return change;
	}
}
