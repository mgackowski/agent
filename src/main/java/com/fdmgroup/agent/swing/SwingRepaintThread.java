package com.fdmgroup.agent.swing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Update the Swing panel every 100 milliseconds.
 * @author Mikolaj.Gackowski
 *
 */
public class SwingRepaintThread extends Thread {
	
	static Logger log = LogManager.getLogger();
	SwingDisplay display;
	
	SwingRepaintThread(SwingDisplay display) {
		this.display = display;
	}

	public void run() {
		log.debug("Swing repaint thread started.");
		while(true) {
			for (JNeedBar thisBar : display.getNeedBars()) {
				thisBar.update();
			}
			for (JActionTextArea thisArea : display.getActionStatuses()) {
				thisArea.update();
			}
			display.getFrame().repaint();
			display.getFrame().revalidate();
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				log.error("Swing repaint thread interrupted.");
				e.printStackTrace();
			}
		}
	}
}
