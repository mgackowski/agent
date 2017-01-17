package com.fdmgroup.agent.threads;

import java.util.HashMap;
import java.util.Map;

import com.fdmgroup.agent.Agent;
import com.fdmgroup.agent.actions.Action;
import com.fdmgroup.agent.objects.ObjectPool;
import com.fdmgroup.agent.objects.UseableObject;

public class AgentDecisionThread extends Thread {
	
	Agent thisAgent;
	
	public AgentDecisionThread(Agent thisAgent) {
		this.thisAgent = thisAgent;
	}
	
	public void run() {
		while(thisAgent.isAlive()) {
			while(!thisAgent.getActionQueue().isEmpty()) {
				Action nextAction = thisAgent.getActionQueue().remove();
				try {
					nextAction.execute(thisAgent, nextAction.getTiedObject()).join(); //waits for thread
				} catch (InterruptedException e) {
					System.out.println("DEBUG: Execute interrupted.");
					e.printStackTrace();
				}
			}
			
			Map<Action,Float> possibilities = new HashMap<Action,Float>();
			for(UseableObject singleObject : ObjectPool.getInstance().getObjects()) {
				for (Action singleAction : singleObject.advertiseActions()) {
					possibilities.put(singleAction, attenuatedScoreAction(singleAction));
				}
			}
			Action nextAction = pickNextAction(possibilities);
			
			/* If there's nothing to do, wait it out */
			if (nextAction == null) {
				Thread waitSecond = new WaitThread(1000);
				waitSecond.start();
				thisAgent.setActionStatus("wait (no actions available)");
				try {
					waitSecond.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else { // else, add next best action to the queue
				thisAgent.getActionQueue().add(nextAction);
			}

			//System.out.println("DEBUG: " + thisAgent.getName() + "'s possibilities: " + possibilities.toString());
		}
	}
	
	public float simpleScoreAction(Action thisAction) {
		//for each of the agent's needs:
		float sum = 0;
		for (String needName : thisAgent.getNeeds().getNeeds().keySet()) {
			float current = thisAgent.getNeeds().getNeed(needName); // add current need level
			float delta = thisAction.getPromises().getChange(needName); // add advertised need change
			
			if ((current + delta) <= 100) {
				sum += current;
				sum += delta; // action is only attractive for agent it's not overkill
			}
			else {
				//sum += 90; //OPTION 1: -10 penalty for gluttony
				sum += current; //OPTION 2: ignore any benefits if the promise is to go over 100
			}
		}
		//System.out.println("DEBUG: sum: " + sum);
		return sum;
	}
	
	public float attenuatedScoreAction(Action thisAction) {
		//TODO: Agents will still choose actions which make them go over 100%
		/*TODO: Jake helped with function, but needs work before it can be implemented:
		 * 
		 * 	y = 5000 * (1/c - 1/(c+d) - 1/((c^2)-(d^2)))
		 * 	where c is current value and d is delta
		 * 
		 */
		float sum = 0;
		for (String needName : thisAgent.getNeeds().getNeeds().keySet()) {
			float current = thisAgent.getNeeds().getNeed(needName);
			float delta = thisAction.getPromises().getChange(needName);
			
			/* Resets oversatisfying future values to 100, not ideal - shorter actions should score better */
			if ((current + delta) >= 100) {
				delta = 100 - current;
			}
			
			sum += (10 / current) - 10 / (current + delta);

		}
		return sum;
	}
	
	public Action pickNextAction(Map<Action,Float> possibilities) {
		/* If no action scores greater than zero, this will return null */
		
		debugScores(possibilities);
		
		if (possibilities.isEmpty()) {
			return null;
		}
		
		float maxScore = 0;
		Action bestAction = null;	//maybe replace with Idle for safety?
		for(Action thisAction : possibilities.keySet()) {
			if (possibilities.get(thisAction) > maxScore) {
				maxScore = possibilities.get(thisAction);
				bestAction = thisAction;
			}
		}
		return bestAction;
	}
	
	private void debugScores(Map<Action,Float> possibilities) {
		System.out.println("\n═══ WELCOME TO " + thisAgent.getName() + "'s BRAIN ═══");
		for(Action thisAction : possibilities.keySet()) {
			System.out.println("[" + thisAction.getName() + "]" + " SCORE: " + possibilities.get(thisAction));}
		System.out.println("═════════════════════════════════\n");
	}
}
