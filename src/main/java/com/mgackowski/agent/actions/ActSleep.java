package com.mgackowski.agent.actions;

public class ActSleep extends BasicAction {
	
	public ActSleep() {
		name = "sleep";
		advertisedPromise.getChanges().put("ENERGY", 80f);
		consequences.put("ENERGY", new Consequence("ENERGY", 80f));
	}

}