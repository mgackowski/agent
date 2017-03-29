package com.mgackowski.agent;

import com.mgackowski.agent.swing.SwingDisplay;

public class App 
{
    /**
     * Needs-based Agent simulation. See http://github.com/mgackowski/agent
     */
    public static void main( String[] args )
    {

      AgentSim demo = new Demo();
      demo.prepareAndStartSim();
      //demo.startPrintThread();
      
      SwingDisplay display = new SwingDisplay(demo);
      display.createGui();
      display.startGui();
      
    }
}