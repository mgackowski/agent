package com.fdmgroup.agent.outputs;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.agents.AgentPool;

public class SwingDisplay {
	
	private static JFrame frame = new JFrame("Agent status");
	private static List<JNeedBar> needBars = new ArrayList<JNeedBar>();
	private static List<JActionTextField> actionStatuses = new ArrayList<JActionTextField>();
	
	public static void createGui() {
		frame.setSize(500,300);
		frame.setLayout(new FlowLayout());
		
		for (Agent thisAgent : AgentPool.getInstance().getAgents()) {
			
			JPanel thisPanel = new JPanel();
			thisPanel.setLayout (new BoxLayout (thisPanel, BoxLayout.Y_AXIS));
			thisPanel.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.black), new EmptyBorder(10, 10, 10, 10)));
			frame.add(thisPanel);
			
			thisPanel.add(new JLabel(thisAgent.getName()));
			
			JActionTextField status = new JActionTextField(thisAgent);
			thisPanel.add(status);
			actionStatuses.add(status);
			
			for (String needName : thisAgent.getNeeds().getNeeds().keySet()) {
				
				thisPanel.add(new JLabel(needName));
				
				JNeedBar needBar = new JNeedBar(thisAgent,needName);
				thisPanel.add(needBar);
				needBars.add(needBar);
			}
		}
		
		frame.setVisible(true);
	}

	public static JFrame getJf() {
		return frame;
	}

	public static List<JNeedBar> getNeedBars() {
		return needBars;
	}

	public static List<JActionTextField> getActionStatuses() {
		return actionStatuses;
	}

	public static void setActionStatuses(List<JActionTextField> actionStatuses) {
		SwingDisplay.actionStatuses = actionStatuses;
	}
	
}
