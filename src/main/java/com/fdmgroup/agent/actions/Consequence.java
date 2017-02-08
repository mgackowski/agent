package com.fdmgroup.agent.actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * (Refactoring)
 * The class can and should be extended for more advanced object/action implementations.
 * @author Mikolaj Gackowski
 *
 */
public class Consequence {
	
	static Logger log = LogManager.getLogger();
	
	private String need;
	private float change;
	private long satietyLength;
	
	public Consequence(String need, float change) {
		this.need = need;
		this.change = change;
		this.satietyLength = 0;
	}
	
	/*
	 * This constructor allows to set a custom satiety length in millis.
	 * TODO: Change this when timer becomes independent from seconds.
	 */
	public Consequence(String need, float change, long satietyLength) {
		this.need = need;
		this.change = change;
		this.satietyLength = satietyLength;
	}

	public String getNeed() {
		return need;
	}

	public float getChange() {
		return change;
	}

	public long getSatietyLength() {
		return satietyLength;
	}

}
