package us.mattowens.concurrencyvisualizer.display;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JInternalFrame;

public class ThreadDisplayPanel extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double currentYPosition;
	private long lastTimeStamp;
	private ArrayList<DisplayEventComponent> events = new ArrayList<DisplayEventComponent>();

	


	public ThreadDisplayPanel(long threadId, String threadName) {
		super("Thread: " + threadId + "-" + threadName, true, false, false, false);
		setLayout(null);
		currentYPosition = 0.0;
		lastTimeStamp = 0;
	}
	
	/**
	 * Adds a new event panel to the thread display
	 * @param eventPanel The event panel to add
	 * @param timestamp The timestamp of the new event
	 * @return The new minimum height necessary for the thread panel
	 */
	public int addEvent(DisplayEventComponent eventPanel, long timestamp) {
		double lineLength = timestampToPosition(timestamp - lastTimeStamp);
		//Add event to display
		eventPanel.setLineLength(timestampToPosition(timestamp - lastTimeStamp));
		Dimension size = eventPanel.getPreferredSize();
		eventPanel.setBounds(0, (int)currentYPosition, getWidth(), size.height);
		add(eventPanel);
		events.add(eventPanel);
		eventPanel.repaint();
		revalidate();
		repaint();
		
		//Setup for next event
		lastTimeStamp = timestamp;
		int newMinimumHeight = (int) (currentYPosition + size.height) + 10;
		System.out.println("CurrentYPosition: " + currentYPosition +   " lineLength: " + lineLength + " preferredHeight: " + newMinimumHeight + " actual height: " + getHeight());
		currentYPosition = timestampToPosition(timestamp);
		return newMinimumHeight;
	}

	public static double timestampToPosition(long timestamp) {
		return timestamp * 0.000001 + 10;
	}
}
