package com.mgackowski.agent.objects;

import com.mgackowski.agent.actions.ActWashHands;

public class ObjSink extends BasicObject {
	
	public ObjSink() {
		name = "sink";
		allActions.add(new ActWashHands());
	}

}