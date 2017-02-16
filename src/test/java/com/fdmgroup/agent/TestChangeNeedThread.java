package com.fdmgroup.agent;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.agent.agents.Agent;

public class TestChangeNeedThread {
	
	Agent testAgent;
	
	@Before
	public void setUpChangeNeedThread() {
		testAgent = new Agent("Test Agent");	
	}
	
	@Test
	public void TestChangeNeedThread_Run() {
		//TODO
		assertTrue(false);
	}
	
}
