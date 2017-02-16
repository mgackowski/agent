package com.fdmgroup.agent.actions;

import java.util.HashMap;
import java.util.Map;

/**
 * A basic implementation of Action. Can (and should) be extended to create actions.
 * @author Mikolaj.Gackowski
 *
 */
public abstract class BasicAction implements Action{

	protected String name = "";
	protected Promise advertisedPromise = new Promise();
	protected Map<String, Consequence> consequences = new HashMap<String, Consequence>();
	private Action nextAction = null;

	/* (non-Javadoc)
	 * @see com.fdmgroup.agent.actions.Action#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.fdmgroup.agent.actions.Action#getPromises()
	 */
	public Promise getPromises() {
		return advertisedPromise;
	}

	/* (non-Javadoc)
	 * @see com.fdmgroup.agent.actions.Action#getConsequences()
	 */
	public Map<String, Consequence> getConsequences() {
		return consequences;
	}

	/* (non-Javadoc)
	 * @see com.fdmgroup.agent.actions.Action#getConsequence(java.lang.String)
	 */
	public Consequence getConsequence(String needName) {
		return consequences.get(needName);
	}

	/* (non-Javadoc)
	 * @see com.fdmgroup.agent.actions.Action#getNextAction()
	 */
	public Action getNextAction() {
		return nextAction;
	}


	/**
	 * @param nextAction the next action that is to be performed after finishing this one. Can't point to itself.
	 * @return true if successful, false if set to point to itself - then, nextAction is reset to null
	 */
	public boolean setNextAction(Action nextAction) {
		if(nextAction.getClass().equals(this.getClass())) {
			this.nextAction = null;
			return false;
		}
		else {
			this.nextAction = nextAction;
			return true;
		}
	}

}