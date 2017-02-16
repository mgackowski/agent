package com.fdmgroup.agent;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.agent.actions.BasicAction;
import com.fdmgroup.agent.actions.Consequence;

public class TestBasicAction {
	
	BasicAction testAction;
	
	@Before
	public void setUpBasicAction() {
		class TestAction extends BasicAction {
			TestAction() {
				name = "test action";
				consequences.put("TEST_NEED", new Consequence("TEST_NEED", 50f));
			}
		}
		testAction = new TestAction();
		testAction.setNextAction(new TestAction());
	}
	
	@Test
	public void TestBasicAction_GetName_RetrievesOverriddenName() {
		assertTrue(testAction.getName().equals("test action"));
	}
	
	@Test
	public void TestBasicAction_GetConsequence_NullWhenNoMapping() {
		assertNull(testAction.getConsequence("DIFFERENT_NEED"));
	}
	
	@Test
	public void TestBasicAction_GetNextAction_NullWhenSameAction() {
		assertNull(testAction.getNextAction());
	}
	
	@Test
	public void TestBasicAction_GetNextAction_FalseWhenSameAction() {
		assertFalse(testAction.setNextAction(testAction));
	}
	
}
