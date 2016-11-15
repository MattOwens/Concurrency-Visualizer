package us.mattowens.concurrencyvisualizer.display;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

public class ZoomedExecutionPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<DisplayEvent> events;
	private double timeScalingMultiplier;
	
	public ZoomedExecutionPanel(ArrayList<DisplayEvent> events) {
		this.events = events;
		Collections.sort(events, new DisplayEventComparator());
		setScalingConstants();
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int maxWidth = 0;
		int eventYLocation = 0;;
		
		long firstEventTimestamp = events.get(0).getTimestamp();
		for(DisplayEvent event : events) {
			
			String eventLabel = event.getJoinPointName() + "@" + event.getTargetDescription();
			int displayWidth = g.getFontMetrics().stringWidth(eventLabel);
			if(displayWidth > maxWidth) {
				maxWidth = displayWidth;
			}
			
			eventYLocation = (int)(timeScalingMultiplier * (event.getTimestamp()-firstEventTimestamp)) + 20;
			g.drawString(eventLabel, 10, eventYLocation);
		}
		
		//Set preferred size so all drawn text is visible
		setPreferredSize(new Dimension(maxWidth + 20,  eventYLocation + 20));
	}

	private void setScalingConstants() {
		long minDifference = Long.MAX_VALUE;
		
		for(int i = 0; i < events.size() - 1; i++) {
			 DisplayEvent firstEvent = events.get(i);
			 DisplayEvent secondEvent = events.get(i+1);
			 
			 long timeDifference = secondEvent.getTimestamp() - firstEvent.getTimestamp();
			 
			 if(timeDifference < minDifference) {
				 minDifference = timeDifference;
			 }
		}
		
		
		timeScalingMultiplier = 15.0/(double)minDifference;
	}
}
