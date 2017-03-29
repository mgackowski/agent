package com.mgackowski.agent.objects;

import java.util.ArrayList;
import java.util.List;

import com.mgackowski.agent.actions.Action;

/**
 * A basic implementation of UseableObject. Can (and should) be extended to create objects.
 * @author Mikolaj.Gackowski
 *
 */
public abstract class BasicObject implements UseableObject {
	
	protected List<Action> allActions = new ArrayList<Action>();
	protected String name = "";
	protected boolean beingUsed = false;

	/* (non-Javadoc)
	 * @see com.mgackowski.agent.objects.UseableObject#advertiseActions()
	 */
	public List<Action> advertiseActions() {
		if(beingUsed) {
			return new ArrayList<Action>();
		}
		else {
			return allActions;
		}
	}

	/* (non-Javadoc)
	 * @see com.mgackowski.agent.objects.UseableObject#getName()
	 */
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see com.mgackowski.agent.objects.UseableObject#isBeingUsed()
	 */
	public boolean isBeingUsed() {
		return beingUsed;
	}

	/* (non-Javadoc)
	 * @see com.mgackowski.agent.objects.UseableObject#setBeingUsed(boolean)
	 */
	public void setBeingUsed(boolean beingUsed) {
		this.beingUsed = beingUsed;
	}
	
}
