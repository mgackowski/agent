package com.fdmgroup.agent.objects;

import com.fdmgroup.agent.actions.ActRead;

public class ObjBook extends BasicObject {
	
	public ObjBook() {
		name = "book";
		allActions.add(new ActRead());
	}

}