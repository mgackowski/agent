package com.fdmgroup.agent.actions;

public class ActRead extends BasicAction {
	
	public ActRead() {
		name = "read";
		advertisedPromise.getChanges().put("FUN", 50f);
		consequences.put("FUN", new Consequence("FUN", 50f));
	}

}