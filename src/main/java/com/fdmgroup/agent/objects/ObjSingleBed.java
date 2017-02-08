package com.fdmgroup.agent.objects;

import java.util.ArrayList;
import java.util.List;

import com.fdmgroup.agent.actions.Action;
import com.fdmgroup.agent.actions.ActSleep;
import com.fdmgroup.agent.actions.ActTakeNap;

public class ObjSingleBed implements UseableObject {
	
	List<Action> allActions = new ArrayList<Action>();
	String name = "single bed";
	boolean beingUsed = false;
	
	public ObjSingleBed() {
		allActions.add(new ActSleep());
		allActions.add(new ActTakeNap());
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