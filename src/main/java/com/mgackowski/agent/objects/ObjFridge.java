package com.mgackowski.agent.objects;

import com.mgackowski.agent.actions.ActEatMeal;
import com.mgackowski.agent.actions.ActEatSnack;

public class ObjFridge extends BasicObject {
	
	public ObjFridge() {
		name = "fridge";
		allActions.add(new ActEatSnack());
		allActions.add(new ActEatMeal());
	}

}