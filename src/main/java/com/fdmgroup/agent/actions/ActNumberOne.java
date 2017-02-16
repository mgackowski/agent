package com.fdmgroup.agent.actions;

public class ActNumberOne extends BasicAction {
	
	public ActNumberOne() {
		name = "number one";
		advertisedPromise.getChanges().put("BLADDER", 30f);
		consequences.put("BLADDER", new Consequence("BLADDER", 30f));
		consequences.put("HYGIENE", new Consequence("HYGIENE", -10f));
		setNextAction(new ActFlush());
	}

}