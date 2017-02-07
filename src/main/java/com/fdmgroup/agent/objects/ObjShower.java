package com.fdmgroup.agent.objects;

import java.util.ArrayList;
import java.util.List;

import com.fdmgroup.agent.actions.Action;
import com.fdmgroup.agent.deprecated.ActTakeQuickShower;
import com.fdmgroup.agent.deprecated.ActTakeShower;

public class ObjShower implements UseableObject {
	
	List<Action> allActions = new ArrayList<Action>();
	String name = "shower";
	boolean beingUsed = false;
	
	public ObjShower() {
		allActions.add(new ActTakeShower(this));
		allActions.add(new ActTakeQuickShower(this));
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