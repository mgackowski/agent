package com.fdmgroup.agent.actions;

public class ActNumberTwo extends BasicAction {
	
	public ActNumberTwo() {
		name = "number two";
		advertisedPromise.getChanges().put("BLADDER", 70f);
		consequences.put("BLADDER", new Consequence("BLADDER", 70f));
		consequences.put("HYGIENE", new Consequence("HYGIENE", -20f));
		nextAction = new ActFlush();
	}

}