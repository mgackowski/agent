package com.fdmgroup.agent;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import com.fdmgroup.agent.threads.WaitThread;

public class TestWaitThread {
	
	@Test
	public void TestWaitThread_Run_WaitsMinimumDuration() {
		WaitThread thread = new WaitThread(1000);
		Date start = new Date();
		thread.run();
		Date finish = new Date();
		assertTrue(finish.getTime() - start.getTime() >= 1000);
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
