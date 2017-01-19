package com.fdmgroup.agent;

import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.agents.AgentPool;
import com.fdmgroup.agent.agents.BasicIndividuality;
import com.fdmgroup.agent.objects.ObjFridge;
import com.fdmgroup.agent.objects.ObjSingleBed;
import com.fdmgroup.agent.objects.ObjectPool;
import com.fdmgroup.agent.threads.GlobalDisplayInfobarThread;

public class Demo implements AgentSim {

	public boolean prepareObjects() {
		//TODO: Return false if unsuccessful

        ObjectPool.getInstance().addObject(new ObjFridge());
        ObjectPool.getInstance().addObject(new ObjSingleBed());
        
        return true;
	}

	public boolean prepareAgents() {
		//TODO: Return false if unsuccessful
        AgentPool.getInstance().addAgent(new Agent("Bob", new BasicIndividuality(1.3f, 1.22f)));
        AgentPool.getInstance().addAgent(new Agent("Alice", new BasicIndividuality(1.1f, 1.16f)));
        
		return true;
	}

	public boolean startSim() {
		
		boolean success = true;
        for(Agent thisAgent : AgentPool.getInstance().getAgents()) {
        	if(!thisAgent.startLife()) {
        		success = false;
        	};
        	try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return false;
			}
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

}
