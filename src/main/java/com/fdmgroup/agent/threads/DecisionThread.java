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

/**
 * A Decision Thread for a given Agent, perhaps the most important part of the program.
 * 	1. Actions from the Agent's action queue are performed;
 * 	2. If there are no more Actions, this thread searches for more;
 * 	3. Actions are scored and optimal ones are added to the action queue
 * 	4. Fallback actions such as waiting are performed if there are no high scoring Actions available.
 * 
 * TODO: Write method to update the local object pool
 * TODO: Abstract out the ObjectPool-dependent functionality to a separate Perceive class
 * 
 * @author Mikolaj.Gackowski
 *
 */

public class DecisionThread extends Thread {
	
	static Logger log = LogManager.getLogger();
	
	Agent thisAgent;
	List<UseableObject> availableObjects;
	long stepMillis = 100;
	
	/**
	 * @param thisAgent the Agent on behalf of whom decisions are made
	 */
	public DecisionThread(Agent thisAgent) {
		this.thisAgent = thisAgent;
		this.setName(thisAgent.getName() + "'s decision");
	}
	
	/**
	 * @param thisAgent the Agent on behalf of whom decisions are made
	 * @param availableObjects a List of Objects for the Agent to choose from when making decisions
	 */
	public DecisionThread(Agent thisAgent, List<UseableObject> availableObjects) {
		this.thisAgent = thisAgent;
		this.setName(thisAgent.getName() + "'s decision");
		this.availableObjects = availableObjects;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		log.debug("DecisionThread started for" + thisAgent.getName());
		
		while(thisAgent.isAlive() && !isInterrupted()) {

			performActionsInQueue();
			
			if (thisAgent.isAlive() && !isInterrupted()) {
				Map<ObjectAction,Float> possibilities = queryEnvironmentForPossibilities(availableObjects);
				reactToPossibilities(possibilities);
			}
		}
	}
	
	/**
	 * Perform every action in the queue in sequence by creating PerformActionThreads.
	 * Run CriticalInterruptorThreads to listen for critical needs and interrupt actions if necessary.
	 */
	public void performActionsInQueue() {
		while(!thisAgent.getActionQueue().isEmpty() && thisAgent.isAlive()) {
			
			try {
				log.info(thisAgent.getName() + " initiates a new thread to perform an action.");
				PerformActionThread perform = new PerformActionThread(thisAgent, 10 * stepMillis); //TODO: Speed
				perform.start();
				Thread interruptor = new CriticalInterruptorThread(perform);
				interruptor.start();
				perform.join();	
			}
			catch (InterruptedException e) {
				log.debug("DecisionThread interrupted");
				interrupt();
			}
		}
	}
	
	/**
	 * Go through the Promises of each Action available in the environment and assess their attractiveness.
	 * @param objectList a List of Objects available to the Agent
	 * @return a map of possible Actions to perform, where K - ObjectAction pairing, and V - an assessment of how beneficial the action is
	 */
	public Map<ObjectAction, Float> queryEnvironmentForPossibilities(List<UseableObject> objectList) {
		Map<ObjectAction,Float> possibilities = new HashMap<ObjectAction,Float>();
		for(UseableObject singleObject : objectList) {
			for (Action singleAction : singleObject.advertiseActions()) {
				possibilities.put(new ObjectAction(singleObject, singleAction), attenuatedScoreActionForAllNeeds(singleAction));
			}
		}
		return possibilities;
	}
	
	/**
	 * Go through the Promises of each Action available in the environment and assess their impact on a single need
	 * @param singleNeedName The name of the need. The suggested format is ALLCAPS e.g. "HUNGER".
	 * @param objectList a List of Objects available to the Agent
	 * @return a map of possible Actions to perform, where K - ObjectAction pairing, and V - an assessment of how beneficial the action is
	 */
	public Map<ObjectAction, Float> queryEnvironmentForPossibilities(String singleNeedName, List<UseableObject> objectList) {
		Map<ObjectAction,Float> possibilities = new HashMap<ObjectAction,Float>();
		for(UseableObject singleObject : objectList) {
			for (Action singleAction : singleObject.advertiseActions()) {
				possibilities.put(new ObjectAction(singleObject, singleAction), attenuatedScoreActionForSingleNeed(singleAction, singleNeedName));
			}
		}
		return possibilities;
	}
	
	/**
	 * Make a decision whether to add an Action to the action queue or perform fallback actions.
	 * @param possibilities a Map of possible Actions to perform, where K - ObjectAction pairing, and V - an assessment of how beneficial the action is
	 */
	public void reactToPossibilities(Map<ObjectAction, Float> possibilities) {

		ObjectAction nextAction = pickNextAction(possibilities);
		if (nextAction != null) {
			thisAgent.getActionQueue().add(nextAction);
		}
		else {
			performFallbackActions();
		}
	}
	
	/**
	 * Wait in the absence of anything else to do.
	 */
	public void performFallbackActions() {
		Thread waitSecond = new WaitThread(1000); //TODO: Speed
		waitSecond.start();
		try {
			waitSecond.join();
		}
		catch (InterruptedException e) {
			log.debug("DecisionThread interrupted");
		}
	}
	
	
	/**
	 * A simple scoring algorithm which sums up the change Actions Promise to provide.
	 * Has significant drawbacks - e.g. needs of a lower level are not more urgent.
	 * @param thisAction the Action to be scored
	 * @return a score of how beneficial the Action promises to be - do not mix with attenuated scores!
	 */
	public float simpleScoreActionForAllNeeds(Action thisAction) {
		float sum = 0;
		for (String needName : thisAgent.getNeeds().getNeeds().keySet()) {
			float current = thisAgent.getNeeds().getNeed(needName);
			float delta = thisAction.getPromises().getChange(needName);
			
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
	
	/**
	 * An Action scoring algorithm which takes into account the urgency of satisfying needs.
	 * Score drops sharply for Actions which Promise to increase a Need over the 100 point limit.
	 * Takes all needs into account by summing up results of attenuatedScoreActionForSingleNeed for each Need.
	 * 
	 * 	y = 100/c - 100/(c+d) {0 < c+d <= 100}
	 * 	y = 100/c - 100/(c+d) - c/1000(d+c-100)^2 {c+d > 100}
	 * 	where c is current value and d is promised delta
	 * 
	 * @param thisAction the Action to be scored
	 * @return a score of how beneficial the Action promises to be
	 * 
	 * TODO: Different equations for different needs - e.g. linear for non-vital needs.
	 * TODO: Implement individual modifiers.
	 * TODO: Possible division by zero?
	 */
	public float attenuatedScoreActionForAllNeeds(Action thisAction) {
		float sum = 0;
		for (String needName : thisAgent.getNeeds().getNeeds().keySet()) {
			sum += attenuatedScoreActionForSingleNeed(thisAction, needName);
		}
		return sum;
	}
	
	/**
	 * An Action scoring algorithm which takes into account the urgency of satisfying a need.
	 * Score drops sharply for Actions which Promise to increase a Need over the 100 point limit.
	 * Takes a single need into account.
	 * 
	 * 	y = 100/c - 100/(c+d) {0 < c+d <= 100}
	 * 	y = 100/c - 100/(c+d) - c/1000(d+c-100)^2 {c+d > 100}
	 * 	where c is current value and d is promised delta
	 * 
	 * @param thisAction The Action to be scored.
	 * @param needName The name of the need. The suggested format is ALLCAPS e.g. "HUNGER".
	 * @return A score of how beneficial the Action promises to be in terms of the need.
	 */
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
	
	/**
	 * Determines the best Action from the Map of possible Actions and their scores.
	 * @param possibilities a Map of possible Actions to perform, where K - ObjectAction pairing, and V - an assessment of how beneficial the action is
	 * @return the best Action to take and its associated Object
	 * 
	 * TODO: Check if this can result in a NullPointer
	 */
	public ObjectAction pickNextAction(Map<ObjectAction,Float> possibilities) {
		if (possibilities.isEmpty()) {
			log.info("No actions are available to " + thisAgent.getName());
			return null;
		}
		
		log.debug("Available decisions: " + possibilities.keySet().toString());
		
		float maxScore = 0;
		ObjectAction bestAction = null;
		for(ObjectAction thisAction : possibilities.keySet()) {
			if (possibilities.get(thisAction) > maxScore) {
				maxScore = possibilities.get(thisAction);
				bestAction = thisAction;
			}
		}
		return bestAction;
	}
	
	/**
	 * Mutator for availableObjects.
	 * @param availableObjects a List of Objects for the Agent to choose from when making decisions
	 */
	public void setAvailableObjects(List<UseableObject> availableObjects) {
		this.availableObjects = availableObjects;
	}

	/**
	 * TODO: This is currently unused. Utilise this to help interrupt threads when needs go critical:
	 * 1. if critical flag is true
	 * 2. execute the block once to cancel current actions
	 * 3. set it to false
	 * @param needName The name of the need. The suggested format is ALLCAPS e.g. "HUNGER".
	 * @param actionThread
	 */
	public void introduceCriticalState(String needName, PerformActionThread actionThread) {

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
