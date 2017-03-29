package com.mgackowski.agent;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mgackowski.agent.agents.Agent;
import com.mgackowski.agent.objects.ObjectAction;

public class TestAgent {
	
	Agent testAgent;
	
	@Before
	public void setUpTestAgent() {
		testAgent = new Agent("Test Agent");
	}
	
	@Test
	public void TestAgent_Kill_ChangesStateToFalse() {
		testAgent.kill();
		assertFalse(testAgent.isAlive());
	}
	
	@Test
	public void TestAgent_SetCurrentAction_ActionSetWhenNotNull() {
		ObjectAction testObjectAction = new ObjectAction(null, null);
		testAgent.setCurrentAction(testObjectAction);
		assertTrue(testAgent.getCurrentAction() == testObjectAction);
	}
	
	@Test
	public void TestAgent_SetCurrentAction_ActionSetToNullWhenNull() {
		testAgent.setCurrentAction(null);
		assertNull(testAgent.getCurrentAction());
	}
	
}
