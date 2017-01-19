package com.fdmgroup.agent.agents;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.fdmgroup.agent.actions.Action;
import com.fdmgroup.agent.threads.AgentDecisionThread;
import com.fdmgroup.agent.threads.AgentDeteriorateThread;

/**
 * The Agent is the main focus of the program; it has Needs it will seek to satisfy,
 * as well as Individuality which affects how its need levels change.
 * An Agent's "life" begins a series of threads which affect its needs and individual values,
 * most notably a deterioration thread (Agent's need levels decrease over time) and a
 * decision making thread (performing Actions and picking the best available ones).
 * The class can and should be extended when a more complex simulation is needed.
 * //TODO: Add method to restore default IVs (and possibly new field/object to store them).
 * @author Mikolaj Gackowski
 *
 */
public class Agent {
	
	
	private String name;
	private boolean alive;
	
	private String actionStatus;
	private Individuality indivValues;
	
	private Needs needs = new FiveNeeds();
	private Queue<Action> actionQueue = new ConcurrentLinkedQueue<Action>();
	
	public Agent(String name) {
		indivValues = new FiveIndividuality(1f, 0.5f, 1f, 0.2f, 0.1f);
		actionStatus = "...";
		this.name = name;
		alive = true;
	}
	
	public Agent(String name, Individuality indivValues) {
		this.indivValues = indivValues;
		this.name = name;
		alive = true;
		actionStatus = "...";
	}
	
	public boolean startLife() {
		
		Thread deteriorate = new AgentDeteriorateThread(this);
		deteriorate.start();
		Thread decide = new AgentDecisionThread(this);
		decide.start();
		return true;
	}
	
	public boolean kill() {
		this.actionStatus = "dead";
		this.alive = false;
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

	public String getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}

	public Individuality getIndivValues() {
		return indivValues;
	}

	public void setIndivValues(Individuality indivValues) {
		this.indivValues = indivValues;
	}

	public Queue<Action> getActionQueue() {
		return actionQueue;
	}

	public void setActionQueue(Queue<Action> actionQueue) {
		this.actionQueue = actionQueue;
	}

	public Needs getNeeds() {
		return needs;
	}
}
