package com.fdmgroup.agent.actions;

import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.objects.UseableObject;
import com.fdmgroup.agent.threads.PerformActionThread;

/**
 * Actions are performed by Agents. They should be as atomic as possible.
 * @author Mikolaj Gackowski
 *
 */
public interface Action {
	
	/*
	 * Retrieves a Promise object - a description of needs the action promises to satisfy.
	 */
	public Promise getPromises();
	
	/*
	 * Retrieves a Consequence object - a description of needs the action actually
	 * will satisfy, along with the next action (if any) to form a chain.
	 */
	public Consequence getConsequences();
	
	/*
	 * Retrieves the name of the action, e.g. "eat snack", "take nap".
	 */
	public String getName();
	
	/*
	 * This should start a PerformActionThread, which uses the information stored
	 * in this class and affect the agent over time (in other words, perform this action).
	 * TODO: Preferably, PerformActionThread should be abstract
	 */
	public PerformActionThread execute(Agent performer, UseableObject usedObject);
	
	/*
	 * Retrieves the object this action is performed with. This is necessary because
	 * Agents interact with Actions directly and do not store information about Objects.
	 * TODO: Consider this in a major refactor, if execute() could be moved to Objects.
	 */
	public UseableObject getTiedObject();
	
	/*
	 * Satiety is the length of time in milliseconds for which the Agent's needs will stop
	 * deteriorating. Recommended to set this equal or greater than the minimum required
	 * length of the action in execute().
	 */
	public int getSatietyLength();

}