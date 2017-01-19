package com.fdmgroup.agent.objects;

import java.util.ArrayList;
import java.util.List;

import com.fdmgroup.agent.actions.ActEatMeal;
import com.fdmgroup.agent.actions.ActEatSnack;
import com.fdmgroup.agent.actions.Action;

public class ObjFridge implements UseableObject {
	
	List<Action> allActions = new ArrayList<Action>();
	String name = "fridge";
	boolean beingUsed = false;
	
	public ObjFridge() {
		allActions.add(new ActEatSnack(this));
		allActions.add(new ActEatMeal(this));
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