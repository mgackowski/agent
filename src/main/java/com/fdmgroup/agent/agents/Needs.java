package com.fdmgroup.agent.agents;

import java.util.Map;

/**
 * Needs describe the "urges", or considerations Agents take into account  when optimal Actions are chosen.
 * Agents will seek to maintain optimal levels for those needs based on their decision making.
 * Suggested values are 0f for a complete lack of satisfaction (failure), and 100f for full satisfaction.
 * @author Mikolaj.Gackowski
 *
 */
public interface Needs {

	/**
	 * @param needName The name of the need. The suggested format is ALLCAPS e.g. "HUNGER".
	 * @return The level of need satisfaction - by default, 0f for empty, 100f for full.
	 */
	float getNeed(String needName);

	/**
	 * @param needName The name of the need. The suggested format is ALLCAPS e.g. "HUNGER".
	 * @param newValue The level of need satisfaction - by default, 0f for empty, 100f for full.
	 * @return the need level; default is 0f for empty and 100f for full, -1 if need has no mapping
	 */
	boolean setNeed(String needName, float newValue);

	/**
	 * @param needName The name of the need. The suggested format is ALLCAPS e.g. "HUNGER".
	 * @param newValue The change in the level of need satisfaction - e.g. 10f, or -25f.
	 * @return the need level after the change
	 */
	float changeNeed(String needName, float delta);

	/**
	 * @return the sum of all need levels for scoring purposes
	 */
	float getSumOfAllNeeds();

	/**
	 * @return a Map with keys for need names and values for need levels
	 */
	Map<String, Float> getNeeds();

}