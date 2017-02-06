package com.fdmgroup.agent.swing;

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

/**
 * A simple Swing-powered GUI displaying the needs of all Agents.
 * Much more user-friendly than logs.
 * @author Mikolaj.Gackowski
 *
 */
public class SwingDisplay {
	
	private static JFrame frame = new JFrame("Agent status");
	
	//TODO: Updateable components can be abstracted and stored in single list
	private static List<JNeedBar> needBars = new ArrayList<JNeedBar>();
	private static List<JActionTextArea> actionStatuses = new ArrayList<JActionTextArea>();
	
	public static void createGui() {
		frame.setSize(500,350);
		frame.setLayout(new FlowLayout());
		
		/* Draw a panel for every Agent */
		for (Agent thisAgent : AgentPool.getInstance().getAgents()) {
			
			JPanel thisPanel = new JPanel();
			thisPanel.setLayout (new BoxLayout (thisPanel, BoxLayout.Y_AXIS));
			thisPanel.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.black), new EmptyBorder(10, 10, 10, 10)));
			
			JLabel nameLabel = new JLabel(thisAgent.getName());

			thisPanel.add(nameLabel);
			
			JActionTextArea status = new JActionTextArea(thisAgent);
			thisPanel.add(status);
			actionStatuses.add(status);
			
			generateNeedBars(thisAgent, thisPanel);
			
			frame.add(thisPanel);
		}
		
		frame.setVisible(true);
	}
	
	public static void startGui() {
		new SwingRepaintThread().start();
	}
	
	private static void generateNeedBars(Agent thisAgent, JPanel panel) {
		
		/* Draw a label and bar for every need */
		for (String needName : thisAgent.getNeeds().getNeeds().keySet()) {
			
			panel.add(new JLabel(needName));
			
			JNeedBar needBar = new JNeedBar(thisAgent,needName);
			panel.add(needBar);
			needBars.add(needBar);
		}
	}

	public static JFrame getFrame() {
		return frame;
	}

	public static List<JNeedBar> getNeedBars() {
		return needBars;
	}

	public static List<JActionTextArea> getActionStatuses() {
		return actionStatuses;
	}
	
}
