package com.mgackowski.agent.swing;

import java.awt.Color;
import javax.swing.JProgressBar;

import com.mgackowski.agent.agents.Agent;

/**
 * An implementation-specific Swing progress bar to represent the need level of an Agent.
 * @author Mikolaj.Gackowski
 */
public class JNeedBar extends JProgressBar {
	
	private static final long serialVersionUID = 6752378669971107428L;
	Agent thisAgent;
	String needName;
	
	JNeedBar(Agent agent, String need) {
		super.setMinimum(0);
		super.setMaximum(100);
		this.thisAgent = agent;
		this.needName = need;
		setStringPainted(true);
		this.setValue(Math.round(thisAgent.getNeeds().getNeed(needName)));
	}
	
	public void update() {
		int newValue = Math.round(thisAgent.getNeeds().getNeed(needName));
		this.setValue(newValue);
		if (newValue <= 10) {
			setForeground(Color.orange);
		}
		else {
			setForeground(Color.blue);
		}
		if (!thisAgent.isAlive()) {
			setForeground(Color.red);
		}
	}
	
}