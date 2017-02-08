package com.fdmgroup.agent.actions;

public class ActWashHands extends BasicAction {
	
	public ActWashHands() {
		name = "wash hands";
		advertisedPromise.getChanges().put("HYGIENE", 15f);
		consequences.put("HYGIENE", new Consequence("HYGIENE", 15f));
	}

}