package com.fdmgroup.agent;

public interface AgentSim {
	
	public boolean prepareObjects();
	public boolean prepareAgents();
	public boolean startSim();
	public Thread startPrintThread();
	public boolean run();
	
}
