package com.fdmgroup.agent;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.fdmgroup.agent.actions.Action;
import com.fdmgroup.agent.actions.Consequence;
import com.fdmgroup.agent.actions.Promise;
import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.agents.BasicIndividuality;
import com.fdmgroup.agent.agents.Needs;
import com.fdmgroup.agent.objects.ObjectAction;
import com.fdmgroup.agent.objects.UseableObject;
import com.fdmgroup.agent.threads.DecisionThread;

public class TestDecisionThread {
	
	static Logger log = LogManager.getLogger();
	
	Agent testAgent = mock(Agent.class);
	DecisionThread testDecisionThread;
	List<Action> actionList;
	List<UseableObject> objectList;
	UseableObject testObject;
	
	@Before
	public void setUpDecisionThreadTest() {
		
		Needs testNeedEightyTwenty = mock(Needs.class);
		when(testNeedEightyTwenty.getNeed("TEST_NEED_1")).thenReturn(80f);
		when(testNeedEightyTwenty.getNeed("TEST_NEED_2")).thenReturn(20f);
		
		Map<String, Float> needsMap = new HashMap<String, Float>();
		needsMap.put("TEST_NEED_1", 80f);
		needsMap.put("TEST_NEED_2", 20f);
		when(testNeedEightyTwenty.getNeeds()).thenReturn(needsMap);
		
		when(testAgent.getNeeds()).thenReturn(testNeedEightyTwenty);
		
		testDecisionThread = new DecisionThread(testAgent);
		
		actionList = new ArrayList<Action>();
		objectList = new ArrayList<UseableObject>();
		testObject = mock(UseableObject.class);
		when(testObject.advertiseActions()).thenReturn(actionList);
		when(testObject.getName()).thenReturn("test object");
		objectList.add(testObject);
		
	}
	
	/**
	 * Test that at TEST_NEED = 80f, action +15f is preferable to +5f
	 */
	@Test
	public void TestDecisionThread_QueryEnvironmentForPossibilities_HigherDeltaMeansHigherScore() {
			
		Promise addFifteen = mock(Promise.class);
		when(addFifteen.getChange("TEST_NEED_1")).thenReturn(15f);
		
		Promise addFive = mock(Promise.class);
		when(addFive.getChange("TEST_NEED_1")).thenReturn(5f);
		
		Action addFifteenAction = mock(Action.class);
		when(addFifteenAction.getPromises()).thenReturn(addFifteen);
		when(addFifteenAction.getName()).thenReturn("add fifteen to test need");
		
		Action addFiveAction = mock(Action.class);
		when(addFiveAction.getPromises()).thenReturn(addFive);
		when(addFiveAction.getName()).thenReturn("add five to test need");
		
		actionList.add(addFifteenAction);
		actionList.add(addFiveAction);
		
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
		
		Promise addFifteen = mock(Promise.class);
		when(addFifteen.getChange("TEST_NEED_1")).thenReturn(15f);
		
		Promise addTwentyFive = mock(Promise.class); // <-- changed here
		when(addTwentyFive.getChange("TEST_NEED_1")).thenReturn(25f);
		
		Action addFifteenAction = mock(Action.class);
		when(addFifteenAction.getPromises()).thenReturn(addFifteen);
		when(addFifteenAction.getName()).thenReturn("add fifteen to test need");
		
		Action addTwentyFiveAction = mock(Action.class);
		when(addTwentyFiveAction.getPromises()).thenReturn(addTwentyFive);
		when(addTwentyFiveAction.getName()).thenReturn("add twenty five to test need");
		
		actionList.add(addFifteenAction);
		actionList.add(addTwentyFiveAction);
		
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
		
		Promise removeEighty = mock(Promise.class);
		when(removeEighty.getChange("TEST_NEED_1")).thenReturn(-80f);
		
		Action removeEightyAction = mock(Action.class);
		when(removeEightyAction.getPromises()).thenReturn(removeEighty);
		when(removeEightyAction.getName()).thenReturn("remove eighty test need");
		
		actionList.add(removeEightyAction);
		
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
		
		actionList.add(addTenToNeedTwoAction);
		actionList.add(addTenToNeedOneAction);
		
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
		when(testNeedEightyForty.getNeed("TEST_NEED_2")).thenReturn(40f);
		Map<String, Float> needsMap = new HashMap<String, Float>();
		needsMap.put("TEST_NEED_1", 80f);
		needsMap.put("TEST_NEED_2", 40f);
		when(testNeedEightyForty.getNeeds()).thenReturn(needsMap);
		when(testAgent.getNeeds()).thenReturn(testNeedEightyForty);
		
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
		
		actionList.add(addTenToNeedOneAndElevenToNeedTwoAction);
		actionList.add(addTwelveToNeedOneAndTenToNeedTwoAction);
		
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
	
	@Test(timeout = 2000)
	public void TestDecisionThread_PerformActionsInQueue_PerformActionsIfAvailable() {
		
		ObjectAction testObjectAction = mock(ObjectAction.class);
		Queue<ObjectAction> testActionQueue = new LinkedList<ObjectAction>();
		testActionQueue.add(testObjectAction);
		
		BasicIndividuality testIndividuality = mock(BasicIndividuality.class);
		when(testIndividuality.getDownRate("TEST_NEED_1")).thenReturn(1f);
		
		Consequence needChange = mock(Consequence.class);
		when(needChange.getChange()).thenReturn(2f);
		
		Map<String, Consequence> consequences = new HashMap<String, Consequence>();
		consequences.put("TEST_NEED_1", needChange);
		
		Action testAction = mock(Action.class);
		when(testAction.getConsequences()).thenReturn(consequences);
		when(testAction.getConsequence("TEST_NEED_1")).thenReturn(needChange);
		
		when(testObjectAction.getObject()).thenReturn(testObject);
		when(testObjectAction.getAction()).thenReturn(testAction);
		
		when(testAgent.getActionQueue()).thenReturn(testActionQueue);
		when(testAgent.isAlive()).thenReturn(true);
		when(testAgent.getName()).thenReturn("Test Agent");
		
		when(testAgent.getIndivValues()).thenReturn(testIndividuality);
		
		testDecisionThread.performActionsInQueue();
		
		assertTrue(testActionQueue.isEmpty());
		
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
