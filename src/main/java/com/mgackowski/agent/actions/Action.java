package com.mgackowski.agent.actions;

import java.util.Map;

/**
 * Actions are performed when they appear attractive to an Agent. They contain
 * Promises which affect whether they will be added to an Agent's action queue.
 * They also contain Consequences which describe how an Agent's state will
 * change after the Action is performed.
 * 
 * @author Mikolaj.Gackowski
 *
 */
public interface Action {

	/**
	 * Retrieves a Promise object - a description of needs the action promises
	 * to satisfy.
	 */
	public Promise getPromises();

	/**
	 * Retrieves a Map which assigns Strings of need names to Consequence objects.
	 * Consequence objects describe how a need will change when an action is performed.
	 */
	public Map<String, Consequence> getConsequences();

	/**
	 * Retrieves a Consequence object for the given need.
	 * Consequence objects describe how a need will change when an action is performed.
	 * 
	 * @param The name of the need. The suggested format is ALLCAPS e.g. "HUNGER".
	 * @return A consequence object, or null if there is no consequence for the given need
	 */
	public Consequence getConsequence(String needName);

	/**
	 * Retrieves the name of the action, e.g. "eat snack", "take nap".
	 */
	public String getName();

	/**
	 * Retrieves the next action that is to be performed after finishing this one.
	 */
	public Action getNextAction();

}