package com.fdmgroup.agent.objects;

import java.util.ArrayList;
import java.util.List;

import com.fdmgroup.agent.actions.ActWait;
import com.fdmgroup.agent.actions.Action;

public class ObjIdle implements UseableObject {
	
	List<Action> allActions = new ArrayList<Action>();
	String name = "[idle object]";
	
	public ObjIdle() {
		allActions.add(new ActWait(this));
	}
	
	public List<Action> advertiseActions() {
		//TODO: Be selective about which actions to advertise;
		return allActions;
	}

	public String getName() {
		return this.name;
	}

}