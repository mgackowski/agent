package com.mgackowski.agent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.mgackowski.agent.agents.Agent;
import com.mgackowski.agent.agents.FiveIndividuality;
import com.mgackowski.agent.agents.FiveNeeds;
import com.mgackowski.agent.threads.ChangeNeedThread;

public class TestChangeNeedThread {
	
	Agent testAgent;
	
	static Logger log = LogManager.getLogger();
	
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
		long duration = finish.getTime() - start.getTime();
		log.debug("TestChangeNeedThread_Run_NeedIncrementsOverTime(): duration == " + duration);
		assertTrue(duration >= 1000);
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
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Date finish = new Date();
		long duration = finish.getTime() - start.getTime();
		log.debug("TestChangeNeedThread_Run_StillRunsWhenZero(): duration == " + duration);
		assertTrue(duration >= 3000);
	}

}
