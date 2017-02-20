package com.fdmgroup.agent.agents;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fdmgroup.agent.objects.ObjectAction;

/**
 * The Agent is the main focus of the program; it has Needs it will seek to satisfy,
 * as well as Individuality which affects how its need levels change.
 * An Agent's "life" begins a series of threads invoked in an implementation of an AgentSim class.
 * The class can and should be extended when a more complex simulation is needed.
 * 
 * TODO: Add method to restore default IVs (and possibly new field/object to store them).
 * @author Mikolaj.Gackowski
 *
 */
public class Agent {
	
	static Logger log = LogManager.getLogger();
	
	private String name;
	private boolean alive;
	
	private BasicIndividuality indivValues;
	private Needs needs;
	
	private ObjectAction currentAction;
	private Queue<ObjectAction> actionQueue = new ConcurrentLinkedQueue<ObjectAction>();
	
	/**
	 * Create a default Agent with five needs - 'FOOD', 'HYGIENE', 'BLADDER', 'FUN' and 'ENERGY',
	 * as well as default individual (deterioration speed) values.
	 * @param name - the name of the new Agent
	 */
	public Agent(String name) {
		this.name = name;
		alive = true;
		indivValues = new FiveIndividuality(1f, 0.5f, 1f, 0.2f, 0.1f);
		needs = new FiveNeeds();
		log.info("New Agent created with name " + this.name);
	}
	
	/**
	 * Create an Agent with five needs - 'FOOD', 'HYGIENE', 'BLADDER', 'FUN' and 'ENERGY',
	 * and set individual values (such as need deterioration speed) to the ones provided
	 * @param name - the name of the new Agent
	 * @param indivValues - Individuality object containing individual values
	 */
	public Agent(String name, BasicIndividuality indivValues) {
		this.name = name;
		alive = true;
		this.indivValues = indivValues;
		needs = new FiveNeeds();
		log.info("New Agent created with name " + this.name);
	}
	
	/**
	 * Create an Agent with custom needs and individual values (such as need deterioration speed).
	 * @param name - the name of the new Agent
	 * @param indivValues - Individuality object containing individual values
	 * @param needs - Needs implementation containing the Agent's needs 
	 */
	public Agent(String name, BasicIndividuality indivValues, Needs needs) {
		this.name = name;
		alive = true;
		this.indivValues = indivValues;
		this.needs = needs;
		log.info("New Agent created with name " + this.name);
	}
	
	/**
	 * Sets the Agent's alive state to false. Can be used to exclude Agent from simulation
	 * or signify a failure state.
	 * @return true if successful
	 */
	public boolean kill() {
		this.alive = false;
		log.info(this.name + " is finished.");
		return true;
	}

	/**
	 * @return the Agent's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name a new name for the Agent
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return true if the Agent is alive
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * @param alive - true to set Agent state to alive, false to set Agent state to dead
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	/**
	 * @return an Individuality object which describes the individual modifiers for need changes
	 */
	public BasicIndividuality getIndivValues() {
		return indivValues;
	}

	/**
	 * @param indivValues an Individuality object which describes the individual modifiers for need changes
	 */
	public void setIndivValues(BasicIndividuality indivValues) {
		this.indivValues = indivValues;
	}

	/**
	 * @return a Queue containing Actions and associated Objects to be performed by the Agent
	 */
	public Queue<ObjectAction> getActionQueue() {
		return actionQueue;
	}

	/**
	 * @param actionQueue a Queue containing Actions and associated Objects to be performed by the Agent
	 */
	public void setActionQueue(Queue<ObjectAction> actionQueue) {
		this.actionQueue = actionQueue;
	}

	/**
	 * @return a Needs object describing the needs an Agent has, and their current levels
	 */
	public Needs getNeeds() {
		return needs;
	}

	/**
	 * @return the current ObjectAction pairing being performed by the Agent
	 */
	public ObjectAction getCurrentAction() {
		return currentAction;
	}

	/**
	 * @param currentAction the current ObjectAction pairing being performed by the Agent
	 */
	public void setCurrentAction(ObjectAction currentAction) {
		this.currentAction = currentAction;
		if(currentAction != null) {
			log.debug("Current action set to: " + currentAction.toString());
		}
		else {
			log.debug("Current action set to null");
		}
		
	}
	
}
