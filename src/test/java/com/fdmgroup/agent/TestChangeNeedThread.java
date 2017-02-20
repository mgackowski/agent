package com.fdmgroup.agent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.agents.FiveIndividuality;
import com.fdmgroup.agent.agents.FiveNeeds;
import com.fdmgroup.agent.threads.ChangeNeedThread;

public class TestChangeNeedThread {
	
	Agent testAgent;
	
	@Before
	public void setUpChangeNeedThread() {
		testAgent = new Agent("Test Agent", new FiveIndividuality(), new FiveNeeds());
	}
	
	@Test
	public void TestChangeNeedThread_Run_NeedIncrements() {
		ChangeNeedThread thread = new ChangeNeedThread(testAgent, "FOOD", 20f);
		testAgent.getNeeds().setNeed("FOOD", 10f);
		thread.run();
		assertEquals("food not met", 30f, testAgent.getNeeds().getNeed("FOOD"), 0);
	}
	
	@Test
	public void TestChangeNeedThread_Run_NeedDecrements() {
		ChangeNeedThread thread = new ChangeNeedThread(testAgent, "ENERGY", -10f);
		testAgent.getNeeds().setNeed("ENERGY", 30f);
		thread.run();
		assertTrue(testAgent.getNeeds().getNeed("ENERGY") == 20f);
	}
	
	@Test
	public void TestChangeNeedThread_Run_ResetsToHundred() {
		ChangeNeedThread thread = new ChangeNeedThread(testAgent, "ENERGY", 15f);
		testAgent.getNeeds().setNeed("ENERGY", 95f);
		thread.run();
		assertTrue(testAgent.getNeeds().getNeed("ENERGY") == 100f);
	}
	
	@Test
	public void TestChangeNeedThread_Run_WontChangeIfAgentDead() {
		ChangeNeedThread thread = new ChangeNeedThread(testAgent, "ENERGY", 10f);
		testAgent.getNeeds().setNeed("ENERGY", 10f);
		testAgent.setAlive(false);
		thread.run();
		assertTrue(testAgent.getNeeds().getNeed("ENERGY") == 10f);
	}
	
	@Test
	public void TestChangeNeedThread_Run_WontDropBelowZero() {
		ChangeNeedThread thread = new ChangeNeedThread(testAgent, "ENERGY", -10f);
		testAgent.getNeeds().setNeed("ENERGY", 5f);
		thread.run();
		assertTrue(testAgent.getNeeds().getNeed("ENERGY") == 0f);
	}
	
	@Test
	public void TestChangeNeedThread_Run_NeedIncrementsOverTime() {
		ChangeNeedThread thread = new ChangeNeedThread(testAgent, "FOOD", 5f);
		testAgent.getNeeds().setNeed("FOOD", 0f);
		Date start = new Date();
		thread.run();
		Date finish = new Date();
		assertTrue(finish.getTime() - start.getTime() >= 1000);
	}
	
	@Test
	public void TestChangeNeedThread_Run_NeedIncrementsNotTooLong() {
		ChangeNeedThread thread = new ChangeNeedThread(testAgent, "FOOD", 5f);
		testAgent.getNeeds().setNeed("FOOD", 0f);
		Date start = new Date();
		thread.run();
		Date finish = new Date();
		assertTrue(finish.getTime() - start.getTime() <= 1100);
	}
	
	@Test
	public void TestChangeNeedThread_Run_NeedDecrementsOverTime() {
		ChangeNeedThread thread = new ChangeNeedThread(testAgent, "FOOD", -5f);
		testAgent.getNeeds().setNeed("FOOD", 25f);
		Date start = new Date();
		thread.run();
		Date finish = new Date();
		assertTrue(finish.getTime() - start.getTime() >= 1000);
	}
	
	@Test
	public void TestChangeNeedThread_Run_NeedDecrementsNotTooLong() {
		ChangeNeedThread thread = new ChangeNeedThread(testAgent, "FOOD", -5f);
		testAgent.getNeeds().setNeed("FOOD", 25f);
		Date start = new Date();
		thread.run();
		Date finish = new Date();
		assertTrue(finish.getTime() - start.getTime() <= 1100);
	}
	
	@Test
	public void TestChangeNeedThread_Run_StillRunsWhenZero() {
		ChangeNeedThread thread = new ChangeNeedThread(testAgent, "FOOD", -15f);
		testAgent.getNeeds().setNeed("FOOD", 5f);
		Date start = new Date();
		thread.run();
		Date finish = new Date();
		assertTrue(finish.getTime() - start.getTime() >= 3000);
	}

}
