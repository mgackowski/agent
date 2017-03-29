package com.fdmgroup.agent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.agent.actions.BasicAction;
import com.fdmgroup.agent.actions.Consequence;
import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.agents.FiveIndividuality;
import com.fdmgroup.agent.agents.FiveNeeds;
import com.fdmgroup.agent.objects.BasicObject;
import com.fdmgroup.agent.objects.ObjectAction;
import com.fdmgroup.agent.threads.CriticalInterruptorThread;
import com.fdmgroup.agent.threads.PerformActionThread;

public class TestCriticalInterruptorThread {
	
	static Logger log = LogManager.getLogger();
	
	CriticalInterruptorThread interruptorThread;
	PerformActionThread actionThread;
	Agent testAgent;
	BasicAction testAction;
	BasicObject testObject;
	ObjectAction testObjectAction;
	
	@Before
	public void setUpCriticalInterruptorThreadTest() {
		class TestAction extends BasicAction {
			TestAction() {
				name = "test action";
				consequences.put("TEST_NEED", new Consequence("TEST_NEED", 3f));
				nextAction = null;
			}
		}
		
		testAction = new TestAction();
		
		class TestObject extends BasicObject {
			public TestObject() {
				name = "test object";
				allActions.add(testAction);
			}	
		}
		
		testObject = new TestObject();
		testObjectAction = new ObjectAction(testObject, testAction);

		testAgent = new Agent("Test Agent", new FiveIndividuality(), new FiveNeeds());
		testAgent.getActionQueue().add(testObjectAction);
		actionThread = new PerformActionThread(testAgent);
		interruptorThread = new CriticalInterruptorThread(actionThread);
	}
	
	@Test
	public void TestCriticalInterruptorThread_Run_InterruptorFinishesWhenPairedThreadFinishes() {
		
		actionThread.start();
		interruptorThread.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(interruptorThread.isAlive());
	}
	
	@Test
	public void TestCriticalInterruptorThread_Run_InterruptsWhenNeedBelowTenAndDeteriorating() {
		actionThread = new PerformActionThread(testAgent, 40);
		interruptorThread = new CriticalInterruptorThread(actionThread);
		actionThread.start();
		interruptorThread.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		testAgent.getNeeds().setNeed("FOOD", 9f);
		testAgent.getIndivValues().setDownRate("FOOD", 2f);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		boolean anyThreadAlive = false;
		for(Thread thisThread : actionThread.getThreads()) {
			if (thisThread.isAlive()) {
				anyThreadAlive = true;
			}
		}
		assertFalse(anyThreadAlive);
	}
	
	@Test
	public void TestCriticalInterruptorThread_Run_DoesntInterruptWhenNeedAboveTenAndDeteriorating() {
		actionThread = new PerformActionThread(testAgent, 40);
		interruptorThread = new CriticalInterruptorThread(actionThread);
		actionThread.start();
		interruptorThread.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		testAgent.getNeeds().setNeed("FOOD", 11f);
		testAgent.getIndivValues().setDownRate("FOOD", 2f);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		boolean allThreadsDead = true;
		for(Thread thisThread : actionThread.getThreads()) {
			log.debug(thisThread.getName() + " : " + thisThread.isAlive());
			if (!thisThread.isAlive()) {
				allThreadsDead = false;
			}
		}
		assertTrue(allThreadsDead);
	}
	
	@Test
	public void TestCriticalInterruptorThread_Run_DoesntInterruptWhenNeedBelowTenAndNotDeteriorating() {
		actionThread = new PerformActionThread(testAgent, 40);
		interruptorThread = new CriticalInterruptorThread(actionThread);
		actionThread.start();
		interruptorThread.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		testAgent.getNeeds().setNeed("FOOD", 9f);
		testAgent.getIndivValues().setDownRate("FOOD", 0f);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		boolean allThreadsDead = true;
		for(Thread thisThread : actionThread.getThreads()) {
			log.debug(thisThread.getName() + " : " + thisThread.isAlive());
			if (!thisThread.isAlive()) {
				allThreadsDead = false;
			}
		}
		assertTrue(allThreadsDead);
	}

}
