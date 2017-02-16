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
	public void TestFiveNeeds_GetNeed_ReturnMinusOneIfNoMapping() {
		assertTrue(testBasicNeeds.getNeed("NEED_3") == -1f);
	}
	
	@Test
	public void TestFiveNeeds_SetNeed_ReturnTrueIfSet() {
		assertTrue(testBasicNeeds.setNeed("NEED_1", 20f));
	}
	
	@Test
	public void TestFiveNeeds_SetNeed_ReturnFalseWhenNoMapping() {
		assertFalse(testBasicNeeds.setNeed("NEED_3", 20f));
	}
	
	@Test
	public void TestFiveNeeds_ChangeNeed() {
		//TODO:
		assertTrue(false);
	}
	
	@Test
	public void TestFiveNeeds_GetSumOfAllNeeds() {
		//TODO:
		assertTrue(false);
	}
	
}
