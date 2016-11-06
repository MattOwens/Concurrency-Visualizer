package us.mattowens.concurrencyvisualizer.display;

import java.awt.Component;
import java.awt.Point;

import javax.swing.*;

public class ThreadDisplayPanel extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int locationY = 0;
	
	public ThreadDisplayPanel(long threadId, String threadName) {
		super("Id: " + threadId + " Name: " + threadName, true, true, false, false);
		JDesktopPane contentPane = new JDesktopPane();
		setContentPane(contentPane);
		//setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setLayout(null);
	}
	
	public void addEventPanel(EventPanel eventPanel, Point location) {
		//add(new JSeparator(SwingConstants.HORIZONTAL));
		eventPanel.setVisible(true);
		eventPanel.setLocation(location);
		eventPanel.setSize(eventPanel.getPreferredSize());
		add(eventPanel);
		System.out.println("Added event panel");
	}
	
	public void addLabel(JLabel label) {
		label.setLocation(0, locationY);
		label.setSize(label.getPreferredSize());
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(label);
		locationY += label.getHeight();
		setMinimumSize(getPreferredSize());
	}
	
	public void addLabel(JButton label) {
		add(label);
	}

}
