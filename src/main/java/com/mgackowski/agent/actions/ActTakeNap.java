package com.mgackowski.agent.actions;

public class ActTakeNap extends BasicAction {
	
	public ActTakeNap() {
		name = "take nap";
		advertisedPromise.getChanges().put("ENERGY", 20f);
		consequences.put("ENERGY", new Consequence("ENERGY", 20f));
	}

}