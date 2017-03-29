package com.mgackowski.agent.actions;

public class ActEatMeal extends BasicAction {
	
	public ActEatMeal() {
		name = "eat meal";
		advertisedPromise.getChanges().put("FOOD", 60f);
		consequences.put("FOOD", new Consequence("FOOD", 60f));
	}

}