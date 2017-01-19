package com.fdmgroup.agent.agents;

public class FiveIndividuality extends Individuality {

	public FiveIndividuality() {
		super.getDownRate().put("FOOD", 1f);
		super.getDownRate().put("HYGIENE", 1f);
		super.getDownRate().put("BLADDER", 1f);
		super.getDownRate().put("FUN", 1f);
		super.getDownRate().put("ENERGY", 1f);
	}
	
	public FiveIndividuality(float foodDeteriorationRate, float hygieneDeteriorationRate, float bladderDeteriorationRate, float funDeteriorationRate, float energyDeteriorationRate) {
		super.getDownRate().put("FOOD", foodDeteriorationRate);
		super.getDownRate().put("HYGIENE", hygieneDeteriorationRate);
		super.getDownRate().put("BLADDER", bladderDeteriorationRate);
		super.getDownRate().put("FUN", funDeteriorationRate);
		super.getDownRate().put("ENERGY", energyDeteriorationRate);
	}
}
