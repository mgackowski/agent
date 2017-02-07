package com.fdmgroup.agent.objects;

import java.util.ArrayList;
import java.util.List;

import com.fdmgroup.agent.actions.Action;
import com.fdmgroup.agent.deprecated.ActWashHands;

public class ObjSink implements UseableObject {
	
	List<Action> allActions = new ArrayList<Action>();
	String name = "sink";
	boolean beingUsed = false;
	
	public ObjSink() {
		allActions.add(new ActWashHands(this));
	}
	
	public List<Action> advertiseActions() {
		if(beingUsed) {
			return new ArrayList<Action>();
		}
		else {
			return allActions;
		}
	}

	public String getName() {
		return this.name;
	}

	public boolean isBeingUsed() {
		return beingUsed;
	}

	public void setBeingUsed(boolean beingUsed) {
		this.beingUsed = beingUsed;
	}

}