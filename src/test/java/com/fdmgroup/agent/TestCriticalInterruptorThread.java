package com.fdmgroup.agent;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.threads.CriticalInterruptorThread;
import com.fdmgroup.agent.threads.PerformActionThread;

public class TestCriticalInterruptorThread {
	
	
	CriticalInterruptorThread interruptorThread;
	PerformActionThread actionThread;
	Agent testAgent;
	
	@Before
	public void setUpCriticalInterruptorThreadTest() {
		testAgent = new Agent("Test Agent");
		actionThread = new PerformActionThread(testAgent);
		interruptorThread = new CriticalInterruptorThread(actionThread);
	}
	
	@Test
	public void TestCriticalInterruptorThread_Run_InterruptorFinishesWhenPairedThreadFinishes() {
		
		actionThread.start();
		interruptorThread.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(interruptorThread.isAlive());
	}
	
	@Test
	public void TestCriticalInterruptorThread_Run_InterruptsWhenNeedBelowTenAndDeteriorating() {
		//TODO
	}
	
	@Test
	public void TestCriticalInterruptorThread_Run_DoesntInterruptWhenNeedAboveTenAndDeteriorating() {
		//TODO
	}
	
	@Test
	public void TestCriticalInterruptorThread_Run_DoesntInterruptWhenNeedBelowTenAndNotDeteriorating() {
		//TODO
	}

}
