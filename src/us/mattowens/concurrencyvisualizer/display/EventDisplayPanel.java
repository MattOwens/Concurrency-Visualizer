package us.mattowens.concurrencyvisualizer.display;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class EventDisplayPanel extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	


	//Stores ThreadDisplayPanel Objects by ThreadId
	private ConcurrentHashMap<Long, ThreadDisplayPanel> threadPanelsMap;
	private CopyOnWriteArrayList<ThreadDisplayPanel> threadPanelsList;
	
	//Used by mouse listener to track mouse clicks
	private HashMap<Rectangle2D, DisplayEvent> eventRectangles;
	
	/*
	 * This set of variables is controlled by the scroll bars at the top of the screen
	 */
	
	//Amount to delay between events
	private long displayDelay = 100;
	
	//Factor relating timestamp to spacing
	private double spacingScalar = 0.00001;
	
	//Width of thread panels
	private int threadPanelWidth = 300;
	
	private Thread eventLoaderThread;	
	
	public EventDisplayPanel(ConcurrencyVisualizerRunMode runMode) {
		threadPanelsMap = new ConcurrentHashMap<Long, ThreadDisplayPanel>();
		threadPanelsList = new CopyOnWriteArrayList<ThreadDisplayPanel>();
		setLayout(null);
		addMouseListener(this);
		
		if(runMode == ConcurrencyVisualizerRunMode.Live) {
			eventLoaderThread = new Thread(new ReadContinuouslyDataLoader());
		} else if (runMode == ConcurrencyVisualizerRunMode.OnDelay) {
			eventLoaderThread = new Thread(new ReadDelayDataLoader(this));
		} else if (runMode == ConcurrencyVisualizerRunMode.ReadAll) {
			eventLoaderThread = new Thread(new ReadAllDataLoader());
		}
		
		if(eventLoaderThread != null) {
			eventLoaderThread.start();
		}

	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		eventRectangles = new HashMap<Rectangle2D, DisplayEvent>();
		Graphics2D g2 = (Graphics2D) g;
		
		for(ThreadDisplayPanel threadPanel : threadPanelsList) {
			drawRightBoundLine(g2, threadPanel);
			drawThreadNameBox(g2, threadPanel);
			drawEventMarkers(g2, threadPanel);
		}
	}
	
	private void drawEventMarkers(Graphics2D g2, ThreadDisplayPanel threadPanel) {
		Iterator<DisplayEvent> events = threadPanel.getEventsIterator();
		DisplayEvent previousEvent = null, nextEvent;
		
		while(events.hasNext()) {
			nextEvent = events.next();
			
			if(previousEvent != null) {
				Line2D.Double connectingLine = new Line2D.Double(threadPanel.getMidPoint(),
						timestampToPosition(previousEvent.getTimestamp()), threadPanel.getMidPoint(), 
						timestampToPosition(nextEvent.getTimestamp()));
				g2.draw(connectingLine);
			}
			
			double threadPanelWidth = threadPanel.getRightBound() - threadPanel.getLeftBound();
			double rectangleWidth = threadPanelWidth/2;
			double rectangleHeight = 5;
			double rectangleX = threadPanel.getLeftBound() + threadPanelWidth/4;
			double rectangleY = timestampToPosition(nextEvent.getTimestamp()) -2.5;
			
			Rectangle2D eventRectangle = new Rectangle2D.Double(rectangleX, rectangleY, rectangleWidth, rectangleHeight);
			eventRectangles.put(eventRectangle, nextEvent);
			g2.draw(eventRectangle);
			
			previousEvent = nextEvent;
		}
		
	}

	private void drawThreadNameBox(Graphics2D g2, ThreadDisplayPanel threadPanel) {
		double threadPanelWidth = threadPanel.getRightBound() - threadPanel.getLeftBound();
		double rectangleWidth = threadPanelWidth/2;
		double rectangleHeight = 30;
		double rectangleX = threadPanel.getLeftBound() + threadPanelWidth/4;
		double rectangleY = timestampToPosition(threadPanel.getFirstTimeStamp()) - 20;
		
		Rectangle2D.Double nameBox = new Rectangle2D.Double(rectangleX, rectangleY, rectangleWidth, rectangleHeight);
		g2.draw(nameBox);
		int stringWidth = g2.getFontMetrics().stringWidth(threadPanel.getDisplayName());
		int labelXPosition = (int)(rectangleX + (rectangleWidth - stringWidth)/2);
		g2.drawString(threadPanel.getDisplayName(), labelXPosition, (int)rectangleY+15);
	}

	private void drawRightBoundLine(Graphics2D g2, ThreadDisplayPanel threadPanel) {
		Line2D.Double boundaryLine = new Line2D.Double(threadPanel.getRightBound(), 0, 
				threadPanel.getRightBound(), getMinimumHeight());
		g2.draw(boundaryLine);
	}

	public void setDisplayDelay(long delay) {
		displayDelay = delay;
	}
	
	public void setSpacingScalar(double scalar) {
		spacingScalar = scalar;
		refreshDisplay();
	}
	
	public void setThreadPanelWidth(int width) {
		threadPanelWidth = width;
		for(int threadNum = 0; threadNum < threadPanelsList.size(); threadNum++) {
			ThreadDisplayPanel threadPanel = threadPanelsList.get(threadNum);
			threadPanel.setBounds(threadPanelWidth * threadNum, threadPanelWidth * (threadNum +1));
		}
		refreshDisplay();
	}
	
	public void showEventInputDone() {
		EventDisplayPanel panel = this;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JOptionPane.showMessageDialog(panel, "Event replay has finished.", "No More Events", 
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
	
	//Adds the next event in the event queue to the display.  Returns true if and only if an event was added
	public boolean addNextEvent() {
		DisplayEvent nextEvent = InputEventQueue.getNextEvent();
		
		if(nextEvent == null) {
			return false;
		}
		
		if(!threadPanelsMap.containsKey(nextEvent.getThreadId())) {
			addNewThread(nextEvent);
		}
		addDisplayEvent(nextEvent);
		return true;
	}
	
	
	//Adds a new column to the display for the thread name and id in the DisplayEvent fromEvent
	private void addNewThread(DisplayEvent fromEvent) {
		ThreadDisplayPanel newThreadPanel = new ThreadDisplayPanel(fromEvent.getThreadId(), fromEvent.getThreadName());
		int numThreads = threadPanelsList.size();
		newThreadPanel.setBounds(threadPanelWidth * numThreads, threadPanelWidth * (numThreads +1));
		threadPanelsMap.put(fromEvent.getThreadId(), newThreadPanel);
		threadPanelsList.add(newThreadPanel);
		
		refreshDisplay();
	}
	
	//Adds a new event to the display
	private void addDisplayEvent(DisplayEvent newEvent) {
		long threadId = newEvent.getThreadId();
		ThreadDisplayPanel threadPanel = threadPanelsMap.get(threadId);
		threadPanel.addEvent(newEvent);

		refreshDisplay();
	}
	
	//Returns the required height of the tallest thread panel
	private int getMinimumHeight() {
		long maxTimestamp = 0;
		for(ThreadDisplayPanel threadPanel : threadPanelsList) {
			long timestamp = threadPanel.getMostRecentTimestamp();
			if(timestamp > maxTimestamp) {
				maxTimestamp = timestamp;
			}
		}
		return (int)timestampToPosition(maxTimestamp);
	}
	
	private void setPanelSize() {
		setPreferredSize(new Dimension(threadPanelsList.size() * threadPanelWidth + 10, getMinimumHeight() + 10));
	}
	
	private double timestampToPosition(long timestamp) {
		return timestamp * spacingScalar;
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
		
		for(Rectangle2D rectangle : eventRectangles.keySet()) {
			if(rectangle.contains(clickPoint)) {
				DisplayEventFrame eventFrame = new DisplayEventFrame(eventRectangles.get(rectangle));
				Dimension size = eventFrame.getPreferredSize();
				eventFrame.setVisible(true);
				eventFrame.setBounds(clickPoint.x + 10, clickPoint.y + 10, size.width, size.height);
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
	
	/*
	 * This reads all of the events from the event queue in one shot
	 */
	class ReadAllDataLoader implements Runnable {
		public void run() {
			boolean hasEvents = true;
			
			while(hasEvents) {
				hasEvents = addNextEvent();
			}
		}
	}
	
	/*
	 * This reads all of the events from the event queue, pausing for a delay between each event
	 */
	class ReadDelayDataLoader implements Runnable {
		private EventDisplayPanel parentPanel;
		public ReadDelayDataLoader(EventDisplayPanel parentPanel) {
			this.parentPanel = parentPanel;
		}
		public void run() {
			boolean hasEvents = true;
			while(hasEvents) {
				hasEvents = addNextEvent();
				
				try {
					Thread.sleep(displayDelay);
				} catch (InterruptedException e) {
					return;
				}
			}
			parentPanel.showEventInputDone();
		}
	}
	
	/*
	 * This continues trying to read events until the thread is interrupted
	 */
	class ReadContinuouslyDataLoader implements Runnable {
		public void run() {
			
			while(true) {
				boolean hasEvents = true;
				
				while(hasEvents) {
					hasEvents = addNextEvent();
				}
				
				try {
					Thread.sleep(displayDelay);
				} catch(InterruptedException e) {
					return;
				}
				
				if(Thread.interrupted()) return;
			}
		}
	}


}
