package com.fdmgroup.agent;

public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {

      AgentSim demo = new Demo();
      demo.run();
      demo.startPrintThread();
        
    }
}
