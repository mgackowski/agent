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
	protected Action nextAction = null;

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

}