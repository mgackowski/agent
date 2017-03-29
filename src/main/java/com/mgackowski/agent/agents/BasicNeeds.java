package com.mgackowski.agent.agents;

import java.util.HashMap;
import java.util.Map;

/**
 * An abstract class for Needs to serve as a template for basic needs.
 * Each need has a name and value, and a method for changing the need is provided.
 * @author Mikolaj.Gackowski
 *
 */
public abstract class BasicNeeds implements Needs {
	
	protected Map<String,Float> needs = new HashMap<String,Float>();
	
	/* (non-Javadoc)
	 * @see com.mgackowski.agent.agents.Needs#getNeed(java.lang.String)
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
	 * @see com.mgackowski.agent.agents.Needs#setNeed(java.lang.String, float)
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
	 * @see com.mgackowski.agent.agents.Needs#changeNeed(java.lang.String, float)
	 */
	public float changeNeed(String needName, float delta) {
		if (!needs.containsKey(needName)) {
			return -1f;
		}
		float newValue = needs.get(needName) + delta;
		if (newValue >= 0f) {
			needs.put(needName, newValue);
		}
		else {
			needs.put(needName, 0f);
		}
		return needs.get(needName);
	}
	
	/* (non-Javadoc)
	 * @see com.mgackowski.agent.agents.Needs#getSumOfAllNeeds()
	 */
	public float getSumOfAllNeeds() {
		float sum = 0;
		for (Float needValue : needs.values()) {
			sum += needValue;
		}
		return sum;
	}

	/* (non-Javadoc)
	 * @see com.mgackowski.agent.agents.Needs#getNeeds()
	 */
	public Map<String, Float> getNeeds() {
		return needs;
	}
	
}
