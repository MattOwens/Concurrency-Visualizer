package us.mattowens.concurrencyvisualizer.display;

import java.util.concurrent.ConcurrentHashMap;

import javax.swing.*;

public class EventDisplayPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Stores ThreadDisplayPanel Objects by ThreadId
	private ConcurrentHashMap<Long, ThreadDisplayPanel> threadPanels;
	
	//Amount to delay between 
	private long displayDelay = 100;
	
	private Thread eventLoaderThread;
	

	
	public EventDisplayPanel(ConcurrencyVisualizerRunMode runMode) {
		threadPanels = new ConcurrentHashMap<Long, ThreadDisplayPanel>();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		if(runMode == ConcurrencyVisualizerRunMode.Live) {
			eventLoaderThread = new Thread(new ReadContinuouslyDataLoader(this));
		} else if (runMode == ConcurrencyVisualizerRunMode.OnDelay) {
			eventLoaderThread = new Thread(new ReadDelayDataLoader(this));
		} else if (runMode == ConcurrencyVisualizerRunMode.ReadAll) {
			eventLoaderThread = new Thread(new ReadAllDataLoader(this));
		}
		
		if(eventLoaderThread != null) {
			System.out.println("Starting loader thread");
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
		threadPanels.put(fromEvent.getThreadId(), newThreadPanel);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				add(new JSeparator(SwingConstants.VERTICAL));
				add(newThreadPanel);
			}
		});
	}
	
	//Adds a new event to the display
	private void addDisplayEvent(DisplayEvent newEvent) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				EventPanel eventPanel = new EventPanel(newEvent);
				threadPanels.get(newEvent.getThreadId()).addEventPanel(eventPanel);
				revalidate();
				repaint();
			}
		});
	}
	

	
	/*
	 * This reads all of the events from the event queue in one shot
	 */
	class ReadAllDataLoader implements Runnable {
		private EventDisplayPanel parentPanel;
		public ReadAllDataLoader(EventDisplayPanel parentPanel) {
			this.parentPanel = parentPanel;
		}
		
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
		private EventDisplayPanel parentPanel;
		public ReadContinuouslyDataLoader(EventDisplayPanel parentPanel) {
			this.parentPanel = parentPanel;
		}
		
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
