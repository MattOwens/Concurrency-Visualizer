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
		super("Thread: " + threadId + "-" + threadName, false, false, false, false);
		setLayout(null);
		currentYPosition = 0.0;
		lastTimeStamp = 0;
	}
	
	/**
	 * Adds a new event panel to the thread display
	 * @param eventPanel The event panel to add
	 * @param timestamp The timestamp of the new event
	 * @return The height that must be added to the thread display panel to accommodate the new event
	 */
	public int addEvent(DisplayEventComponent eventPanel, long timestamp) {
		eventPanel.setLineLength(timestampToPosition(timestamp) - timestampToPosition(lastTimeStamp));
		Dimension size = eventPanel.getPreferredSize();
		lastTimeStamp = timestamp;
		eventPanel.setBounds(0, (int)currentYPosition, getWidth(), size.height);
		add(eventPanel);
		events.add(eventPanel);
		
		eventPanel.repaint();
		
		revalidate();
		repaint();
		currentYPosition = timestampToPosition(lastTimeStamp);
		return size.height;
	}

	public static double timestampToPosition(long timestamp) {
		return timestamp * 0.000001 + 10;
	}
}
