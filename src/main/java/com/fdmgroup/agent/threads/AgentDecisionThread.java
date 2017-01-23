package com.fdmgroup.agent.threads;

import java.util.HashMap;
import java.util.Map;

import com.fdmgroup.agent.actions.Action;
import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.objects.ObjectPool;
import com.fdmgroup.agent.objects.UseableObject;

public class AgentDecisionThread extends Thread {
	
	Agent thisAgent;
	
	public AgentDecisionThread(Agent thisAgent) {
		this.thisAgent = thisAgent;
	}
	
	public void run() {
		
		//TODO: Agents should break out of actions if there is another critical need to satisfy
		//TODO: Alive condition must be checked more often
		
		while(thisAgent.isAlive()) {	//TODO: Nested while loops
			
			//While Agent has actions in the queue, perform them
			while(!thisAgent.getActionQueue().isEmpty()) {
				Action nextAction = thisAgent.getActionQueue().remove();
				try {
					nextAction.execute(thisAgent, nextAction.getTiedObject()).join(); //waits for thread
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			//When Agent has run out of actions, find one to perform next
			Map<Action,Float> possibilities = new HashMap<Action,Float>();
			for(UseableObject singleObject : ObjectPool.getInstance().getObjects()) {
				for (Action singleAction : singleObject.advertiseActions()) {
					possibilities.put(singleAction, attenuatedScoreAction(singleAction));
				}
			}
			Action nextAction = pickNextAction(possibilities);
			
			//If there is a next action, add it to the queue
			if (nextAction != null) {
				thisAgent.getActionQueue().add(nextAction);
			}
			else {
				Thread waitSecond = new WaitThread(1000);
				waitSecond.start();
				thisAgent.setActionStatus("wait (no compelling actions available)");
				try {
					waitSecond.join();
				}
				catch (InterruptedException e) {
					//TODO: Log that an action has been interrupted.
					e.printStackTrace();
				}
			}
		}
	}
	
	public float simpleScoreAction(Action thisAction) {
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
	
	public float attenuatedScoreAction(Action thisAction) {
		
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
			float current = thisAgent.getNeeds().getNeed(needName);
			float delta = thisAction.getPromises().getChange(needName);
			
			if (0 < (current + delta) && (current+delta) <= 100) {
				sum += 100/current - 100/(current+delta);
			}
			if ((current+delta) > 100) {
				sum += 100/current - 100/(current+delta) - current/1000*(current+delta-100)*(current+delta-100);
			}
		}
		return sum;
	}
	
	public Action pickNextAction(Map<Action,Float> possibilities) {
		if (possibilities.isEmpty()) {
			return null;
		}
		float maxScore = 0;	// threshold below which being idle is considered better
		Action bestAction = null;
		for(Action thisAction : possibilities.keySet()) {
			if (possibilities.get(thisAction) > maxScore) {
				maxScore = possibilities.get(thisAction);
				bestAction = thisAction;
			}
		}
		return bestAction;
	}
	
	//TODO: This should be handled by a logger
	private void debugScores(Map<Action,Float> possibilities) {
		System.out.println("\n═══ WELCOME TO " + thisAgent.getName() + "'s BRAIN ═══");
		for(Action thisAction : possibilities.keySet()) {
			System.out.println("[" + thisAction.getName() + "]" + " SCORE: " + possibilities.get(thisAction));}
		System.out.println("═════════════════════════════════\n");
	}
}
