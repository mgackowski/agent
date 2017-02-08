package com.fdmgroup.agent.actions;

import java.util.Map;

import com.fdmgroup.agent.objects.ObjectAction;

/**
 * Actions are performed when they appear attractive to an Agent. They contain
 * Promises which affect whether they will be added to an Agent's action queue.
 * They also contain Consequences which describe how an Agent's state will
 * change after the Action is performed.
 * 
 * @author Mikolaj Gackowski
 *
 */
public interface Action {

	/**
	 * Retrieves a Promise object - a description of needs the action promises
	 * to satisfy.
	 */
	public Promise getPromises();

	/*
	 *
	 */
	public Map<String, Consequence> getConsequences();

	/**
	 * TODO: This needs to return zero when the need is not defined. Perhaps
	 * have it return an Exception?
	 * 
	 * @param needName
	 * @return
	 */
	public Consequence getConsequence(String needName);

	/*
	 * Retrieves the name of the action, e.g. "eat snack", "take nap".
	 */
	public String getName();

	public Action getNextAction();

}