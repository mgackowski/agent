package com.mgackowski.agent.actions;

import java.util.HashMap;
import java.util.Map;

/**
 * Actions contain Promises which "advertise" the needs they can satisfy.
 * This class provides a description of a set of promises for an individual action.
 * Note that the promised values can be different from the actual consequences (see Consequence).
 * The class can and should be extended for more advanced object/action implementations.
 * @author Mikolaj.Gackowski
 *
 */
public class Promise {
	
	/** K - need name, e.g. "FOOD", V - promised change, e.g. 20f or -10f */
	private Map<String,Float> change = new HashMap<String,Float>();

	/**
	 * @param The name of the need. The suggested format is ALLCAPS e.g. "HUNGER".
	 * @return The amount of points that that will be added/subtracted from the current need level. Returns 0 if the need is not described in the promise or its value is null.
	 */
	public float getChange(String needName){
		if (change.get(needName) == null) {
			return 0f;
		}
		if (change.containsKey(needName)) {
			return change.get(needName);
		}
			return 0f;
	}

	/**
	 * @param needName The name of the need. The suggested format is ALLCAPS e.g. "HUNGER".
	 * @param newValue A new value for the amount of points that will be added/subtracted from the current need level when the aciton is performed.
	 * @return true if successful; false if there is no mapping for the need provided
	 */
	public boolean setChange(String needName, float newValue){
		if (change.containsKey(needName)) {
			change.put(needName, newValue);
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * @return a Map of K - need name, e.g. "FOOD", and V - promised change, e.g. 20f or -10f
	 */
	public Map<String, Float> getChanges() {
		return change;
	}
}
