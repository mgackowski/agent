package com.mgackowski.agent.objects;

import java.util.List;

import com.mgackowski.agent.actions.Action;

/**
 * An Object is placed in the simulation via an ObjectPool and contains Actions which can be
 * performed by Agents. Objects can "advertise" the benefits of the Actions they contain
 * by using Promises, although those Promises need not necessarily match the Consequences.
 * More Actions may exist for the Objects than are advertised.
 * @author Mikolaj.Gackowski
 * 
 * TODO: Implement a BasicObject in a similar manner to BasicAction
 *
 */
public interface UseableObject {
	
	/**
	 * @return a List of Actions available (not necessarily all Actions, just the ones possible in the situation)
	 */
	public List<Action> advertiseActions();
	
	/**
	 * @return the name of the object, usually lower case
	 */
	public String getName();
	
	/**
	 * Set whether the object is currently being used; this can be a factor in determining which actions are available.
	 * @param beingUsed true if object is being used, false if it is not
	 */
	public void setBeingUsed(boolean beingUsed);
	
	/**
	 * @return true if object is being used, false if it is not
	 */
	public boolean isBeingUsed();

}