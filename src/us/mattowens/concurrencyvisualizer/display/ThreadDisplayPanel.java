package us.mattowens.concurrencyvisualizer.display;

import java.awt.FlowLayout;

import javax.swing.*;

public class ThreadDisplayPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ThreadDisplayPanel(long threadId, String threadName) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(new JLabel("Id: " + threadId + " Name: " + threadName));

	}
	
	public void addEventPanel(EventPanel eventPanel) {
		add(new JSeparator(SwingConstants.HORIZONTAL));
		add(eventPanel);
	}

}
