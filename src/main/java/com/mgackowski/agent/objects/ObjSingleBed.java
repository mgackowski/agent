package com.mgackowski.agent.objects;

import com.mgackowski.agent.actions.ActSleep;
import com.mgackowski.agent.actions.ActTakeNap;

public class ObjSingleBed extends BasicObject {
	
	public ObjSingleBed() {
		name = "single bed";
		allActions.add(new ActSleep());
		allActions.add(new ActTakeNap());
	}

}