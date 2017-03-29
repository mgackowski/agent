package com.mgackowski.agent.actions;

public class ActTakeShower extends BasicAction {
	
	public ActTakeShower() {
		name = "take shower";
		advertisedPromise.getChanges().put("HYGIENE", 80f);
		consequences.put("HYGIENE", new Consequence("HYGIENE", 80f));
	}

}