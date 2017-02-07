package com.fdmgroup.agent.agents;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Mikolaj Gackowski
 *
 */
public class AgentPool {
	
	private List<Agent> agents = new ArrayList<Agent>();
	private boolean aliveAgents = false;

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}
	
	public boolean addAgent(Agent newAgent) {
		if (agents.add(newAgent)) {
			updateAliveAgents();
			return true;
		}
		return false;
	}
	
	public boolean removeAgent(Agent newAgent) {
		boolean success = agents.remove(newAgent);
		updateAliveAgents();
		return success;
	}

	public boolean updateAliveAgents() {
		boolean anyoneAlive = false;
		for (Agent thisAgent : agents) {
			if(thisAgent.isAlive()) {
				anyoneAlive = true;
			}
		}
		aliveAgents = anyoneAlive;
		return true;
	}

	public boolean hasAliveAgents() {
		return aliveAgents;
	}

}
