package com.fdmgroup.agent;

import static org.junit.Assert.*;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.agents.FiveIndividuality;
import com.fdmgroup.agent.agents.FiveNeeds;
import com.fdmgroup.agent.threads.SatietyThread;
import com.fdmgroup.agent.threads.WaitThread;

public class TestSatietyThread {
	
	Agent testAgent;
	Thread pairedThread;
	
	static Logger log = LogManager.getLogger();
	
	@Before
	public void setUpSatietyThread() {
		testAgent = new Agent("Test Agent", new FiveIndividuality(), new FiveNeeds());
		pairedThread = new WaitThread(1000);
	}
	
	@Test
	public void TestSatietyThread_Run_IsDownRateZeroWhileExecution() {
		SatietyThread thread = new SatietyThread(testAgent, "ENERGY", 1000);
		thread.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(testAgent.getIndivValues().getDownRate("ENERGY") == 0);
		thread.interrupt();
	}
	
	@Test
	public void TestSatietyThread_Run_IsOriginalAfterExecution() {
		SatietyThread thread = new SatietyThread(testAgent, "ENERGY", 1000);
		float original = testAgent.getIndivValues().getDownRate("ENERGY");
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(null, original, testAgent.getIndivValues().getDownRate("ENERGY"), 0);
	}
	
	@Test
	public void TestSatietyThread_Run_IsOriginalAfterInterruption() {
		SatietyThread thread = new SatietyThread(testAgent, "ENERGY", 1000);
		float original = testAgent.getIndivValues().getDownRate("ENERGY");
		thread.start();
		thread.interrupt();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(null, original, testAgent.getIndivValues().getDownRate("ENERGY"), 0);
	}
	
	@Test
	public void TestSatietyThread_Run_MillisDurationNotTooShort() {
		SatietyThread thread = new SatietyThread(testAgent, "ENERGY", 1000);
		Date start = new Date();
		thread.run();
		Date finish = new Date();
		long duration = finish.getTime() - start.getTime();
		log.debug("TestSatietyThread_Run_MillisDurationNotTooShort(): duration == " + duration);
		assertTrue(duration >= 1000);
	}
	
	@Test
	public void TestSatietyThread_Run_MillisDurationNotTooLong() {
		SatietyThread thread = new SatietyThread(testAgent, "ENERGY", 1000);
		Date start = new Date();
		thread.run();
		Date finish = new Date();
		assertTrue(finish.getTime() - start.getTime() <= 1100);
	}
	
	@Test
	public void TestSatietyThread_Run_PairedDurationNotTooShort() {
		SatietyThread thread = new SatietyThread(testAgent, "ENERGY", pairedThread);
		Date start = new Date();
		pairedThread.run();
		thread.run();
		Date finish = new Date();
		long duration = finish.getTime() - start.getTime();
		log.debug("TestSatietyThread_Run_PairedDurationNotTooShort(): duration == " + duration);
		assertTrue(duration >= 1000);
	}
	
	@Test
	public void TestSatietyThread_Run_PairedDurationNotTooLong() {
		SatietyThread thread = new SatietyThread(testAgent, "ENERGY", pairedThread);
		Date start = new Date();
		pairedThread.run();
		thread.run();
		Date finish = new Date();
		assertTrue(finish.getTime() - start.getTime() <= 1100);
	}
	
	@Test
	public void TestSatietyThread_Run_PairedAndMillisDurationNotTooShort() {
		SatietyThread thread = new SatietyThread(testAgent, "ENERGY", pairedThread, 500);
		Date start = new Date();
		pairedThread.run();
		thread.run();
		Date finish = new Date();
		long duration = finish.getTime() - start.getTime();
		log.debug("TestSatietyThread_Run_PairedAndMillisDurationNotTooShort(): duration == " + duration);
		assertTrue(duration >= 1500);
	}
	
	@Test
	public void TestSatietyThread_Run_PairedAndMillisDurationNotTooLong() {
		SatietyThread thread = new SatietyThread(testAgent, "ENERGY", pairedThread, 500);
		Date start = new Date();
		pairedThread.run();
		thread.run();
		Date finish = new Date();
		assertTrue(finish.getTime() - start.getTime() <= 1600);
	}

}
