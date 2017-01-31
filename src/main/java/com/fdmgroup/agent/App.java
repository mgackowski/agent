package com.fdmgroup.agent;

import com.fdmgroup.agent.outputs.SwingExample;

public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {

      AgentSim demo = new Demo();
      demo.run();
      //demo.startPrintThread();
      
      SwingExample.createGui();
        
    }
}