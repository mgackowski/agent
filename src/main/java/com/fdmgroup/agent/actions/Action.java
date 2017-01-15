package com.fdmgroup.agent.actions;

import com.fdmgroup.agent.Agent;
import com.fdmgroup.agent.objects.UseableObject;

public interface Action {
	
	public Promise getPromises();	//e.g. food+30, sleep+40
	public Consequence getConsequences();
	public String getName();
	public Thread execute(Agent performer, UseableObject usedObject);
	public UseableObject getTiedObject();
	public int getSatietyLength();

}