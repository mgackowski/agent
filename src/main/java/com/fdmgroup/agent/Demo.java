package com.fdmgroup.agent;

import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.agents.AgentPool;
import com.fdmgroup.agent.agents.FiveIndividuality;
import com.fdmgroup.agent.objects.ObjBook;
import com.fdmgroup.agent.objects.ObjFridge;
import com.fdmgroup.agent.objects.ObjShower;
import com.fdmgroup.agent.objects.ObjSingleBed;
import com.fdmgroup.agent.objects.ObjSink;
import com.fdmgroup.agent.objects.ObjToilet;
import com.fdmgroup.agent.objects.ObjectPool;
import com.fdmgroup.agent.threads.GlobalDisplayInfobarThread;

public class Demo implements AgentSim {

	public boolean prepareObjects() {
		//TODO: Return false if unsuccessful

        ObjectPool.getInstance().addObject(new ObjFridge());
        ObjectPool.getInstance().addObject(new ObjSingleBed());
        ObjectPool.getInstance().addObject(new ObjToilet());
        ObjectPool.getInstance().addObject(new ObjShower());
        ObjectPool.getInstance().addObject(new ObjSink());
        ObjectPool.getInstance().addObject(new ObjBook());
        
        return true;
	}

	public boolean prepareAgents() {
		//TODO: Return false if unsuccessful
        AgentPool.getInstance().addAgent(new Agent("Bob", new FiveIndividuality(1.2f,0.6f,1.2f,0.2f,0.12f)));
        AgentPool.getInstance().addAgent(new Agent("Alice", new FiveIndividuality(1f,0.5f,1f,0.2f,0.1f)));
        
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
