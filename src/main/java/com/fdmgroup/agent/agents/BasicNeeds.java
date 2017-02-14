package com.fdmgroup.agent.agents;

import java.util.HashMap;
import java.util.Map;

/**
 * A basic implementation of Needs, representing two needs - "FOOD" and "ENERGY".
 * @author Mikolaj.Gackowski
 *
 */
public class BasicNeeds implements Needs {
	
	private Map<String,Float> needs = new HashMap<String,Float>();
	
	public BasicNeeds() {
		needs.put("FOOD", 80f);
		needs.put("ENERGY", 80f);
	}
	
	/* (non-Javadoc)
	 * @see com.fdmgroup.agent.agents.Needs#getNeed(java.lang.String)
	 */
	public float getNeed(String needName){
		if (needs.containsKey(needName)) {
			return (Float) needs.get(needName);
		}
		else {
			return -1f;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.fdmgroup.agent.agents.Needs#setNeed(java.lang.String, float)
	 */
	public boolean setNeed(String needName, float newValue){
		if (needs.containsKey(needName)) {
			needs.put(needName, newValue);
			return true;
		}
		else {
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.fdmgroup.agent.agents.Needs#changeNeed(java.lang.String, float)
	 */
	public float changeNeed(String needName, float delta) {
		if (needs.containsKey(needName)) {
			needs.put(needName, needs.get(needName) + delta);
			return needs.get(needName);
		}
		else {
			return -1f;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.fdmgroup.agent.agents.Needs#getSumOfAllNeeds()
	 */
	public float getSumOfAllNeeds() {
		float sum = 0;
		for (Float needValue : needs.values()) {
			sum += needValue;
		}
		return sum;
	}

	/* (non-Javadoc)
	 * @see com.fdmgroup.agent.agents.Needs#getNeeds()
	 */
	public Map<String, Float> getNeeds() {
		return needs;
	}
	
}
