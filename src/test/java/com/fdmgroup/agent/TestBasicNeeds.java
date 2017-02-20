package com.fdmgroup.agent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.agent.agents.BasicNeeds;

public class TestBasicNeeds {
	
	BasicNeeds testBasicNeeds;
	
	@Before
	public void setUpFiveNeeds() {
		class TestNeeds extends BasicNeeds {
			TestNeeds() {
				needs.put("NEED_1", 50f);
				needs.put("NEED_2", 50f);
			}
		}
		testBasicNeeds = new TestNeeds();
	}
	
	@Test
	public void TestBasicNeeds_GetNeed_ReturnCorrectValue() {
		assertTrue(testBasicNeeds.getNeed("NEED_1") == 50f);
	}
	
	@Test
	public void TestBasicNeeds_GetNeed_ReturnMinusOneIfNoMapping() {
		assertTrue(testBasicNeeds.getNeed("NEED_3") == -1f);
	}
	
	@Test
	public void TestBasicNeeds_SetNeed_ReturnTrueIfSet() {
		assertTrue(testBasicNeeds.setNeed("NEED_1", 20f));
	}
	
	@Test
	public void TestBasicNeeds_SetNeed_NeedValueUpdated() {
		testBasicNeeds.setNeed("NEED_1", 80f);
		assertTrue(testBasicNeeds.getNeed("NEED_1") == 80f);
	}
	
	@Test
	public void TestBasicNeeds_SetNeed_ReturnFalseWhenNoMapping() {
		assertFalse(testBasicNeeds.setNeed("NEED_3", 20f));
	}
	
	@Test
	public void TestBasicNeeds_ChangeNeed_Decreases() {
		testBasicNeeds.setNeed("NEED_1", 90f);
		testBasicNeeds.changeNeed("NEED_1", -80f);
		assertTrue(testBasicNeeds.getNeed("NEED_1") == 10f);
	}
	
	@Test
	public void TestBasicNeeds_ChangeNeed_Increases() {
		testBasicNeeds.setNeed("NEED_1", 15f);
		testBasicNeeds.changeNeed("NEED_1", 25f);
		assertTrue(testBasicNeeds.getNeed("NEED_1") == 40f);
	}
	
	@Test
	public void TestBasicNeeds_ChangeNeed_NegativesChangeToZero() {
		testBasicNeeds.setNeed("NEED_1", 5f);
		testBasicNeeds.changeNeed("NEED_1", -10f);
		assertTrue(testBasicNeeds.getNeed("NEED_1") == 0f);
	}
	
	@Test
	public void TestBasicNeeds_ChangeNeed_NegativeSumReturnsZero() {
		testBasicNeeds.setNeed("NEED_1", 5f);
		assertTrue(testBasicNeeds.changeNeed("NEED_1", -10f) == 0f);
	}
	
	@Test
	public void TestBasicNeeds_GetSumOfAllNeeds_ReturnsSum() {
		testBasicNeeds.setNeed("NEED_1", 5f);
		testBasicNeeds.setNeed("NEED_2", -10f);
		testBasicNeeds.setNeed("NEED_3", 10f);
		assertTrue(testBasicNeeds.getSumOfAllNeeds() == -5f);
	}
	
}
