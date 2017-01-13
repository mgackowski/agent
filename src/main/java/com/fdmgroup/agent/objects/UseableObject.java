package com.fdmgroup.agent.objects;

import java.util.List;

import com.fdmgroup.agent.actions.Action;

public interface UseableObject {
	
	public List<Action> advertiseActions();
	public String getName();

}