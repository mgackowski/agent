package com.fdmgroup.agent.objects;

import com.fdmgroup.agent.actions.ActFlush;
import com.fdmgroup.agent.actions.ActNumberOne;
import com.fdmgroup.agent.actions.ActNumberTwo;

public class ObjToilet extends BasicObject {
	
	public ObjToilet() {
		name = "toilet";
		allActions.add(new ActNumberOne());
		allActions.add(new ActNumberTwo());
		allActions.add(new ActFlush());
	}

}