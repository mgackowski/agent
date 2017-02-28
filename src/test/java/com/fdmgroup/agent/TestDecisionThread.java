package com.fdmgroup.agent;

import org.junit.Ignore;
import org.junit.Test;

public class TestDecisionThread {
	
	@Ignore
	@Test
	public void TestDecisionThread_QueryEnvironmentForPossibilities_HigherDeltaMeansHigherScore() {
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
