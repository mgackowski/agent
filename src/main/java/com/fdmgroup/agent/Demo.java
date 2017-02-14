package com.fdmgroup.agent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import com.fdmgroup.agent.threads.DecisionThread;
import com.fdmgroup.agent.threads.DeteriorationThread;

public class Demo implements AgentSim {
	
	static Logger log = LogManager.getLogger();
	
	private AgentPool agents = new AgentPool();
	private ObjectPool objects = new ObjectPool();

	public boolean prepareObjects() {
		boolean success = true;

        success = objects.addObject(new ObjFridge()) && success;
        success = objects.addObject(new ObjSingleBed()) && success;
        success = objects.addObject(new ObjToilet()) && success;
        success = objects.addObject(new ObjShower()) && success;
        success = objects.addObject(new ObjSink()) && success;
        success = objects.addObject(new ObjBook()) && success;
        
        return success;
	}

	public boolean prepareAgents() {
		
		boolean success = true;
		
		success = agents.addAgent(new Agent("Bob", new FiveIndividuality(1.2f,0.6f,1.2f,0.2f,0.12f))) && success;
		success = agents.addAgent(new Agent("Alice", new FiveIndividuality(1f,0.5f,1f,0.2f,0.1f))) && success;
        
		return success;
	}

	public boolean startSim() {

        for(Agent thisAgent : agents.getAgents()) {
        	Thread deteriorate = new DeteriorationThread(thisAgent);
        	deteriorate.start();
        	Thread decide = new DecisionThread(thisAgent, objects.getObjects());
        	decide.start();
        	try {
				Thread.sleep(100); // prevent from accessing objects in the same instant
			} catch (InterruptedException e) {
				log.error("startSim() was interrupted.");
				e.printStackTrace();
				return false;
			}
        }
        return true;
	}

	public boolean prepareAndStartSim() {
		
		boolean success = true;
		
		success = prepareObjects() && success;
		success = prepareAgents() && success;
		success = startSim() && success;
		
		return success;
	}

	public AgentPool getAgentPool() {
		return agents;
	}

	public ObjectPool getObjectPool() {
		return objects;
	}

}
