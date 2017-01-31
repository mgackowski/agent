package com.fdmgroup.agent.outputs;

import javax.swing.JTextField;

import com.fdmgroup.agent.agents.Agent;

public class JActionTextField extends JTextField {
	
	private static final long serialVersionUID = 6752378669971107428L;
	Agent thisAgent;
	
	JActionTextField(Agent agent) {
		this.thisAgent = agent;
		if (thisAgent.getActionStatus() != null) {
			super.setText(thisAgent.getActionStatus());
		}
		else {
			super.setText("...");
		}
		
		super.setEditable(false);
	}
	
	public void update() {
		super.setText(thisAgent.getActionStatus());
	}
	
}