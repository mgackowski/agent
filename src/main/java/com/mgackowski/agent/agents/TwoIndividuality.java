package com.mgackowski.agent.agents;

/**
 * An implementation of Individuality containing deterioration rates for two basic needs, "FOOD" and "ENERGY".
 * @author Mikolaj.Gackowski
 *
 */
public class TwoIndividuality extends BasicIndividuality {

	public TwoIndividuality() {
		super.getDownRate().put("FOOD", 1f);
		super.getDownRate().put("ENERGY", 1f);
	}
	
	public TwoIndividuality(float foodDeteriorationRate, float energyDeteriorationRate) {
		super.getDownRate().put("FOOD", foodDeteriorationRate);
		super.getDownRate().put("ENERGY", energyDeteriorationRate);
	}
}
