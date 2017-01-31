package com.fdmgroup.agent.outputs;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.fdmgroup.agent.agents.Agent;
import com.fdmgroup.agent.agents.AgentPool;

public class SwingExample {
	
	private static JFrame jf = new JFrame("Testing Swing");
	
	public static void createGui() {
		jf.setSize(500,300);
		jf.setLayout(new FlowLayout());  
		
		
		for (Agent thisAgent : AgentPool.getInstance().getAgents()) {
			JPanel p = new JPanel();
			p.setLayout (new BoxLayout (p, BoxLayout.Y_AXIS));
			p.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.black), new EmptyBorder(10, 10, 10, 10)));
			//BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.black))
			jf.add(p);
			p.add(new JLabel(thisAgent.getName()));
			for (String needName : thisAgent.getNeeds().getNeeds().keySet()) {
				p.add(new JLabel(needName));
				p.add(new JProgressBar(0,100));
			}
		}
		
		jf.setVisible(true);
	}
	

}
