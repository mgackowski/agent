package com.fdmgroup.agent.outputs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SwingRepaintThread extends Thread {
	
	static Logger log = LogManager.getLogger();

	public void run() {
		log.debug("Swing repaint thread started.");
		while(true) {
			
			for (JNeedBar thisBar : SwingDisplay.getNeedBars()) {
				thisBar.update();
			}
			for (JActionTextField thisField : SwingDisplay.getActionStatuses()) {
				thisField.update();
			}
			SwingDisplay.getJf().repaint();
			SwingDisplay.getJf().revalidate();
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				log.error("Swing repaint thread interrupted.");
				e.printStackTrace();
			}
		}
	}
	
}
