package com.fdmgroup.agent.objects;

import com.fdmgroup.agent.actions.ActTakeQuickShower;
import com.fdmgroup.agent.actions.ActTakeShower;

public class ObjShower extends BasicObject {
	
	public ObjShower() {
		name = "shower";
		allActions.add(new ActTakeShower());
		allActions.add(new ActTakeQuickShower());
	}

}