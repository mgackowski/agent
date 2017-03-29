package com.mgackowski.agent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mgackowski.agent.agents.Agent;
import com.mgackowski.agent.agents.AgentPool;
import com.mgackowski.agent.agents.FiveIndividuality;
import com.mgackowski.agent.objects.ObjBook;
import com.mgackowski.agent.objects.ObjFridge;
import com.mgackowski.agent.objects.ObjShower;
import com.mgackowski.agent.objects.ObjSingleBed;
import com.mgackowski.agent.objects.ObjSink;
import com.mgackowski.agent.objects.ObjToilet;
import com.mgackowski.agent.objects.ObjectPool;
import com.mgackowski.agent.threads.DecisionThread;
import com.mgackowski.agent.threads.DeteriorationThread;

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
	 * @see com.mgackowski.agent.AgentSim#prepareObjects()
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
	 * @see com.mgackowski.agent.AgentSim#prepareAgents()
	 */
	public boolean prepareAgents() {
		
		boolean success = true;
		
		success = agents.addAgent(new Agent("Bob", new FiveIndividuality(1.2f,0.6f,1.2f,0.2f,0.12f))) && success;
		success = agents.addAgent(new Agent("Alice", new FiveIndividuality(1f,0.5f,1f,0.2f,0.1f))) && success;
        
		return success;
	}

	/* (non-Javadoc)
	 * @see com.mgackowski.agent.AgentSim#startSim()
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
	 * @see com.mgackowski.agent.AgentSim#prepareAndStartSim()
	 */
	public boolean prepareAndStartSim() {
		
		boolean success = true;
		
		success = prepareObjects() && success;
		success = prepareAgents() && success;
		success = startSim() && success;
		
		return success;
	}

	/* (non-Javadoc)
	 * @see com.mgackowski.agent.AgentSim#getAgentPool()
	 */
	public AgentPool getAgentPool() {
		return agents;
	}

	/* (non-Javadoc)
	 * @see com.mgackowski.agent.AgentSim#getObjectPool()
	 */
	public ObjectPool getObjectPool() {
		return objects;
	}

}
