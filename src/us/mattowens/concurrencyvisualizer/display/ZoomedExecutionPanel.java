package us.mattowens.concurrencyvisualizer.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JPanel;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public abstract class ZoomedExecutionPanel extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Event> events;
	private HashMap<Rectangle2D, Event> eventsByRectangle;
	private double timeScalingMultiplier;
	private long firstEventTimestamp;
	
	public ZoomedExecutionPanel(ArrayList<Event> events) {
		this.events = events;
		setLayout(null);
		addMouseListener(this);
		Collections.sort(events, new EventComparator());
		setScalingConstants();
	}
	
	protected abstract String getEventLabel(Event event);
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		eventsByRectangle = new HashMap<Rectangle2D, Event>();
		showEvents(g);
		drawTimeMarkers((Graphics2D) g);
	}


	private void showEvents(Graphics g) {
		int maxWidth = 0;
		int eventYLocation = 0;;
		
		firstEventTimestamp = events.get(0).getTimestamp();
		for(Event event : events) {
			
			String eventLabel = getEventLabel(event);
			int displayWidth = g.getFontMetrics().stringWidth(eventLabel);
			if(displayWidth > maxWidth) {
				maxWidth = displayWidth;
			}
			
			eventYLocation = (int)(timeScalingMultiplier * (event.getTimestamp()-firstEventTimestamp)) + 20;
			g.setColor(EventColors.getColor(event.getEventClassString()));
			g.drawString(eventLabel, 100, eventYLocation);
			
			Rectangle2D eventHitBox = new Rectangle2D.Double(0, eventYLocation - 12, displayWidth + 50, 15);
			eventsByRectangle.put(eventHitBox, event);
		}
		
		
		//Set preferred size so all drawn text is visible
		setPreferredSize(new Dimension(maxWidth + 120,  eventYLocation + 20));
	}
	
	private void drawTimeMarkers(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		long maxTimestamp = events.get(events.size() - 1).getTimestamp();
		double yPositionNeeded = timestampToPosition(maxTimestamp);
		Line2D.Double markerLine = new Line2D.Double(5, 0, 
				5, yPositionNeeded + 30);
		g2.draw(markerLine);
		
		int position = 0;
		do {
			position += 40;
			Line2D.Double tick = new Line2D.Double(2.5, position, 7.5, position);
			g2.draw(tick);
			
			double timestampInMicros = Math.round((double)positionToTimestamp(position)/1000.0);
			String label = String.valueOf(timestampInMicros) + "\u00B5s";
			g2.drawString(label, 8, position);
		} while(yPositionNeeded > position);
	}

	private void setScalingConstants() {
		long minDifference = Long.MAX_VALUE;
		
		for(int i = 0; i < events.size() - 1; i++) {
			 Event firstEvent = events.get(i);
			 Event secondEvent = events.get(i+1);
			 
			 long timeDifference = secondEvent.getTimestamp() - firstEvent.getTimestamp();
			 
			 if(timeDifference < minDifference) {
				 minDifference = timeDifference;
			 }
		}
		
		
		timeScalingMultiplier = 15.0/(double)minDifference;
	}
	
	private double timestampToPosition(long timestamp) {
		return timeScalingMultiplier * (timestamp-firstEventTimestamp) + 20;
	}
	
	private long positionToTimestamp(int position) {
		return (long) ((position - 20)/timeScalingMultiplier) + firstEventTimestamp;
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		Point clickPoint = arg0.getPoint();
		
		for(Rectangle2D hitBox : eventsByRectangle.keySet()) {
			if(hitBox.contains(clickPoint)) {
				EventFrame eventFrame = new EventFrame(eventsByRectangle.get(hitBox));
				eventFrame.setSize(eventFrame.getPreferredSize());
				eventFrame.setLocation(clickPoint);
				eventFrame.setVisible(true);
				add(eventFrame);
			}
		}
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
