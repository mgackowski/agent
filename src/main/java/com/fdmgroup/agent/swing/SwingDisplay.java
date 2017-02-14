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

import com.fdmgroup.agent.AgentSim;
import com.fdmgroup.agent.agents.Agent;

/**
 * A simple Swing-powered GUI displaying the needs of all Agents.
 * Much more user-friendly than logs.
 * @author Mikolaj.Gackowski
 *
 */
public class SwingDisplay {
	
	private AgentSim simulation;
	private JFrame frame = new JFrame("Agent status");
	
	public SwingDisplay(AgentSim simulationToPresent) {
		this.simulation = simulationToPresent;
	}
	
	//TODO: Abstract components which can be updated, then call update on each
	private static List<JNeedBar> needBars = new ArrayList<JNeedBar>();
	private static List<JActionTextArea> actionStatuses = new ArrayList<JActionTextArea>();
	
	/**
	 * Create the GUI. This will not update by itself - call startGui() afterwards.
	 */
	public void createGui() {
		frame.setSize(500,350);
		frame.setLayout(new FlowLayout());
		
		for (Agent thisAgent : simulation.getAgentPool().getAgents()) {
			
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
	
	/**
	 * Start a thread which will keep updating the GUI.
	 */
	public void startGui() {
		new SwingRepaintThread(this).start();
	}
	
	private static void generateNeedBars(Agent thisAgent, JPanel panel) {
		
		for (String needName : thisAgent.getNeeds().getNeeds().keySet()) {
			
			panel.add(new JLabel(needName));
			
			JNeedBar needBar = new JNeedBar(thisAgent,needName);
			panel.add(needBar);
			needBars.add(needBar);
		}
	}

	public JFrame getFrame() {
		return frame;
	}

	public List<JNeedBar> getNeedBars() {
		return needBars;
	}

	public List<JActionTextArea> getActionStatuses() {
		return actionStatuses;
	}
	
}
