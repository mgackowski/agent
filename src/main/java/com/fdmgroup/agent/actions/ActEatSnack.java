package com.fdmgroup.agent.actions;

public class ActEatSnack extends BasicAction {
	
	public ActEatSnack() {
		name = "eat snack";
		advertisedPromise.getChanges().put("FOOD", 15f);
		consequences.put("FOOD", new Consequence("FOOD", 15f));
	}

}