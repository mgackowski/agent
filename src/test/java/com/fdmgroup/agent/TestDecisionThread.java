package com.fdmgroup.agent;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.fdmgroup.agent.actions.Action;
import com.fdmgroup.agent.actions.Promise;
import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.agents.Needs;
import com.fdmgroup.agent.objects.ObjectAction;
import com.fdmgroup.agent.objects.UseableObject;
import com.fdmgroup.agent.threads.DecisionThread;

public class TestDecisionThread {
	
	@Before
	public void setUpDecisionThreadTest() {
		
	}
	
	/**
	 * Test that at TEST_NEED = 80f, action +15f is preferable to +5f
	 */
	@Test
	public void TestDecisionThread_QueryEnvironmentForPossibilities_HigherDeltaMeansHigherScore() {
		
		/* Mock objects */
		Needs testNeedEighty = mock(Needs.class);
		when(testNeedEighty.getNeed("TEST_NEED")).thenReturn(80f);
		Map<String, Float> needsMap = new HashMap<String, Float>();
		needsMap.put("TEST_NEED", 80f);
		when(testNeedEighty.getNeeds()).thenReturn(needsMap);
		
		Agent testAgent = mock(Agent.class);
		when(testAgent.getNeeds()).thenReturn(testNeedEighty);
		
		DecisionThread testDecisionThread = new DecisionThread(testAgent);
		
		Promise addFifteen = mock(Promise.class);
		when(addFifteen.getChange("TEST_NEED")).thenReturn(15f);
		
		Promise addFive = mock(Promise.class);
		when(addFive.getChange("TEST_NEED")).thenReturn(5f);
		
		Action addFifteenAction = mock(Action.class);
		when(addFifteenAction.getPromises()).thenReturn(addFifteen);
		when(addFifteenAction.getName()).thenReturn("add fifteen to test need");
		
		Action addFiveAction = mock(Action.class);
		when(addFiveAction.getPromises()).thenReturn(addFive);
		when(addFiveAction.getName()).thenReturn("add five to test need");
		
		UseableObject testObject = mock(UseableObject.class);
		List<Action> actionList = new ArrayList<Action>();
		actionList.add(addFifteenAction);
		actionList.add(addFiveAction);
		when(testObject.advertiseActions()).thenReturn(actionList);
		when(testObject.getName()).thenReturn("test object");
		
		List<UseableObject> objectList = new ArrayList<UseableObject>();
		objectList.add(testObject);
		
		/* Perform test */
		Map<ObjectAction, Float> resultMap = testDecisionThread.queryEnvironmentForPossibilities(objectList);

		float fiveActionScore = 0;
		float fifteenActionScore = 0;
		for (Entry<ObjectAction, Float> thisEntry : resultMap.entrySet()) {
			if (thisEntry.getKey().getAction().equals(addFiveAction)) {
				fiveActionScore = thisEntry.getValue();
			}
			if (thisEntry.getKey().getAction().equals(addFifteenAction)) {
				fifteenActionScore = thisEntry.getValue();
			}
		}
		assertTrue(fifteenActionScore > fiveActionScore);
		
	}
	
	/**
	 * Test that at TEST_NEED = 80f, action +15f is preferable to +25f
	 */
	@Test
	public void TestDecisionThread_QueryEnvironmentForPossibilities_GoingOverHundredIsDiscouraged() {
		
		/* Mock objects */
		Needs testNeedEighty = mock(Needs.class);
		when(testNeedEighty.getNeed("TEST_NEED")).thenReturn(80f);
		Map<String, Float> needsMap = new HashMap<String, Float>();
		needsMap.put("TEST_NEED", 80f);
		when(testNeedEighty.getNeeds()).thenReturn(needsMap);
		
		Agent testAgent = mock(Agent.class);
		when(testAgent.getNeeds()).thenReturn(testNeedEighty);
		
		DecisionThread testDecisionThread = new DecisionThread(testAgent);
		
		Promise addFifteen = mock(Promise.class);
		when(addFifteen.getChange("TEST_NEED")).thenReturn(15f);
		
		Promise addTwentyFive = mock(Promise.class); // <-- changed here
		when(addTwentyFive.getChange("TEST_NEED")).thenReturn(25f);
		
		Action addFifteenAction = mock(Action.class);
		when(addFifteenAction.getPromises()).thenReturn(addFifteen);
		when(addFifteenAction.getName()).thenReturn("add fifteen to test need");
		
		Action addTwentyFiveAction = mock(Action.class);
		when(addTwentyFiveAction.getPromises()).thenReturn(addTwentyFive);
		when(addTwentyFiveAction.getName()).thenReturn("add twenty five to test need");
		
		UseableObject testObject = mock(UseableObject.class);
		List<Action> actionList = new ArrayList<Action>();
		actionList.add(addFifteenAction);
		actionList.add(addTwentyFiveAction);
		when(testObject.advertiseActions()).thenReturn(actionList);
		when(testObject.getName()).thenReturn("test object");
		
		List<UseableObject> objectList = new ArrayList<UseableObject>();
		objectList.add(testObject);
		
		/* Perform test */
		Map<ObjectAction, Float> resultMap = testDecisionThread.queryEnvironmentForPossibilities(objectList);

		float twentyFiveActionScore = 0;
		float fifteenActionScore = 0;
		for (Entry<ObjectAction, Float> thisEntry : resultMap.entrySet()) {
			if (thisEntry.getKey().getAction().equals(addTwentyFiveAction)) {
				twentyFiveActionScore = thisEntry.getValue();
			}
			if (thisEntry.getKey().getAction().equals(addFifteenAction)) {
				fifteenActionScore = thisEntry.getValue();
			}
		}
		assertTrue(fifteenActionScore > twentyFiveActionScore);
	}
	
	@Ignore
	@Test
	public void TestDecisionThread_QueryEnvironmentForPossibilities_NegativeDeltasAreScoredLowerThanFallback() {
		// At need_1 = 80f, waiting is preferable to N1-80f
	}
	
	@Ignore
	@Test
	public void TestDecisionThread_QueryEnvironmentForPossibilities_LowerNeedsTakePrecedence() {
		// At need_1 = 80f and need_2 = 20f, N2+10f is preferable to N1 + 10f
	}
	
	@Ignore
	@Test
	public void TestDecisionThread_QueryEnvironmentForPossibilities_LowerDeltaWithLowerNeedCanBeScoredHigherThanHigherDeltaWithHigherNeed() {
		// At need_1 = 80f and need_2 = 40f, N1+10f & N2+11f is preferable to N1+12f & N2+10f
	}
	
	@Ignore
	@Test
	public void TestDecisionThread_PerformActionsInQueue_PerformActionsIfAvailable() {
		// Performs actions in queue if they're available
	}
	
	@Ignore
	@Test
	public void TestDecisionThread_Run_ActionQueueUnchangedWhenObjectPoolIsNull() {
		// Behave nicely when objectpool is null
	}
	
	@Ignore
	@Test
	public void TestDecisionThread_Run_IfNoActionsInQueueThenDicisionIsMade() {
		// If there are no more actions in queue, decision making is fired
	}
	
	@Ignore
	@Test
	public void TestDecisionThread_WhenGetObjectsReturnsEmpty() {
		// Decision making doesn't update queue in the absence of objects (getobjects is empty)
	}
	
	@Ignore
	@Test
	public void TestDecisionThread_WaitThreadExecutedWhenNoBeneficialActionsAvailable() {
		// Wait thread is executed when there are no beneficial actions
	}
	
	@Ignore
	@Test
	public void TestDecisionThread_ThreadStopsWhenAgentDies() {
		// Decision thread is stopped when the agent is dead
	}

}
