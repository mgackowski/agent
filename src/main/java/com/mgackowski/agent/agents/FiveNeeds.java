package com.mgackowski.agent.agents;

/**
 * A basic implementation of BasicNeeds, representing five needs -
 * "FOOD", "HYGIENE", "BLADDER", "FUN" and "ENERGY".
 * @author Mikolaj.Gackowski
 *
 */
public class FiveNeeds extends BasicNeeds {
	
	/**
	 * Instantiate a FiveNeeds object.
	 */
	public FiveNeeds() {
		needs.put("FOOD", 30f);
		needs.put("HYGIENE", 30f);
		needs.put("BLADDER", 30f);
		needs.put("FUN", 30f);
		needs.put("ENERGY", 30f);
	}
	
	public FiveNeeds(float food, float hygiene, float bladder, float fun, float energy) {
		needs.put("FOOD", food);
		needs.put("HYGIENE", hygiene);
		needs.put("BLADDER", bladder);
		needs.put("FUN", fun);
		needs.put("ENERGY", energy);
	}
	
}
