package com.fdmgroup.agent.agents;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fdmgroup.agent.objects.ObjectAction;

/**
 * TODO: Rewrite comment to include refactor
 * The Agent is the main focus of the program; it has Needs it will seek to satisfy,
 * as well as Individuality which affects how its need levels change.
 * An Agent's "life" begins a series of threads which affect its needs and individual values,
 * most notably a deterioration thread (Agent's need levels decrease over time) and a
 * decision making thread (performing Actions and picking the best available ones).
 * The class can and should be extended when a more complex simulation is needed.
 * TODO: Add method to restore default IVs (and possibly new field/object to store them).
 * @author Mikolaj Gackowski
 *
 */
public class Agent {
	
	static Logger log = LogManager.getLogger();
	
	private String name;
	private boolean alive;
	
	private Individuality indivValues;	//TODO: Have base values and modified values
	
	private Needs needs = new FiveNeeds();
	
	private ObjectAction currentAction;
	private Queue<ObjectAction> actionQueue = new ConcurrentLinkedQueue<ObjectAction>();
	
	public Agent(String name) {
		this.name = name;
		alive = true;
		indivValues = new FiveIndividuality(1f, 0.5f, 1f, 0.2f, 0.1f);
		log.info("New Agent created with name " + this.name);
	}
	
	public Agent(String name, Individuality indivValues) {
		this.name = name;
		alive = true;
		this.indivValues = indivValues;
		log.info("New Agent created with name " + this.name);
	}
	
	public boolean kill() {
		this.alive = false;
		log.info(this.name + " is finished.");
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public Individuality getIndivValues() {
		return indivValues;
	}

	public void setIndivValues(Individuality indivValues) {
		this.indivValues = indivValues;
	}

	public Queue<ObjectAction> getActionQueue() {
		return actionQueue;
	}

	public void setActionQueue(Queue<ObjectAction> actionQueue) {
		this.actionQueue = actionQueue;
	}

	public Needs getNeeds() {
		return needs;
	}

	public ObjectAction getCurrentAction() {
		return currentAction;
	}

	public void setCurrentAction(ObjectAction currentAction) {
		this.currentAction = currentAction;
	}
	
}
