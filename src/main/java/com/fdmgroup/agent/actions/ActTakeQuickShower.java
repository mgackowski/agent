package com.fdmgroup.agent.actions;

public class ActTakeQuickShower extends BasicAction {
	
	public ActTakeQuickShower() {
		name = "take quick shower";
		advertisedPromise.getChanges().put("HYGIENE", 40f);
		consequences.put("HYGIENE", new Consequence("HYGIENE", 40f));
	}

}