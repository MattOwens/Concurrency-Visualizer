package us.mattowens.concurrencyvisualizer.display;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JPanel;

public class ZoomedExecutionPanel extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<DisplayEvent> events;
	private HashMap<Rectangle2D, DisplayEvent> eventsByRectangle;
	private double timeScalingMultiplier;
	
	public ZoomedExecutionPanel(ArrayList<DisplayEvent> events) {
		this.events = events;
		setLayout(null);
		addMouseListener(this);
		Collections.sort(events, new DisplayEventComparator());
		setScalingConstants();
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		eventsByRectangle = new HashMap<Rectangle2D, DisplayEvent>();
		
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
			
			Rectangle2D eventHitBox = new Rectangle2D.Double(0, eventYLocation - 12, displayWidth + 30, 15);
			eventsByRectangle.put(eventHitBox, event);
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


	@Override
	public void mouseClicked(MouseEvent arg0) {
		Point clickPoint = arg0.getPoint();
		
		for(Rectangle2D hitBox : eventsByRectangle.keySet()) {
			if(hitBox.contains(clickPoint)) {
				DisplayEventFrame eventFrame = new DisplayEventFrame(eventsByRectangle.get(hitBox));
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
