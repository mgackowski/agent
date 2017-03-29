package com.mgackowski.agent;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mgackowski.agent.actions.Promise;

public class TestPromise {
	
	Promise testPromise;
	
	@Before
	public void setUpPromise() {
		testPromise = new Promise();
		testPromise.getChanges().put("TEST_NEED_1", 50f);
		testPromise.getChanges().put("TEST_NEED_2", -30f);
		testPromise.getChanges().put("TEST_NEED_3", null);
	}
	
	@Test
	public void TestPromise_GetChange_ReturnsPositiveValue() {
		assertTrue(testPromise.getChange("TEST_NEED_1") == 50f);
	}
	
	@Test
	public void TestPromise_GetChange_ReturnsNegativeValue() {
		assertTrue(testPromise.getChange("TEST_NEED_2") == -30f);
	}
	
	@Test
	public void TestPromise_GetChange_ReturnsZeroIfNull() {
		assertTrue(testPromise.getChange("TEST_NEED_3") == 0f);
	}
	
	@Test
	public void TestPromise_GetChange_ReturnsZeroIfNoMapping() {
		assertTrue(testPromise.getChange("TEST_NEED_4") == 0f);
	}
	
	@Test
	public void TestPromise_SetChange_ReturnsTrueIfMappingExists() {
		assertTrue(testPromise.setChange("TEST_NEED_3", 10f));
	}
	
	@Test
	public void TestPromise_SetChange_ReturnsFalseIfNoMappingExists() {
		assertFalse(testPromise.setChange("TEST_NEED_4", 10f));
	}
	
	@Test
	public void TestPromise_SetChange_ValueUpdatesCorrectly() {
		testPromise.setChange("TEST_NEED_3", 30f);
		assertTrue(testPromise.getChange("TEST_NEED_3") == 30f);
	}
	
	@Test
	public void TestPromise_SetChange_NewEntryIsNotCreated() {
		testPromise.setChange("TEST_NEED_5", 5f);
		assertTrue(testPromise.getChange("TEST_NEED_5") == 0f);
	}
	
}
