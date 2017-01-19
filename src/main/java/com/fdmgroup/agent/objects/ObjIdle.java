package com.fdmgroup.agent.objects;

import java.util.ArrayList;
import java.util.List;

import com.fdmgroup.agent.actions.ActWait;
import com.fdmgroup.agent.actions.Action;

/**
 * Deprecated. The AgentDecisionThread will now use WaitThread as fallback when
 * no beneficial actions are available.
 * @author Mikolaj.Gackowski
 *
 */
public class ObjIdle implements UseableObject {
	
	List<Action> allActions = new ArrayList<Action>();
	String name = "[idle object]";
	boolean beingUsed = false;
	
	public ObjIdle() {
		allActions.add(new ActWait(this));
	}
	
	public synchronized List<Action> advertiseActions() {
		/* Special case: be available even when used */
		return allActions;
	}

	public String getName() {
		return this.name;
	}

	public boolean isBeingUsed() {
		return beingUsed;
	}

	public void setBeingUsed(boolean beingUsed) {
		this.beingUsed = beingUsed;
	}

}