package com.fdmgroup.agent;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.agent.actions.BasicAction;
import com.fdmgroup.agent.actions.Consequence;
import com.fdmgroup.agent.objects.BasicObject;

public class TestBasicObject {
	
	BasicObject testObject;
	
	@Before
	public void setUpBasicObject() {
		class TestAction extends BasicAction {
			TestAction() {
				name = "test action";
				consequences.put("TEST_NEED", new Consequence("TEST_NEED", 50f));
			}
		}
		class TestObject extends BasicObject {
			public TestObject() {
				name = "test object";
				allActions.add(new TestAction());
			}	
		}
		testObject = new TestObject();
	}
	
	@Test
	public void TestBasicObject_AdvertiseActions_NonEmptyListIfObjNotUsed() {
		testObject.setBeingUsed(false);
		assertTrue(testObject.advertiseActions().size() > 0);
	}
	
	@Test
	public void TestBasicObject_AdvertiseActions_EmptyListIfObjUsed() {
		testObject.setBeingUsed(true);
		assertTrue(testObject.advertiseActions().size() == 0);
	}
	
}
