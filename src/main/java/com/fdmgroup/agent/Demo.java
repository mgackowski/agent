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

/**
 * An implementation of AgentSim which prepares a set of sample Objects and Agents, modelled
 * after humans in a home environment.
 * @author Mikolaj.Gackowski
 *
 */
public class Demo implements AgentSim {
	
	static Logger log = LogManager.getLogger();
	
	private AgentPool agents = new AgentPool();
	private ObjectPool objects = new ObjectPool();
	long stepMillis = 100;

	/* (non-Javadoc)
	 * @see com.fdmgroup.agent.AgentSim#prepareObjects()
	 */
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

	/* (non-Javadoc)
	 * @see com.fdmgroup.agent.AgentSim#prepareAgents()
	 */
	public boolean prepareAgents() {
		
		boolean success = true;
		
		success = agents.addAgent(new Agent("Bob", new FiveIndividuality(1.2f,0.6f,1.2f,0.2f,0.12f))) && success;
		success = agents.addAgent(new Agent("Alice", new FiveIndividuality(1f,0.5f,1f,0.2f,0.1f))) && success;
        
		return success;
	}

	/* (non-Javadoc)
	 * @see com.fdmgroup.agent.AgentSim#startSim()
	 */
	public boolean startSim() {

        for(Agent thisAgent : agents.getAgents()) {
        	Thread deteriorate = new DeteriorationThread(thisAgent);
        	deteriorate.start();
        	Thread decide = new DecisionThread(thisAgent, objects.getObjects());
        	decide.start();
        	try {
				Thread.sleep(stepMillis); // TODO: speed
			} catch (InterruptedException e) {
				log.error("startSim() was interrupted.");
				e.printStackTrace();
				return false;
			}
        }
        return true;
	}

	/* (non-Javadoc)
	 * @see com.fdmgroup.agent.AgentSim#prepareAndStartSim()
	 */
	public boolean prepareAndStartSim() {
		
		boolean success = true;
		
		success = prepareObjects() && success;
		success = prepareAgents() && success;
		success = startSim() && success;
		
		return success;
	}

	/* (non-Javadoc)
	 * @see com.fdmgroup.agent.AgentSim#getAgentPool()
	 */
	public AgentPool getAgentPool() {
		return agents;
	}

	/* (non-Javadoc)
	 * @see com.fdmgroup.agent.AgentSim#getObjectPool()
	 */
	public ObjectPool getObjectPool() {
		return objects;
	}

}
