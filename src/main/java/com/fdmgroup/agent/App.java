package com.fdmgroup.agent;

import com.fdmgroup.agent.objects.ObjFridge;
import com.fdmgroup.agent.objects.ObjIdle;
import com.fdmgroup.agent.objects.ObjSingleBed;
import com.fdmgroup.agent.objects.ObjectPool;
import com.fdmgroup.agent.threads.GlobalDisplayInfobarThread;

public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
    	/*Add two agents*/
        AgentPool.getInstance().addAgent(new Agent("Bob", new BasicIndividuality(1.3f, 1.22f)));
        AgentPool.getInstance().addAgent(new Agent("Alice", new BasicIndividuality(1.1f, 1.16f)));
        /*Add a fridge*/
        ObjectPool.getInstance().addObject(new ObjFridge());
        ObjectPool.getInstance().addObject(new ObjSingleBed());
        
        /* Fallback wait thread has taken over the function of this */
        //ObjectPool.getInstance().addObject(new ObjIdle());
        
        for(Agent thisAgent : AgentPool.getInstance().getAgents()) {
        	thisAgent.startLife();
        	Thread.sleep(100);
        }
        
        Thread gdit = new GlobalDisplayInfobarThread();
        gdit.start();
        
    }
}
