package com.mgackowski.agent.objects;

import com.mgackowski.agent.actions.ActRead;

public class ObjBook extends BasicObject {
	
	public ObjBook() {
		name = "book";
		allActions.add(new ActRead());
	}

}