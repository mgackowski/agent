package com.fdmgroup.agent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.agents.AgentPool;
import com.fdmgroup.agent.agents.BasicIndividuality;
import com.fdmgroup.agent.agents.FiveIndividuality;
import com.fdmgroup.agent.objects.ObjBook;
import com.fdmgroup.agent.objects.ObjFridge;
import com.fdmgroup.agent.objects.ObjShower;
import com.fdmgroup.agent.objects.ObjSingleBed;
import com.fdmgroup.agent.objects.ObjSink;
import com.fdmgroup.agent.objects.ObjToilet;
import com.fdmgroup.agent.objects.ObjectPool;
import com.fdmgroup.agent.threads.DecisionThread;
import com.fdmgroup.agent.threads.DeteriorationThread;

public class Demo implements AgentSim {
	
	static Logger log = LogManager.getLogger();
	
	private AgentPool agents = new AgentPool();
	private ObjectPool objects = new ObjectPool();

	public boolean prepareObjects() {
		//TODO: Return false if unsuccessful

        objects.addObject(new ObjFridge());
        objects.addObject(new ObjSingleBed());
        objects.addObject(new ObjToilet());
        objects.addObject(new ObjShower());
        objects.addObject(new ObjSink());
        objects.addObject(new ObjBook());
        
        return true;
	}

	public boolean prepareAgents() {
		//TODO: Return false if unsuccessful
        agents.addAgent(new Agent("Bob", new FiveIndividuality(1.2f,0.6f,1.2f,0.2f,0.12f)));
        agents.addAgent(new Agent("Alice", new FiveIndividuality(1f,0.5f,1f,0.2f,0.1f)));
        
		return true;
	}

	public boolean startSim() {
		//TODO: Return true/false
		boolean success = false;
        for(Agent thisAgent : agents.getAgents()) {
        	Thread deteriorate = new DeteriorationThread(thisAgent);
        	deteriorate.start();
        	Thread decide = new DecisionThread(thisAgent, objects.getObjects());
        	decide.start();
        	try {
				Thread.sleep(100); // prevent from accessing objects in the same instant
			} catch (InterruptedException e) {
				log.info("startSim() was interrupted.");
				e.printStackTrace();
			}
        }
        return success;
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
