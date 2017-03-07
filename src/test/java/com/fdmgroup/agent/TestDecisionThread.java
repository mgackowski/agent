package com.fdmgroup.agent;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.fdmgroup.agent.actions.BasicAction;
import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.objects.BasicObject;
import com.fdmgroup.agent.objects.UseableObject;
import com.fdmgroup.agent.threads.DecisionThread;

public class TestDecisionThread {
	
	DecisionThread testDecisionThread;
	Agent testAgent;
	BasicObject testObject;
	BasicAction testAction;
	List<UseableObject> testList;
	
	
	
	@Before
	public void setUpDecisionThreadTest() {
		
		testAgent = mock(Agent.class);
		testObject = mock(BasicObject.class);
		testAction = mock(BasicAction.class);
		testList = new ArrayList<UseableObject>();
		//testObject
		testList.add(testObject);
		
		testDecisionThread = new DecisionThread(testAgent, testList);
		
	}
	
	@Ignore
	@Test
	public void TestDecisionThread_QueryEnvironmentForPossibilities_HigherDeltaMeansHigherScore() {
		List actionList = new ArrayList();
		actionList.add(null);
		when(testObject.advertiseActions()).thenReturn(null);
		// At need_1 = 80f, action N1+15f is preferable to N1+5f
	}
	
	@Ignore
	@Test
	public void TestDecisionThread_QueryEnvironmentForPossibilities_GoingOverHundredIsDiscouraged() {
		// At need_1 = 80f, action N1+15f is preferable to N1+25f
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
