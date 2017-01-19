package com.fdmgroup.agent.agents;

import java.util.HashMap;
import java.util.Map;

public abstract class Individuality {
	
	private Map<String,Float> downRate = new HashMap<String,Float>();

	public float getDownRate(String needName){
		if (downRate.containsKey(needName)) {
			return (Float) downRate.get(needName);
		}
		else {
			return 0f;
		}
	}

	public boolean setDownRate(String needName, float newValue){
		if (downRate.containsKey(needName)) {
			downRate.put(needName, newValue);
			return true;
		}
		else {
			return false;
		}
	}

	public Map<String, Float> getDownRate() {
		return downRate;
	}
}
