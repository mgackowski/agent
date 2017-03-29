package com.mgackowski.agent;

import static org.junit.Assert.*;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mgackowski.agent.actions.BasicAction;
import com.mgackowski.agent.actions.Consequence;
import com.mgackowski.agent.agents.Agent;
import com.mgackowski.agent.agents.FiveIndividuality;
import com.mgackowski.agent.agents.FiveNeeds;
import com.mgackowski.agent.objects.BasicObject;
import com.mgackowski.agent.objects.ObjectAction;
import com.mgackowski.agent.objects.UseableObject;
import com.mgackowski.agent.threads.PerformActionThread;
import com.mgackowski.agent.threads.WaitThread;

public class TestPerformActionThread {
	
	static Logger log = LogManager.getLogger();
	
	Agent testAgent;
	PerformActionThread actionThread;
	PerformActionThread timedActionThread;
	BasicObject testObject;
	BasicAction testAction;
	ObjectAction testObjectAction;
	
	@Before
	public void setUpPerformActionThread() {
		
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
		timedActionThread = new PerformActionThread(testAgent, 20);
	}
	
	@Test
	public void TestPerformActionThread_InterruptThreads_ThreadsAreInterrupted() {
		PerformActionThread thread = new PerformActionThread(testAgent);
		
		WaitThread firstWaitThread = new WaitThread(2000);
		WaitThread secondWaitThread = new WaitThread(2000);
		WaitThread thirdWaitThread = new WaitThread(2000);
		
		firstWaitThread.start();
		secondWaitThread.start();
		thirdWaitThread.start();
		
		thread.getThreads().add(firstWaitThread);
		thread.getThreads().add(secondWaitThread);
		thread.getThreads().add(thirdWaitThread);
		
		thread.interruptThreads();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean success = true;
		for (Thread thisThread : thread.getThreads()) {
			if (thisThread.isAlive()) {
				success = false;
			}
		}
		assertTrue(success);
	}
	
	@Test
	public void TestPerformActionThread_Run_ObjectsSetAsUsed() {
		
		testAgent.getActionQueue().peek().getObject().setBeingUsed(false);
		
		actionThread.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertTrue(testAgent.getActionQueue().peek().getObject().isBeingUsed());
		actionThread.interrupt();
	}
	
	@Test
	public void TestPerformActionThread_Run_ObjectNotUsedAfterThreadFinishes() {
		
		UseableObject thisObject = testAgent.getActionQueue().peek().getObject();
		thisObject.setBeingUsed(false);
		actionThread.run();
		assertFalse(thisObject.isBeingUsed());
	}
	
	@Test
	public void TestPerformActionThread_Run_ObjectNotUsedAfterThreadInterrupted() {
		
		UseableObject thisObject = testAgent.getActionQueue().peek().getObject();
		thisObject.setBeingUsed(false);
		actionThread.start();
		try {
			Thread.sleep(250);
			actionThread.interrupt();
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(thisObject.isBeingUsed());
	}
	
	@Test
	public void TestPerformActionThread_Run_CurrentActionSetWhenPerformed() {
		
		testAgent.setCurrentAction(null);
		
		timedActionThread.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(testAgent.getCurrentAction() == testObjectAction);
		actionThread.interrupt();
	}
	
	@Test
	public void TestPerformActionThread_Run_CurrentActionNullAfterThreadFinishes() {
		
		testAgent.setCurrentAction(null);
		actionThread.run();
		assertNull(testAgent.getCurrentAction());
	}
	
	@Test
	public void TestPerformActionThread_Run_CurrentActionNullAfterThreadInterrupted() {
		
		testAgent.setCurrentAction(null);
		actionThread.start();
		try {
			Thread.sleep(500);
			actionThread.interrupt();
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertNull(testAgent.getCurrentAction());
	}
	
	@Test
	public void TestPerformActionThread_Run_ThreadLastsLongerThanMinimum() {
		
		Date start = new Date();
		timedActionThread.start();
		try {
			timedActionThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Date finish = new Date();
		long duration = finish.getTime() - start.getTime();
		log.debug("TestPerformActionThread_Run_ThreadLastsLongerThanMinimum: duration == " + duration);
		assertTrue(duration >= 2000);
		
	}
	
	@Test
	public void TestPerformActionThread_Run_ThreadLastsNoLongerThanMinimumAndChangingNeeds() {
		
		Date start = new Date();
		timedActionThread.start();
		try {
			timedActionThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Date finish = new Date();
		long duration = finish.getTime() - start.getTime();
		log.debug("TestPerformActionThread_Run_ThreadLastsLongerThanMinimum: duration == " + duration);
		assertTrue(duration < 2100);
		
	}
	
	@Test
	public void TestPerformActionThread_Run_NoActionIfActionQueueEmpty() {
		
		testAgent.getActionQueue().clear();
		Date start = new Date();
		actionThread.start();
		try {
			actionThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Date finish = new Date();
		long duration = finish.getTime() - start.getTime();
		assertTrue(duration < 100);
	}
	
	@Test
	public void TestPerformActionThread_Run_FirstActionRemovedFromQueue() {
		
		testAgent.setCurrentAction(null);
		testAgent.getActionQueue().clear();
		for (int i = 0; i < 3; i++) {
			testAgent.getActionQueue().add(testObjectAction);
		}
		actionThread.start();
		try {
			actionThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(testAgent.getActionQueue().size() == 2);
	}
	
	@Test
	public void TestPerformActionThread_Run_PlacesNextActionIfExists() {
		
		class NextAction extends BasicAction {
			NextAction() {
				name = "test next action";
			}
		}
		testAgent.setCurrentAction(null);
		testAgent.getActionQueue().clear();
		testAction.setNextAction(new NextAction());
		testAgent.getActionQueue().add(testObjectAction);
		
		actionThread.start();
		try {
			actionThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(testAgent.getActionQueue().peek().getAction().getName().equals("test next action"));
	}
	
	@Test
	public void TestPerformActionThread_Run_DoesntSetNextActionIfNull() {
		
		testAgent.setCurrentAction(null);
		testAgent.getActionQueue().clear();
		testAgent.getActionQueue().add(testObjectAction);
		
		actionThread.start();
		try {
			actionThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertNull(testAgent.getActionQueue().peek());
	}
	
	@After
	public void runAfterEachTest() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.debug("----------");
	}
	
}
