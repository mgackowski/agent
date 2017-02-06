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

	public void run() {
		log.debug("Swing repaint thread started.");
		while(true) {
			//TODO: Updateable components can be abstracted and stored in single list
			for (JNeedBar thisBar : SwingDisplay.getNeedBars()) {
				thisBar.update();
			}
			for (JActionTextArea thisArea : SwingDisplay.getActionStatuses()) {
				thisArea.update();
			}
			SwingDisplay.getFrame().repaint();
			SwingDisplay.getFrame().revalidate();
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				log.error("Swing repaint thread interrupted.");
				e.printStackTrace();
			}
		}
	}
}
