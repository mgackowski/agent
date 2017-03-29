package com.fdmgroup.agent;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.fdmgroup.agent.threads.WaitThread;

public class TestWaitThread {
	
	static Logger log = LogManager.getLogger();
	
	@Test
	public void TestWaitThread_Run_WaitsMinimumDuration() {
		WaitThread thread = new WaitThread(1000);
		Date start = new Date();
		thread.run();
		Date finish = new Date();
		long duration = finish.getTime() - start.getTime();
		log.debug("TestWaitThread_Run_WaitsMinimumDuration(): duration == " + duration);
		assertTrue(duration >= 1000);
	}
	
	@Test
	public void TestWaitThread_Run_WaitsNotTooLong() {
		WaitThread thread = new WaitThread(1000);
		Date start = new Date();
		thread.run();
		Date finish = new Date();
		assertTrue(finish.getTime() - start.getTime() <= 1100);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void TestWaitThread_Run_ThrowsExceptionIfNegative() {
		WaitThread thread = new WaitThread(-1000);
		thread.run();
	}

}
