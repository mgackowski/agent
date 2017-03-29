package com.mgackowski.agent;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.mgackowski.agent.actions.Consequence;

public class TestConsequence {
	
	Consequence testConsequence;
	
	@Before
	public void setUpConsequence() {
		testConsequence = new Consequence("TEST_NEED", 50f);
	}
	
	@Test
	public void TestConsequence_GetExtraSatietyLength_DefaultsToZero() {
		assertTrue(testConsequence.getExtraSatietyLength() == 0);
	}
	
}
