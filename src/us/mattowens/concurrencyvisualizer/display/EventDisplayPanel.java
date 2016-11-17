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
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public abstract class EventDisplayPanel extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int timeMarkerWidth = 20;


	//Stores ThreadDisplayPanel Objects by ThreadId
	private ConcurrentHashMap<Object, EventGroupDisplayPanel> eventGroupPanelsMap;
	private CopyOnWriteArrayList<EventGroupDisplayPanel> eventGroupPanelsList;
	
	//Used by mouse listener to track mouse clicks
	private HashMap<Rectangle2D, DisplayEvent> eventRectangles;
	
	private long maxTimestamp;
	
	/*
	 * This set of variables is controlled by the scroll bars at the top of the screen
	 */
	//Factor relating timestamp to spacing
	private double spacingScalar = 0.00001;
	
	//Width of thread panels
	private int groupPanelWidth = 300;
	
	
	public EventDisplayPanel() {
		eventGroupPanelsMap = new ConcurrentHashMap<Object, EventGroupDisplayPanel>();
		eventGroupPanelsList = new CopyOnWriteArrayList<EventGroupDisplayPanel>();
		
		setLayout(null);
		addMouseListener(this);
	}
	
	protected abstract Object getEventGroupKey(DisplayEvent event);
	protected abstract String getEventGroupName(DisplayEvent fromEvent);
	protected abstract ZoomedExecutionPanel createZoomedExecutionPanel(ArrayList<DisplayEvent> events);
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		eventRectangles = new HashMap<Rectangle2D, DisplayEvent>();
		Graphics2D g2 = (Graphics2D) g;
		
		for(EventGroupDisplayPanel threadPanel : eventGroupPanelsList) {
			drawRightBoundLine(g2, threadPanel);
			drawGroupDisplayName(g2, threadPanel);
			drawEventMarkers(g2, threadPanel);
		}
		
		drawTimeMarkers(g2);
	}
	
	private void drawTimeMarkers(Graphics2D g2) {
		double yPositionNeeded = timestampToPosition(maxTimestamp);
		Line2D.Double markerLine = new Line2D.Double(5, 0, 
				5, yPositionNeeded);
		g2.draw(markerLine);
		
		int position = 0;
		do {
			position += 100;
			Line2D.Double tick = new Line2D.Double(2.5, position, 7.5, position);
			g2.draw(tick);
			
			double timestampInMillis = Math.round((double)positionToTimestamp(position)/1000000.0);
			String label = String.valueOf(timestampInMillis) + "ms";
			g2.drawString(label, 8, position);
		} while(yPositionNeeded > position);
	}
	
	private void drawEventMarkers(Graphics2D g2, EventGroupDisplayPanel groupPanel) {
		DisplayEvent previousEvent = null;
		
		for(DisplayEvent nextEvent : groupPanel.getEventsArray()) {
			if(previousEvent != null) {
				Line2D.Double connectingLine = new Line2D.Double(groupPanel.getMidPoint(),
						timestampToPosition(previousEvent.getTimestamp()), groupPanel.getMidPoint(), 
						timestampToPosition(nextEvent.getTimestamp()));
				g2.draw(connectingLine);
			}
			
			double groupPanelWidth = groupPanel.getWidth();
			double rectangleWidth = groupPanelWidth/2;
			double rectangleHeight = 5;
			double rectangleX = groupPanel.getLeftBound() + groupPanelWidth/4;
			double rectangleY = timestampToPosition(nextEvent.getTimestamp()) - 2.5;
			
			Rectangle2D eventRectangle = new Rectangle2D.Double(rectangleX, rectangleY, rectangleWidth, rectangleHeight);
			eventRectangles.put(eventRectangle, nextEvent);
			g2.setColor(EventColors.getColor(nextEvent.getEventClass()));
			g2.draw(eventRectangle);
			
			previousEvent = nextEvent;
			g2.setColor(Color.BLACK);
		}
		
	}

	private void drawGroupDisplayName(Graphics2D g2, EventGroupDisplayPanel groupPanel) {
		double groupPanelWidth = groupPanel.getWidth();
		double rectangleWidth = groupPanelWidth/2;
		double rectangleX = groupPanel.getLeftBound() + groupPanelWidth/4;
		double rectangleY = timestampToPosition(groupPanel.getFirstTimeStamp()) - 20;
		

		int stringWidth = g2.getFontMetrics().stringWidth(groupPanel.getDisplayName());
		int labelXPosition = (int)(rectangleX + (rectangleWidth - stringWidth)/2);
		g2.drawString(groupPanel.getDisplayName(), labelXPosition, (int)rectangleY+15);
	}

	private void drawRightBoundLine(Graphics2D g2, EventGroupDisplayPanel threadPanel) {
		Line2D.Double boundaryLine = new Line2D.Double(threadPanel.getRightBound(), 0, 
				threadPanel.getRightBound(), getMinimumHeight());
		g2.draw(boundaryLine);
	}
	
	public void setSpacingScalar(double scalar) {
		spacingScalar = scalar;
		refreshDisplay();
	}
	
	public void setGroupPanelWidth(int width) {
		groupPanelWidth = width;
		for(int threadNum = 0; threadNum < eventGroupPanelsList.size(); threadNum++) {
			EventGroupDisplayPanel threadPanel = eventGroupPanelsList.get(threadNum);
			threadPanel.setBounds(timeMarkerWidth + groupPanelWidth * threadNum, groupPanelWidth * (threadNum +1));
		}
		refreshDisplay();
	}

	

	
	public void addEvent(DisplayEvent event) {
		if(!eventGroupPanelsMap.containsKey(getEventGroupKey(event))) {
			addNewEventGroup(event);
		}
		maxTimestamp = event.getTimestamp();
		addDisplayEvent(event);
	}
	
	//Adds a new column to the display for the thread name and id in the DisplayEvent fromEvent
	private void addNewEventGroup(DisplayEvent fromEvent) {
		EventGroupDisplayPanel newGroupPanel = new EventGroupDisplayPanel(getEventGroupKey(fromEvent), 
				getEventGroupName(fromEvent));
		int numEventGroups = eventGroupPanelsList.size();
		newGroupPanel.setBounds(timeMarkerWidth + groupPanelWidth * numEventGroups, 
				timeMarkerWidth + groupPanelWidth * (numEventGroups +1));
		eventGroupPanelsMap.put(getEventGroupKey(fromEvent), newGroupPanel);
		eventGroupPanelsList.add(newGroupPanel);
		
		refreshDisplay();
	}
	
	//Adds a new event to the display
	private void addDisplayEvent(DisplayEvent newEvent) {
		Object groupKey = getEventGroupKey(newEvent);
		EventGroupDisplayPanel groupPanel = eventGroupPanelsMap.get(groupKey);
		groupPanel.addEvent(newEvent);

		refreshDisplay();
	}
	
	//Returns the required height of the tallest EventGroupDisplayPanel
	private int getMinimumHeight() {
		long maxTimestamp = 0;
		for(EventGroupDisplayPanel groupPanel : eventGroupPanelsList) {
			long timestamp = groupPanel.getMostRecentTimestamp();
			if(timestamp > maxTimestamp) {
				maxTimestamp = timestamp;
			}
		}
		return (int)timestampToPosition(maxTimestamp);
	}
	
	private void setPanelSize() {
		setPreferredSize(new Dimension(eventGroupPanelsList.size() * groupPanelWidth + timeMarkerWidth+ 10, 
				getMinimumHeight() + 10));
	}
	
	private double timestampToPosition(long timestamp) {
		return timestamp * spacingScalar;
	}
	
	private long positionToTimestamp(int position) {
		return (long) (position/spacingScalar);
	}
	
	private void refreshDisplay() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setPanelSize();
				revalidate();
				repaint();
			}
		});
	}
	

	
	/*
	 * Mouse listener methods
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		Point clickPoint = e.getPoint();
		
	
		ArrayList<DisplayEvent> coveringEvents = getEventsCoveringPoint(clickPoint);
		
		if(coveringEvents.size() > 0) {

			JFrame zoomedExecutionFrame = new JFrame(getEventGroupName(coveringEvents.get(0)));
			ZoomedExecutionPanel zoomedPanel = createZoomedExecutionPanel(coveringEvents);
			JScrollPane scrollPane = new JScrollPane(zoomedPanel);
			zoomedExecutionFrame.add(scrollPane);
			zoomedExecutionFrame.setSize(700, 700);
			zoomedExecutionFrame.setVisible(true);
		}
	}

	private ArrayList<DisplayEvent> getEventsCoveringPoint(Point clickPoint) {
		ArrayList<DisplayEvent> eventsCovering = new ArrayList<DisplayEvent>();
		
		for(Rectangle2D rectangle : eventRectangles.keySet()) {
			if(rectangle.contains(clickPoint)) {
				eventsCovering.add(eventRectangles.get(rectangle));
			}
		}
		
		return eventsCovering;
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
