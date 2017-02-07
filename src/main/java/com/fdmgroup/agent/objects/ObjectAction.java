package com.fdmgroup.agent.objects;

import com.fdmgroup.agent.actions.Action;

public class ObjectAction {
	
	private UseableObject object;
	private Action action;
	
	public ObjectAction(UseableObject object, Action action) {
		this.object = object;
		this.action = action;
	}

	public UseableObject getObject() {
		return object;
	}

	public Action getAction() {
		return action;
	}
	
}
