package com.fdmgroup.agent.outputs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerExample {
	
	static Logger log = LogManager.getLogger(LoggerExample.class.getName());
	
	public static void testLogs() {
		log.debug("Hi, this is a Debug message");
	}
}
