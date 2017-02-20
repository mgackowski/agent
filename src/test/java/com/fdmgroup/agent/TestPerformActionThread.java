package com.fdmgroup.agent;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.agents.FiveIndividuality;
import com.fdmgroup.agent.agents.FiveNeeds;
import com.fdmgroup.agent.threads.PerformActionThread;
import com.fdmgroup.agent.threads.WaitThread;

public class TestPerformActionThread {
	
	Agent testAgent;
	
	@Before
	public void setUpSatietyThread() {
		testAgent = new Agent("Test Agent", new FiveIndividuality(), new FiveNeeds());
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
	
	@Ignore
	@Test
	public void TestPerformActionThread_Run_ObjectsSetAsUsed() {
		//TODO
		assertTrue(false);
	}
	
	//make sure objects are set as used
	//make sure objects are freed after thread finishes
	//make sure objects are freed after thread is interrupted
	
	//make sure the thread lasts longer than the required minimum length
	
	//more tests...
	

}
