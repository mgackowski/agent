package com.fdmgroup.agent.objects;

import com.fdmgroup.agent.actions.ActWashHands;

public class ObjSink extends BasicObject {
	
	public ObjSink() {
		name = "sink";
		allActions.add(new ActWashHands());
	}

}