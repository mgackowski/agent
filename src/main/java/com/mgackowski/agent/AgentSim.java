package com.mgackowski.agent;

import com.mgackowski.agent.agents.AgentPool;
import com.mgackowski.agent.objects.ObjectPool;

/**
 * A general simulation which requires implementing a pool of Agents, Objects,
 * and a way to start a simulation using those pools.
 * @author Mikolaj.Gackowski
 *
 */
public interface AgentSim {
	
	/**
	 * Add simulated UseableObjects to the ObjectPool before simulation starts.
	 * @return true if successful
	 */
	public boolean prepareObjects();
	
	/**
	 * Add agents to the AgentPool before simulation starts.
	 * @return true if successful
	 */
	public boolean prepareAgents();
	
	
	/**
	 * Invoke the necessary methods and threads to start the simulation.
	 * @return true if successful
	 */
	public boolean startSim();
	
	/**
	 * Prepare the objects and conditions necessary to run the simulation, then run the simulation.
	 * @return true if successful
	 */
	public boolean prepareAndStartSim();
	
	/**
	 * Accessor for the AgentPool containing Agents in this simulation.
	 * @return the AgentPool in this simulation
	 */
	public AgentPool getAgentPool();
	
	/**
	 * Accessor for the ObjectPool containing Objects in this simulation.
	 * @return the ObjectPool in this simulation
	 */
	public ObjectPool getObjectPool();
	
}
