package com.fdmgroup.agent;

import com.fdmgroup.agent.outputs.SwingDisplay;
import com.fdmgroup.agent.outputs.SwingRepaintThread;

public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {

      AgentSim demo = new Demo();
      demo.run();
      //demo.startPrintThread();
      
      SwingDisplay.createGui();
      SwingDisplay.startGui();
      
    }
}