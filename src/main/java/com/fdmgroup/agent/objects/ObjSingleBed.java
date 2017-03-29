package com.fdmgroup.agent.objects;

import com.fdmgroup.agent.actions.ActSleep;
import com.fdmgroup.agent.actions.ActTakeNap;

public class ObjSingleBed extends BasicObject {
	
	public ObjSingleBed() {
		name = "single bed";
		allActions.add(new ActSleep());
		allActions.add(new ActTakeNap());
	}

}