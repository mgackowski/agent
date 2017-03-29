package com.mgackowski.agent.objects;

import com.mgackowski.agent.actions.ActFlush;
import com.mgackowski.agent.actions.ActNumberOne;
import com.mgackowski.agent.actions.ActNumberTwo;

public class ObjToilet extends BasicObject {
	
	public ObjToilet() {
		name = "toilet";
		allActions.add(new ActNumberOne());
		allActions.add(new ActNumberTwo());
		allActions.add(new ActFlush());
	}

}