package com.mgackowski.agent.objects;

import com.mgackowski.agent.actions.Action;

/**
 * A simple object representing a pairing of an action and the object used to perform the action.
 * @author Mikolaj.Gackowski
 */
public class ObjectAction {
	
	private UseableObject object;
	private Action action;
	
	/**
	 * @param object an object used to perform the action
	 * @param action an action that belongs to the object
	 */
	public ObjectAction(UseableObject object, Action action) {
		this.object = object;
		this.action = action;
	}

	/**
	 * @return an object used to perform the action
	 */
	public UseableObject getObject() {
		return object;
	}

	/**
	 * @return an action that belongs to the object
	 */
	public Action getAction() {
		return action;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (object != null && action != null) {
			return "ObjectAction [" + object.getName() + " : " + action.getName() + "]";
		}
		else {
			return "ObjectAction [null object or action]";
		}
	}
	
	
	
}
