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
		while(true) {
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
					possibilities.put(singleAction, simpleScoreAction(singleAction));
				}
			}
			thisAgent.getActionQueue().add(pickNextAction(possibilities));
		}
	}
	
	public float simpleScoreAction(Action thisAction) {
		//for each of the agent's needs:
		float sum = 0;
		for (String needName : thisAgent.getNeeds().getNeeds().keySet()) {
			float current = thisAgent.getNeeds().getNeed(needName); // add current need level
			float delta = thisAction.getPromises().getChange(needName); // add advertised need change
			sum += current;
			if ((current + delta) <= 100) {
				sum += delta; // action is only attractive for agent it's not overkill
			}
			else {
				sum -= 10; //disincentivise gluttony
			}
		}
		//System.out.println("DEBUG: sum: " + sum);
		return sum;
	}
	
	public Action pickNextAction(Map<Action,Float> possibilities) {
		
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
}
