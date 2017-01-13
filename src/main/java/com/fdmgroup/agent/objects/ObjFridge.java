package com.fdmgroup.agent.objects;

import java.util.ArrayList;
import java.util.List;

import com.fdmgroup.agent.actions.ActEatSnack;
import com.fdmgroup.agent.actions.Action;

public class ObjFridge implements UseableObject {
	
	List<Action> allActions = new ArrayList<Action>();
	String name = "fridge";
	
	public ObjFridge() {
		allActions.add(new ActEatSnack(this));
	}
	
	public List<Action> advertiseActions() {
		//TODO: Be selective about which actions to advertise;
		return allActions;
	}

	public String getName() {
		return this.name;
	}

}