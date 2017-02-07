package com.fdmgroup.agent.swing;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.fdmgroup.agent.agents.Agent;

public class JActionTextArea extends JTextArea {
	
	private static final long serialVersionUID = 6752378669971107428L;
	Agent thisAgent;
	
	JActionTextArea(Agent agent) {
		this.thisAgent = agent;
		if (thisAgent.getCurrentAction() != null) {
			super.setText(thisAgent.getCurrentAction().getAction().getName() + " using " + thisAgent.getCurrentAction().getObject().getName());
		}
		else {
			super.setText("...");
		}
		super.setColumns(5);
		super.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.black), new EmptyBorder(10, 10, 10, 10)));
		super.setLineWrap(true);
		super.setWrapStyleWord(true);
		super.setEditable(false);
		super.setBackground(getDisabledTextColor());
	}
	
	public void update() {
		if (thisAgent.getCurrentAction() != null) {
			super.setText(thisAgent.getCurrentAction().getAction().getName() + " using " + thisAgent.getCurrentAction().getObject().getName());
		}
		else {
			super.setText("...");
		}
	}
	
}