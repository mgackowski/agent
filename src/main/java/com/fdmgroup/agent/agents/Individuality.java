package com.fdmgroup.agent.agents;

import java.util.HashMap;
import java.util.Map;

/**
 * An Individuality object describes the individual modifiers which will be taken into account when
 * an Agent's needs are modified. For example, downRates (deterioration) are modifiers used to
 * calculate how quick an Agent's needs deteriorate over time.
 * @author Mikolaj.Gackowski
 *
 */
public abstract class Individuality {
	
	private Map<String,Float> downRate = new HashMap<String,Float>();

	/**
	 * @param The name of the need. The suggested format is ALLCAPS e.g. "HUNGER".
	 * @return The speed at which the given need will deteriorate (1 is default, 2 is twice the speed etc.).
	 */
	public float getDownRate(String needName){
		if (downRate.containsKey(needName)) {
			return (Float) downRate.get(needName);
		}
		else {
			return 0f;
		}
	}

	/**
	 * @param needName The name of the need. The suggested format is ALLCAPS e.g. "HUNGER".
	 * @param newValue The speed at which the given need will deteriorate (1 is default, 2 is twice the speed etc.).
	 * @return true if the change was successful, false if the need didn't exist for the Agent
	 */
	public boolean setDownRate(String needName, float newValue){
		if (downRate.containsKey(needName)) {
			downRate.put(needName, newValue);
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * @return a Map of need Names and rates of deterioration for each need e.g. "HUNGER" : 1.2f
	 */
	public Map<String, Float> getDownRate() {
		return downRate;
	}
}
