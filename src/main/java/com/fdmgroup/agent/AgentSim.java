package com.fdmgroup.agent;

import com.fdmgroup.agent.agents.AgentPool;
import com.fdmgroup.agent.objects.ObjectPool;

public interface AgentSim {
	
	public boolean prepareObjects();
	public boolean prepareAgents();
	public boolean startSim();
	public Thread startPrintThread(); //TODO: Perhaps don't force implementation of this
	public boolean run();
	public AgentPool getAgentPool();
	public ObjectPool getObjectPool();
	
}
