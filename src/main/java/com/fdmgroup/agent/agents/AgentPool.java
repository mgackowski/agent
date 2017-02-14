package com.fdmgroup.agent.agents;

import java.util.ArrayList;
import java.util.List;

/**
 * A pool containing all Agents present in a given simulation. Contains methods to add, remove
 * and retrieve Agents.
 * @author Mikolaj.Gackowski
 *
 */
public class AgentPool {
	
	private List<Agent> agents = new ArrayList<Agent>();
	private boolean aliveAgents = false;

	/**
	 * @return A List of Agents in the pool
	 */
	public List<Agent> getAgents() {
		return agents;
	}

	/**
	 * @param agents A List of Agents in the pool
	 */
	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}
	
	/**
	 * @param newAgent a new Agent to add to the AgentPool
	 * @return true if new Agent was successfully added
	 */
	public boolean addAgent(Agent newAgent) {
		if (agents.add(newAgent)) {
			updateAliveAgents();
			return true;
		}
		return false;
	}
	
	/**
	 * Removes the Agent from the pool. To keep the Agent in the simulation but prevent execution of
	 * associated threads, consider invoking kill() on the Agent instead.
	 * @param agentToRemove the Agent to remove from the pool
	 * @return true if the Agent has been successfully removed
	 */
	public boolean removeAgent(Agent agentToRemove) {
		boolean success = agents.remove(agentToRemove);
		updateAliveAgents();
		return success;
	}

	/**
	 * Update the anyoneAlive field of this Pool.
	 * @return true if there are live Agents in the pool, false if none are live.
	 */
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

	/**
	 * For the most up-to-date result, call updateAliveAgents() before invoking this.
	 * @return true if there are live Agents in the pool, false if none are live.
	 */
	public boolean hasAliveAgents() {
		return aliveAgents;
	}

}
