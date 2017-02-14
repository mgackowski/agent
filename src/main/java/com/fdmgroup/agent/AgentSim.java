package com.fdmgroup.agent;

import com.fdmgroup.agent.agents.AgentPool;
import com.fdmgroup.agent.objects.ObjectPool;

public interface AgentSim {
	
	public boolean prepareObjects();
	public boolean prepareAgents();
	public boolean startSim();
	public boolean prepareAndStartSim();
	public AgentPool getAgentPool();
	public ObjectPool getObjectPool();
	
}
