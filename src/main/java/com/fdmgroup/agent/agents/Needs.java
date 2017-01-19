package com.fdmgroup.agent.agents;

import java.util.Map;

public interface Needs {

	float getNeed(String needName);

	boolean setNeed(String needName, float newValue);

	float changeNeed(String needName, float delta);

	float getSumOfAllNeeds();

	Map<String, Float> getNeeds();

}