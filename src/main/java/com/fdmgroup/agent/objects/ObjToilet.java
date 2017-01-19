package com.fdmgroup.agent.objects;

import java.util.ArrayList;
import java.util.List;

import com.fdmgroup.agent.actions.ActNumberOne;
import com.fdmgroup.agent.actions.ActNumberTwo;
import com.fdmgroup.agent.actions.ActSleep;
import com.fdmgroup.agent.actions.ActTakeNap;
import com.fdmgroup.agent.actions.Action;

public class ObjToilet implements UseableObject {
	
	List<Action> allActions = new ArrayList<Action>();
	String name = "toilet";
	boolean beingUsed = false;
	
	public ObjToilet() {
		allActions.add(new ActNumberOne(this));
		allActions.add(new ActNumberTwo(this));
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