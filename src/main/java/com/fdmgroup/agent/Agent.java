package com.fdmgroup.agent;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.fdmgroup.agent.actions.Action;
import com.fdmgroup.agent.threads.AgentDecisionThread;
import com.fdmgroup.agent.threads.AgentDeteriorateThread;

public class Agent {
	//TODO: Increase abstraction by providing a basic, extensible agent
	
	private BasicNeeds needs = new BasicNeeds();
	private String name;
	private boolean alive;
	private Individuality indivValues;
	private String actionStatus;
	private Queue<Action> actionQueue = new ConcurrentLinkedQueue<Action>();
	
	public Agent(String name) {
		this.name = name;
		alive = true;
		actionStatus = "Just created.";
		indivValues = new BasicIndividuality(1f, 0.1f);
	}
	
	public Agent(String name, Individuality indivValues) {
		this.name = name;
		this.indivValues = indivValues;
		alive = true;
		actionStatus = "Just created.";
	}
	
	public boolean startLife() {
		
		Thread deteriorate = new AgentDeteriorateThread(this);
		deteriorate.start();
		actionStatus = "From now on, slowly dying.";
		Thread decide = new AgentDecisionThread(this);
		decide.start();
		
		return true;
	}
	
	public boolean kill() {
		this.actionStatus = "Dead.";
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

	public BasicNeeds getNeeds() {
		return needs;
	}
}
