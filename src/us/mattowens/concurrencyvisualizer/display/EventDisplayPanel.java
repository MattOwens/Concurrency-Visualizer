package us.mattowens.concurrencyvisualizer.display;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.*;

public class EventDisplayPanel extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Stores ThreadDisplayPanel Objects by ThreadId
	private ConcurrentHashMap<Long, ThreadDisplayPanel> threadPanels;
	private ConcurrentHashMap<JLabel, DisplayEvent> eventPanels;

	
	//Amount to delay between events
	private long displayDelay = 100;
	
	private Thread eventLoaderThread;
	
	private JDesktopPane contentPane;
	
	

	
	public EventDisplayPanel(ConcurrencyVisualizerRunMode runMode) {
		super("ConcurrencyVisualizer", false, false, false, false);
		threadPanels = new ConcurrentHashMap<Long, ThreadDisplayPanel>();
		eventPanels = new ConcurrentHashMap<JLabel, DisplayEvent>();
		contentPane = new JDesktopPane();
		contentPane.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		setContentPane(new JScrollPane(contentPane));
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
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
				newThreadPanel.setVisible(true);
				contentPane.add(new JSeparator(SwingConstants.VERTICAL));
				contentPane.add(newThreadPanel);
			}
		});
	}
	
	//Adds a new event to the display
	private void addDisplayEvent(DisplayEvent newEvent) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JLabel eventLabel = new JLabel(newEvent.getJoinPointName() + "@" + newEvent.getTargetDescription());
				//EventPanel eventPanel = new EventPanel(newEvent);
				eventLabel.addMouseListener(new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						EventPanel eventPanel = new EventPanel(eventPanels.get(arg0.getSource()));
						threadPanels.get(newEvent.getThreadId()).addEventPanel(eventPanel, arg0.getLocationOnScreen());
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
					
				});
				eventPanels.put(eventLabel, newEvent);
				//threadPanels.get(newEvent.getThreadId()).addEventPanel(eventPanel);
				threadPanels.get(newEvent.getThreadId()).addLabel(eventLabel);
				contentPane.revalidate();
				contentPane.repaint();
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
