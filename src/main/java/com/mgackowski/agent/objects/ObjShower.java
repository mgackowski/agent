package com.mgackowski.agent.objects;

import com.mgackowski.agent.actions.ActTakeQuickShower;
import com.mgackowski.agent.actions.ActTakeShower;

public class ObjShower extends BasicObject {
	
	public ObjShower() {
		name = "shower";
		allActions.add(new ActTakeShower());
		allActions.add(new ActTakeQuickShower());
	}

}