package us.mattowens.concurrencyvisualizer.display;

import java.awt.Dimension;
import java.awt.Rectangle;

import java.util.concurrent.ConcurrentHashMap;

import javax.swing.*;

public class EventDisplayPanel extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int THREAD_PANEL_WIDTH = 300;
	private static final int DEFAULT_THREAD_PANEL_HEIGHT = 400;
	
	//Stores ThreadDisplayPanel Objects by ThreadId
	private ConcurrentHashMap<Long, ThreadDisplayPanel> threadPanels;
	private ConcurrentHashMap<Long, Integer> threadPanelMinimumHeights;
	private JDesktopPane contentPane;
	private int numThreadPanels = 0;
	

	
	//Amount to delay between events
	private long displayDelay = 100;
	
	private Thread eventLoaderThread;	
	
	public EventDisplayPanel(ConcurrencyVisualizerRunMode runMode) {
		super("Concurrency Visualizer", false, false, false, false);
		threadPanels = new ConcurrentHashMap<Long, ThreadDisplayPanel>();
		threadPanelMinimumHeights = new ConcurrentHashMap<Long, Integer>();
		contentPane = new JDesktopPane();
		setContentPane(contentPane);
		setLayout(null);
		
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
	
	public void setDisplayDelay(long delay) {
		displayDelay = delay;
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
		
		if(!threadPanels.containsKey(nextEvent.getThreadId())) {
			addNewThread(nextEvent);
		}
		addDisplayEvent(nextEvent);
		return true;
	}
	
	
	//Adds a new column to the display for the thread name and id in the DisplayEvent fromEvent
	private void addNewThread(DisplayEvent fromEvent) {
		ThreadDisplayPanel newThreadPanel = new ThreadDisplayPanel(fromEvent.getThreadId(), fromEvent.getThreadName());
		newThreadPanel.setVisible(true);
		threadPanels.put(fromEvent.getThreadId(), newThreadPanel);
		threadPanelMinimumHeights.put(fromEvent.getThreadId(), DEFAULT_THREAD_PANEL_HEIGHT);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				newThreadPanel.setBounds(numThreadPanels * THREAD_PANEL_WIDTH, 0, THREAD_PANEL_WIDTH, DEFAULT_THREAD_PANEL_HEIGHT);
				numThreadPanels++;
				contentPane.add(newThreadPanel);
				setPanelSize();
				revalidate();
				repaint();
			}
		});
	}
	
	//Adds a new event to the display
	private void addDisplayEvent(DisplayEvent newEvent) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				DisplayEventComponent eventPanel = new DisplayEventComponent(newEvent);
				long threadId = newEvent.getThreadId();
				ThreadDisplayPanel threadPanel = threadPanels.get(threadId);
				int addedHeight = threadPanel.addEvent(eventPanel, newEvent.getTimestamp());
				int newMinimumHeight = threadPanelMinimumHeights.get(threadId) + addedHeight;
				threadPanelMinimumHeights.put(threadId, newMinimumHeight);
				Rectangle bounds = threadPanel.getBounds();
				threadPanel.setBounds(bounds.x, bounds.y, bounds.width, newMinimumHeight);
				setPanelSize();
				revalidate();
				repaint();
			}
		});
	}
	
	//Returns the required height of the tallest thread panel
	private int getMinimumHeight() {
		int max = 0;
		for(Integer height : threadPanelMinimumHeights.values()) {
			if(height > max) {
				max = height;
			}
		}
		return max;
	}
	
	private void setPanelSize() {
		setPreferredSize(new Dimension(numThreadPanels * THREAD_PANEL_WIDTH + 10, getMinimumHeight() + 10));
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
