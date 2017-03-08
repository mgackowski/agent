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
	
	/**
	 * Test that at TEST_NEED = 80f, waiting (score 0) is preferable to -80f
	 * TODO: Turns out that values <= -80f are scored higher than {-80f : 0f}, which shouldn't happen
	 */
	@Test
	public void TestDecisionThread_QueryEnvironmentForPossibilities_NegativeDeltasAreScoredLowerThanFallback() {
		
		/* Mock objects */
		Needs testNeedEighty = mock(Needs.class);
		when(testNeedEighty.getNeed("TEST_NEED")).thenReturn(80f);
		Map<String, Float> needsMap = new HashMap<String, Float>();
		needsMap.put("TEST_NEED", 80f);
		when(testNeedEighty.getNeeds()).thenReturn(needsMap);
		
		Agent testAgent = mock(Agent.class);
		when(testAgent.getNeeds()).thenReturn(testNeedEighty);
		
		DecisionThread testDecisionThread = new DecisionThread(testAgent);
		
		Promise removeEighty = mock(Promise.class); // <-- changes here
		when(removeEighty.getChange("TEST_NEED")).thenReturn(-80f);
		
		
		Action removeEightyAction = mock(Action.class);
		when(removeEightyAction.getPromises()).thenReturn(removeEighty);
		when(removeEightyAction.getName()).thenReturn("remove eighty test need");
		
		UseableObject testObject = mock(UseableObject.class);
		List<Action> actionList = new ArrayList<Action>();
		actionList.add(removeEightyAction);
		when(testObject.advertiseActions()).thenReturn(actionList);
		when(testObject.getName()).thenReturn("test object");
		
		List<UseableObject> objectList = new ArrayList<UseableObject>();
		objectList.add(testObject);
		
		/* Perform test */
		Map<ObjectAction, Float> resultMap = testDecisionThread.queryEnvironmentForPossibilities(objectList);

		float removeEightyScore = 100;
		for (Entry<ObjectAction, Float> thisEntry : resultMap.entrySet()) {
			if (thisEntry.getKey().getAction().equals(removeEightyAction)) {
				removeEightyScore = thisEntry.getValue();
			}
		}
		assertTrue(0 > removeEightyScore);
		
	}
	
	/**
	 * Test that at TEST_NEED_1 = 80f and TEST_NEED_2 = 20f,
	 * need 2 + 20f is preferable to need 1 + 10f
	 */
	public void TestDecisionThread_QueryEnvironmentForPossibilities_LowerNeedsTakePrecedence() {
		
		/* Mock objects */
		Needs testNeedEightyTwenty = mock(Needs.class);
		when(testNeedEightyTwenty.getNeed("TEST_NEED_1")).thenReturn(80f);
		when(testNeedEightyTwenty.getNeed("TEST_NEED_2")).thenReturn(20f);	//<-- changes here
		Map<String, Float> needsMap = new HashMap<String, Float>();
		needsMap.put("TEST_NEED_1", 80f);
		needsMap.put("TEST_NEED_2", 20f);
		when(testNeedEightyTwenty.getNeeds()).thenReturn(needsMap);
		
		Agent testAgent = mock(Agent.class);
		when(testAgent.getNeeds()).thenReturn(testNeedEightyTwenty);
		
		DecisionThread testDecisionThread = new DecisionThread(testAgent);
		
		Promise addTenToNeedTwo = mock(Promise.class);
		when(addTenToNeedTwo.getChange("TEST_NEED_2")).thenReturn(10f);
		
		Promise addTenToNeedOne = mock(Promise.class);
		when(addTenToNeedOne.getChange("TEST_NEED_1")).thenReturn(10f);
		
		Action addTenToNeedTwoAction = mock(Action.class);
		when(addTenToNeedTwoAction.getPromises()).thenReturn(addTenToNeedTwo);
		when(addTenToNeedTwoAction.getName()).thenReturn("add ten to need two");
		
		Action addTenToNeedOneAction = mock(Action.class);
		when(addTenToNeedOneAction.getPromises()).thenReturn(addTenToNeedOne);
		when(addTenToNeedOneAction.getName()).thenReturn("add ten to need one");
		
		UseableObject testObject = mock(UseableObject.class);
		List<Action> actionList = new ArrayList<Action>();
		actionList.add(addTenToNeedTwoAction);
		actionList.add(addTenToNeedOneAction);
		when(testObject.advertiseActions()).thenReturn(actionList);
		when(testObject.getName()).thenReturn("test object");
		
		List<UseableObject> objectList = new ArrayList<UseableObject>();
		objectList.add(testObject);
		
		/* Perform test */
		Map<ObjectAction, Float> resultMap = testDecisionThread.queryEnvironmentForPossibilities(objectList);

		float tenToNeedOneActionScore = 0;
		float tenToNeedTwoActionScore = 0;
		for (Entry<ObjectAction, Float> thisEntry : resultMap.entrySet()) {
			if (thisEntry.getKey().getAction().equals(addTenToNeedOneAction)) {
				tenToNeedOneActionScore = thisEntry.getValue();
			}
			if (thisEntry.getKey().getAction().equals(addTenToNeedTwoAction)) {
				tenToNeedTwoActionScore = thisEntry.getValue();
			}
		}
		assertTrue(tenToNeedTwoActionScore > tenToNeedOneActionScore);
	}
	
	/**
	 * Test that at TEST_NEED_1 = 80f and TEST_NEED_2 = 40f,
	 * need 1 + 10f & need 2 + 11f is preferable to need 1 + 12f & need 2 +10f
	 */
	@Test
	public void TestDecisionThread_QueryEnvironmentForPossibilities_LowerDeltaWithLowerNeedCanBeScoredHigherThanHigherDeltaWithHigherNeed() {
		
		/* Mock objects */
		Needs testNeedEightyForty = mock(Needs.class);
		when(testNeedEightyForty.getNeed("TEST_NEED_1")).thenReturn(80f);
		when(testNeedEightyForty.getNeed("TEST_NEED_2")).thenReturn(40f);	//<-- changes here
		Map<String, Float> needsMap = new HashMap<String, Float>();
		needsMap.put("TEST_NEED_1", 80f);
		needsMap.put("TEST_NEED_2", 40f);
		when(testNeedEightyForty.getNeeds()).thenReturn(needsMap);
		
		Agent testAgent = mock(Agent.class);
		when(testAgent.getNeeds()).thenReturn(testNeedEightyForty);
		
		DecisionThread testDecisionThread = new DecisionThread(testAgent);
		
		Promise addTenToNeedOneAndElevenToNeedTwo = mock(Promise.class);
		when(addTenToNeedOneAndElevenToNeedTwo.getChange("TEST_NEED_1")).thenReturn(10f);
		when(addTenToNeedOneAndElevenToNeedTwo.getChange("TEST_NEED_2")).thenReturn(11f);
		
		Promise addTwelveToNeedOneAndTenToNeedTwo = mock(Promise.class);
		when(addTwelveToNeedOneAndTenToNeedTwo.getChange("TEST_NEED_1")).thenReturn(12f);
		when(addTwelveToNeedOneAndTenToNeedTwo.getChange("TEST_NEED_2")).thenReturn(10f);
		
		Action addTenToNeedOneAndElevenToNeedTwoAction = mock(Action.class);
		when(addTenToNeedOneAndElevenToNeedTwoAction.getPromises()).thenReturn(addTenToNeedOneAndElevenToNeedTwo);
		when(addTenToNeedOneAndElevenToNeedTwoAction.getName()).thenReturn("add ten to need one and eleven to need two");
		
		Action addTwelveToNeedOneAndTenToNeedTwoAction = mock(Action.class);
		when(addTwelveToNeedOneAndTenToNeedTwoAction.getPromises()).thenReturn(addTwelveToNeedOneAndTenToNeedTwo);
		when(addTwelveToNeedOneAndTenToNeedTwoAction.getName()).thenReturn("add twelve to need one and ten to need two");
		
		UseableObject testObject = mock(UseableObject.class);
		List<Action> actionList = new ArrayList<Action>();
		actionList.add(addTenToNeedOneAndElevenToNeedTwoAction);
		actionList.add(addTwelveToNeedOneAndTenToNeedTwoAction);
		when(testObject.advertiseActions()).thenReturn(actionList);
		when(testObject.getName()).thenReturn("test object");
		
		List<UseableObject> objectList = new ArrayList<UseableObject>();
		objectList.add(testObject);
		
		/* Perform test */
		Map<ObjectAction, Float> resultMap = testDecisionThread.queryEnvironmentForPossibilities(objectList);

		float tenToNeedOneAndElevenToNeedTwoScore = 0;
		float twelveToNeedOneAndTenToNeedTwoActionScore = 0;
		for (Entry<ObjectAction, Float> thisEntry : resultMap.entrySet()) {
			if (thisEntry.getKey().getAction().equals(addTwelveToNeedOneAndTenToNeedTwoAction)) {
				twelveToNeedOneAndTenToNeedTwoActionScore = thisEntry.getValue();
			}
			if (thisEntry.getKey().getAction().equals(addTenToNeedOneAndElevenToNeedTwoAction)) {
				tenToNeedOneAndElevenToNeedTwoScore = thisEntry.getValue();
			}
		}
		assertTrue(tenToNeedOneAndElevenToNeedTwoScore > twelveToNeedOneAndTenToNeedTwoActionScore);
		
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
