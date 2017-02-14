package com.fdmgroup.agent.actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Consequence objects describe how a need will change when an action is performed.
 * The class can and should be extended for more advanced object/action implementations.
 * @author Mikolaj.Gackowski
 *
 */
public class Consequence {
	
	static Logger log = LogManager.getLogger();
	
	private String need;
	private float change;
	private long extraSatietyLength;
	
	public Consequence(String need, float change) {
		this.need = need;
		this.change = change;
		this.extraSatietyLength = 0;
	}

	/**
	 * This constructor allows to set a custom extra satiety length in milliseconds.
	 * Satiety is a state during which a need level does not decrease. By default, it lasts while
	 * a need is being satisfied. Extra satiety length can be specified in addition to that time.
	 * TODO: Change this when timer becomes independent from seconds.
	 */
	public Consequence(String need, float change, long extraSatietyLength) {
		this.need = need;
		this.change = change;
		this.extraSatietyLength = extraSatietyLength;
	}

	/**
	 * @return the name of the need this consequence will affect
	 */
	public String getNeed() {
		return need;
	}

	/**
	 * @return the amount of points that will be added/subtracted from the current need level
	 */
	public float getChange() {
		return change;
	}

	/**
	 * Satiety is a state during which a need level does not decrease. By default, it lasts while
	 * a need is being satisfied. Extra satiety length can be specified in addition to that time.
	 * @return extra satiety length in milliseconds.
	 */
	public long getExtraSatietyLength() {
		return extraSatietyLength;
	}

}
