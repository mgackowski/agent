package com.fdmgroup.agent.objects;

import java.util.ArrayList;
import java.util.List;

import com.fdmgroup.agent.actions.ActEatSnack;
import com.fdmgroup.agent.actions.ActSleep;
import com.fdmgroup.agent.actions.ActTakeNap;
import com.fdmgroup.agent.actions.Action;

public class ObjSingleBed implements UseableObject {
	
	List<Action> allActions = new ArrayList<Action>();
	String name = "single bed";
	boolean beingUsed = false;
	
	public ObjSingleBed() {
		allActions.add(new ActSleep(this));
		allActions.add(new ActTakeNap(this));
	}
	
	public List<Action> advertiseActions() {
		if(beingUsed) {
			return new ArrayList<Action>();
		}
		else {
			return allActions;
		}
	}
	
	private void updateAvailableActions() {
		//currently unused - modifying arraylist causes concurrency issues with mulyiple agents
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