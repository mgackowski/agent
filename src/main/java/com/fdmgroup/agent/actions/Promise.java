package com.fdmgroup.agent.actions;

import java.util.HashMap;
import java.util.Map;

/**
 * Actions will communicate with agents, telling them ("promising") which needs they can satisfy.
 * This class provides a description of such promises for an individual action.
 * Note that the promised values can be different from the actual consequences (see Consequence).
 * The class can and should be extended for more advanced object/action implementations.
 * @author Mikolaj Gackowski
 *
 */
public class Promise {
	
	/* K - need name, e.g. "FOOD", V - promised change, e.g. 20f or -10f */
	private Map<String,Float> change = new HashMap<String,Float>();

	public float getChange(String needName){
		if (change.containsKey(needName)) {
			return change.get(needName);
		}
		else {
			return 0f;
		}
	}

	public boolean setChange(String needName, float newValue){
		if (change.containsKey(needName)) {
			change.put(needName, newValue);
			return true;
		}
		else {
			return false;
		}
	}

	public Map<String, Float> getChange() {
		return change;
	}
}
