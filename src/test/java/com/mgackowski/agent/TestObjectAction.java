package com.mgackowski.agent;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.mgackowski.agent.objects.ObjectAction;

public class TestObjectAction {
	
	ObjectAction testObjectAction;
	
	@Before
	public void setUpObjectAction() {
		testObjectAction = new ObjectAction(null, null);
	}
	
	@Test
	public void TestObjectAction_ToString_WorksWhenFieldsNull() {
		assertNotNull(testObjectAction.toString());
	}
	
}
