package com.fdmgroup.agent.objects;

import com.fdmgroup.agent.actions.ActEatMeal;
import com.fdmgroup.agent.actions.ActEatSnack;

public class ObjFridge extends BasicObject {
	
	public ObjFridge() {
		name = "fridge";
		allActions.add(new ActEatSnack());
		allActions.add(new ActEatMeal());
	}

}