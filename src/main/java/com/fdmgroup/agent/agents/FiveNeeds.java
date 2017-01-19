package com.fdmgroup.agent.agents;

import java.util.HashMap;
import java.util.Map;

public class FiveNeeds implements Needs {
	
	private Map<String,Float> needs = new HashMap<String,Float>();
	
	public FiveNeeds() {
		needs.put("FOOD", 80f);
		needs.put("HYGIENE", 80f);
		needs.put("BLADDER", 80f);
		needs.put("FUN", 80f);
		needs.put("ENERGY", 80f);
	}
	
	public float getNeed(String needName){
		if (needs.containsKey(needName)) {
			return (Float) needs.get(needName);
		}
		else {
			return -1f;
		}
	}
	
	public boolean setNeed(String needName, float newValue){
		if (needs.containsKey(needName)) {
			needs.put(needName, newValue);
			return true;
		}
		else {
			return false;
		}
	}
	
	public float changeNeed(String needName, float delta) {
		if (needs.containsKey(needName)) {
			needs.put(needName, needs.get(needName) + delta);
			return needs.get(needName);
		}
		else {
			return -1f;
		}
	}
	
	public float getSumOfAllNeeds() {
		float sum = 0;
		for (Float needValue : needs.values()) {
			sum += needValue;
		}
		return sum;
	}

	public Map<String, Float> getNeeds() {
		return needs;
	}
	
}
