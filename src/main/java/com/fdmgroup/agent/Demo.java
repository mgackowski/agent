package com.fdmgroup.agent;

import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.agents.AgentPool;
import com.fdmgroup.agent.agents.FiveIndividuality;
import com.fdmgroup.agent.deprecated.GlobalDisplayInfobarThread;
import com.fdmgroup.agent.objects.ObjFridge;
import com.fdmgroup.agent.objects.ObjectPool;

public class Demo implements AgentSim {
	
	private AgentPool agents = new AgentPool();
	private ObjectPool objects = new ObjectPool();

	public boolean prepareObjects() {
		//TODO: Return false if unsuccessful

        objects.addObject(new ObjFridge());
        //TODO: Handle this mess
        /*ObjectPool.getInstance().addObject(new ObjSingleBed());
        ObjectPool.getInstance().addObject(new ObjToilet());
        ObjectPool.getInstance().addObject(new ObjShower());
        ObjectPool.getInstance().addObject(new ObjSink());
        ObjectPool.getInstance().addObject(new ObjBook());*/
        
        return true;
	}

	public boolean prepareAgents() {
		//TODO: Return false if unsuccessful
        agents.addAgent(new Agent("Bob", new FiveIndividuality(1.2f,0.6f,1.2f,0.2f,0.12f)));
        agents.addAgent(new Agent("Alice", new FiveIndividuality(1f,0.5f,1f,0.2f,0.1f)));
        
		return true;
	}

	public boolean startSim() {
		
		boolean success = false;
        for(Agent thisAgent : agents.getAgents()) {
        	// start a deteriorate thread and pass in the agent
        	// start a decision thread and pass in the agent
        }
        return success;
	}

	public Thread startPrintThread() {
		Thread gdit = new GlobalDisplayInfobarThread();
        gdit.start();
        return gdit;
	}

	public boolean run() {
		//TODO: Return false if unsuccessful
		
		prepareObjects();
		prepareAgents();
		startSim();
		return true;
	}

	public AgentPool getAgentPool() {
		return agents;
	}

	public ObjectPool getObjectPool() {
		return objects;
	}

}
