package com.fdmgroup.agent.threads;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fdmgroup.agent.actions.Action;
import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.objects.ObjectAction;
import com.fdmgroup.agent.objects.UseableObject;

public class DecisionThread extends Thread {
	
	static Logger log = LogManager.getLogger();
	
	Agent thisAgent;
	List<UseableObject> availableObjects;	//TODO: This can be abstracted out to a Perceive class
	
	public DecisionThread(Agent thisAgent) {
		this.thisAgent = thisAgent;
		this.setName(thisAgent.getName() + "'s decision");
	}
	
	public DecisionThread(Agent thisAgent, List<UseableObject> availableObjects) {
		this.thisAgent = thisAgent;
		this.setName(thisAgent.getName() + "'s decision");
		this.availableObjects = availableObjects;
	}
	
	public void run() {
		log.debug("DecisionThread started for" + thisAgent.getName());
		
		while(thisAgent.isAlive() && !isInterrupted()) {
			
			//Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
			//Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
			//System.out.println(threadArray);
			
			// While Agent has actions in the queue, perform them
			performActionsInQueue();
			
			if (thisAgent.isAlive() && !isInterrupted()) { //Agent might have died while performing Actions
				// Query the environment for possibilities
				Map<ObjectAction,Float> possibilities = queryEnvironmentForPossibilities(availableObjects);	//TODO: Abstract this into another class dependent on ObjectPool
				// Add new action to queue, or do something else 
				reactToPossibilities(possibilities);
			}
		}
	}
	
	public void performActionsInQueue() {
		while(!thisAgent.getActionQueue().isEmpty() && thisAgent.isAlive()) {
			
			try {
				log.info(thisAgent.getName() + " initiates a new thread to perform an action.");
				PerformActionThread perform = new PerformActionThread(thisAgent, 1000);
				perform.start();
				Thread interruptor = new CriticalInterruptorThread(perform, thisAgent);
				interruptor.start();
				perform.join();	//waits
				//listener-interruptor-might interrupt all threads in perform, and it will end naturally
				//listener-interruttor will then set a flag in this thread and end itself
				//if the flag is detected as set, force execution or an optimal action if it exists
				//if action was done successfully, lower the flag
				//if there was no action, keep the flag???
				
			} catch (InterruptedException e) {
				log.debug("DecisionThread interrupted");
				interrupt();
			}
		}
	}
	
	public Map<ObjectAction, Float> queryEnvironmentForPossibilities(List<UseableObject> objectList) {
		Map<ObjectAction,Float> possibilities = new HashMap<ObjectAction,Float>();
		for(UseableObject singleObject : objectList) {
			for (Action singleAction : singleObject.advertiseActions()) {
				possibilities.put(new ObjectAction(singleObject, singleAction), attenuatedScoreActionForAllNeeds(singleAction));
			}
		}
		return possibilities;
	}
	
	//TODO: Write method to update the local object pool
	//TODO: Abstract out the ObjectPool-dependent functionality to a separate Perceive class
	
	public Map<ObjectAction, Float> queryEnvironmentForPossibilities(String singleNeedName, List<UseableObject> objectList) {
		Map<ObjectAction,Float> possibilities = new HashMap<ObjectAction,Float>();
		for(UseableObject singleObject : objectList) {
			for (Action singleAction : singleObject.advertiseActions()) {
				possibilities.put(new ObjectAction(singleObject, singleAction), attenuatedScoreActionForSingleNeed(singleAction, singleNeedName));
			}
		}
		return possibilities;
	}
	
	public void reactToPossibilities(Map<ObjectAction, Float> possibilities) {
		// Pick the best action from the possibilities
		ObjectAction nextAction = pickNextAction(possibilities);
		
		// If there is a next action, add it to the queue
		if (nextAction != null) {
			thisAgent.getActionQueue().add(nextAction);
		}
		else {
			performFallbackActions();
		}
	}
	
	public void performFallbackActions() {
		// Wait for one second and hope for the situation to change
		Thread waitSecond = new WaitThread(1000);
		waitSecond.start();
		//thisAgent.setActionStatus("wait (no compelling actions available)");
		try {
			waitSecond.join();
		}
		catch (InterruptedException e) {
			log.debug("DecisionThread interrupted");
		}
	}
	
	public float simpleScoreActionForAllNeeds(Action thisAction) {
		float sum = 0;
		for (String needName : thisAgent.getNeeds().getNeeds().keySet()) {
			float current = thisAgent.getNeeds().getNeed(needName); // add current need level
			float delta = thisAction.getPromises().getChange(needName); // add advertised need change
			
			if ((current + delta) <= 100) {
				sum += current;
				sum += delta;
			}
			else {
				sum += current;
			}
		}
		return sum;
	}
	
	public float attenuatedScoreActionForAllNeeds(Action thisAction) {
		
		//TODO: Different equations for different needs?
		//TODO: Individual modifiers?
		//TODO: Division by zero exception?
		
		/* Best function I could come up with, more urgent needs are more attractive
		 * Score drops significantly for actions which overprovide
		 * 
		 * 	y = 100/c - 100/(c+d) {0 < c+d <= 100}
		 * 	y = 100/c - 100/(c+d) - c/1000(d+c-100)^2 {c+d > 100}
		 * 	where c is current value and d is promised delta
		 * 
		 */
		float sum = 0;
		for (String needName : thisAgent.getNeeds().getNeeds().keySet()) {
			sum += attenuatedScoreActionForSingleNeed(thisAction, needName);
		}
		return sum;
	}
	
	public float attenuatedScoreActionForSingleNeed(Action thisAction, String needName) {
		
		float score = 0;
		float current = thisAgent.getNeeds().getNeed(needName);
		float delta = thisAction.getPromises().getChange(needName);
		
		if (0 < (current + delta) && (current+delta) <= 100) {
			score = 100/current - 100/(current+delta);
		}
		if ((current+delta) > 100) {
			score = 100/current - 100/(current+delta) - current/1000*(current+delta-100)*(current+delta-100);
		}
		return score;
	}
	
	public ObjectAction pickNextAction(Map<ObjectAction,Float> possibilities) {
		if (possibilities.isEmpty()) {
			log.info("No actions are available to " + thisAgent.getName());
			return null;
		}
		
		log.debug("Available decisions: " + possibilities.keySet().toString());
		
		float maxScore = 0;	// threshold below which being idle is considered better
		ObjectAction bestAction = null;
		for(ObjectAction thisAction : possibilities.keySet()) {
			if (possibilities.get(thisAction) > maxScore) {
				maxScore = possibilities.get(thisAction);
				bestAction = thisAction;
			}
		}
		//TODO: this can result in a nullPointer
		//log.info(thisAgent.getName() + " picks " + bestAction.getName() + " as the best action (score: " + String.format("%.2f", maxScore) + ")");
		return bestAction;
	}
	
	public void setAvailableObjects(List<UseableObject> availableObjects) {
		this.availableObjects = availableObjects;
	}

	public void introduceCriticalState(String needName, PerformActionThread actionThread) {
		// if critical flag is true
		// execute the block once to cancel current actions
		// set it to false
		log.debug("satisfyCriticalState() of need " + needName + " called for Agent " + thisAgent.getName());
		if (thisAgent.getCurrentAction() != null) {
			for (Thread runningThread : actionThread.getThreads()) {
				if (!runningThread.isInterrupted()) {
					runningThread.interrupt();
				}
			}
		}
	}
}
