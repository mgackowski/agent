package com.fdmgroup.agent;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.agents.FiveIndividuality;
import com.fdmgroup.agent.agents.FiveNeeds;
import com.fdmgroup.agent.threads.DeteriorationThread;

public class TestDeteriorationThread {
	
	static Logger log = LogManager.getLogger();
	
	DeteriorationThread testDeteriorationThread;
	Agent testAgent;
	
	@Before
	public void setUpDeteriorationThreadTest() {
		testAgent = new Agent("Test Agent", new FiveIndividuality(1f, 1f, 1f, 1f, 1f), new FiveNeeds(10f, 10f, 10f, 10f, 10f));
		testDeteriorationThread = new DeteriorationThread(testAgent);
	}
	
	@Test(timeout=2000)
	public void TestDeteriorationThread_Run_NeedsDontDeteriorateWhenDead() {
		testAgent.setAlive(false);
		testDeteriorationThread.start();
		assertTrue(testAgent.getNeeds().getSumOfAllNeeds() == 50f);
	}
	
	@Test(timeout=2000)
	public void TestDeteriorationThread_Run_NeedsDeteriorateEveryTick() {
		testDeteriorationThread.start();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(testAgent.getNeeds().getSumOfAllNeeds() == 40f);
		testDeteriorationThread.interrupt();
	}
	
	@Test
	public void TestDeteriorationThread_Run_AgentDiesWhenSingleNeedBelowZero() {
		testAgent.getNeeds().setNeed("FOOD", 0.5f);
		testDeteriorationThread.start();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(testAgent.isAlive());
		testDeteriorationThread.interrupt();
	}
	
	@Test(timeout=2500)
	public void TestDeteriorationThread_Run_ThreadStopsWhenAgentDies() {
		testAgent.getNeeds().setNeed("FOOD", 1f);
		testDeteriorationThread.start();
		try {
			testDeteriorationThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(testDeteriorationThread.isAlive());
	}

}
