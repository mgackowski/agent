package com.fdmgroup.agent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;

import com.fdmgroup.agent.agents.BasicIndividuality;

public class TestBasicIndividuality {
	
	BasicIndividuality testIndividuality;
	
	@Before
	public void setUpBasicIndividuality() {
		class TestIndividuality extends BasicIndividuality {
			TestIndividuality() {
				downRate.put("NEED_1", 1f);
				downRate.put("NEED_2", 0.5f);
				downRate.put("NEED_3", null);
				downRate.put("NEED_4", -1f);
			}
		}
		testIndividuality = new TestIndividuality();
	}
	
	public void TestBasicIndividuality_GetDownRate_ReturnsValueIfExists() {
		assertTrue(testIndividuality.getDownRate("NEED_1") == 1f);
	}
	
	public void TestBasicIndividuality_GetDownRate_ReturnsZeroIfNoMapping() {
		assertTrue(testIndividuality.getDownRate("NEED_5") == 0f);
	}
	
	public void TestBasicIndividuality_GetDownRate_ReturnsZeroIfNull() {
		assertTrue(testIndividuality.getDownRate("NEED_3") == 0f);
	}
	
	public void TestBasicIndividuality_SetDownRate_SetsNewValue() {
		testIndividuality.setDownRate("NEED_2", 1.6f);
		assertTrue(testIndividuality.getDownRate().get("NEED_2") == 1.6f);
	}
	
	public void TestBasicIndividuality_SetDownRate_ReturnsTrueAfterSettingValue() {
		assertTrue(testIndividuality.setDownRate("NEED_2", 1.7f));
	}
	
	public void TestBasicIndividuality_SetDownRate_ReturnsFalseIfNoMapping() {
		assertFalse(testIndividuality.setDownRate("NEED_5", 1.7f));
	}
	
	public void TestBasicIndividuality_SetDownRate_DoesntCreateNewMapping() {
		testIndividuality.setDownRate("NEED_5", 1.6f);
		assertFalse(testIndividuality.getDownRate().containsKey("NEED_5"));
	}
}
