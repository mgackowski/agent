package com.fdmgroup.agent;

import com.fdmgroup.agent.swing.SwingDisplay;

public class App 
{
    public static void main( String[] args )
    {

      AgentSim demo = new Demo();
      demo.run();
      //demo.startPrintThread();
      
      SwingDisplay.createGui();
      SwingDisplay.startGui();
      
    }
}