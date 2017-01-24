package com.fdmgroup.agent.outputs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerExample {
	
	static Logger log = LogManager.getLogger();
	
	public static void testLogs() {
		log.debug("Hi, this is a Debug message I can understand");
	}
}
