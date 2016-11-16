package us.mattowens.concurrencyvisualizer.display;

import java.awt.Container;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public class ConcurrencyVisualizerMainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ConcurrencyVisualizerRunMode runMode;
	private EventDisplayPanel[] displayPanels;
	private JToolBar toolBar;
	private Thread eventLoaderThread;
	
	//Amount to delay between events
	private long displayDelay = 100;
	
	public ConcurrencyVisualizerMainWindow(ConcurrencyVisualizerRunMode runMode) {
		this.runMode = runMode;
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e)
		    {
		        EventQueue.stopEventOutput();
		        InputEventQueue.stopEventInput();
		        System.exit(0);
		    }
		});
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		createMenuBar();
		
		createToolBar();
		
		createEventDisplay();

		startReadingEvents(runMode);
	}


	/*
	 * Event control
	 */
	
	private boolean addNextEvent() {
		DisplayEvent nextEvent = InputEventQueue.getNextEvent();
		
		if(nextEvent == null) {
			return false;
		}
		
		addEvent(nextEvent);
		return true;
	}
	
	private void addEvent(DisplayEvent event) {
		for(EventDisplayPanel panel : displayPanels) {
			panel.addEvent(event);
		}
	}


	private void setSpacingScalar(double newScalar) {
		for(EventDisplayPanel panel : displayPanels) {
			panel.setSpacingScalar(newScalar);
		}
	}
	
	private void setGroupPanelWidth(int newWidth) {
		for(EventDisplayPanel panel : displayPanels) {
			panel.setGroupPanelWidth(newWidth);
		}
	}

	
	/*
	 * User interface creation
	 */
	
	private void createEventDisplay() {
		EventDisplayPanel threadDisplayPanel = new EventDisplayPanel();
		EventDisplayPanel targetDisplayPanel  = new EventDisplayPanel();
		displayPanels = new EventDisplayPanel[]{threadDisplayPanel, targetDisplayPanel };
		
		
		JScrollPane threadScrollPane = new JScrollPane(threadDisplayPanel);
		JScrollPane targetScrollPane = new JScrollPane(targetDisplayPanel);
		
		JTabbedPane tabPane = new JTabbedPane();
		tabPane.addTab("Events By Thread", threadScrollPane);
		tabPane.addTab("Events By Target", targetScrollPane);
		add(tabPane);
	}

	private void createToolBar() {
		toolBar = new JToolBar("Execution Control");
		
		JLabel scalingLabel = new JLabel("Time Scaling:");
		toolBar.add(scalingLabel);
		JSlider scalingSlider = new JSlider(JSlider.HORIZONTAL, 10000, 1000000, 100000);
		toolBar.add(scalingSlider);
		scalingSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				setSpacingScalar(1.0/(double)scalingSlider.getValue());
			}
		});
		
		JLabel widthLabel = new JLabel("Column Width:");
		toolBar.add(widthLabel);
		JSlider widthSlider = new JSlider(JSlider.HORIZONTAL, 200, 1000, 300);
		widthSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				setGroupPanelWidth(widthSlider.getValue());
			}
		});
		toolBar.add(widthSlider);
		
		
		if(runMode == ConcurrencyVisualizerRunMode.StepThrough) {
			addStepThroughControls();
		} else if(runMode == ConcurrencyVisualizerRunMode.OnDelay) {
			addDelayControls();
		}
		

		toolBar.setVisible(true);
		
		getContentPane().add(toolBar);
	}
	
	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu helpMenu = new JMenu("Help");
		
		JMenuItem aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openWebPage("http://mattowens.us/concurrency-visualizer/");
			}
		});
		helpMenu.add(aboutMenuItem);
		JMenuItem userGuideMenuItem = new JMenuItem("User Guide");
		userGuideMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openWebPage("http://mattowens.us/concurrency-visualizer-user-guide/");
			}
		});
		helpMenu.add(userGuideMenuItem);
		JMenuItem sourceMenuItem = new JMenuItem("Source");
		sourceMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openWebPage("http://github.com/MattOwens/Concurrency-Visualizer");
			}
		});
		helpMenu.add(sourceMenuItem);
		JMenuItem colorLegendMenuItem = new JMenuItem("Show Color Legend");
		colorLegendMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showColorLegend();
			}
		});
		helpMenu.add(colorLegendMenuItem);
		
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);
	}

	private void addDelayControls() {
		JLabel sliderLabel = new JLabel("Delay in ms:");
		toolBar.add(sliderLabel);
		JSlider delayTimeSlider = new JSlider(JSlider.HORIZONTAL, 0, 2000, 100);
		delayTimeSlider.setMajorTickSpacing(500);
		delayTimeSlider.setMinorTickSpacing(100);
		delayTimeSlider.setPaintTicks(true);
		delayTimeSlider.setPaintLabels(true);
		delayTimeSlider.setSnapToTicks(true);
		delayTimeSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				displayDelay = delayTimeSlider.getValue();
			}
			
		});
		toolBar.add(delayTimeSlider);
	}

	private void addStepThroughControls() {
		JButton nextEventButton = new JButton();
		nextEventButton.setActionCommand("Show next event");
		nextEventButton.setText("Next");
		toolBar.add(nextEventButton);
		nextEventButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean eventAdded = addNextEvent();
				
				if(!eventAdded) {
					showEventInputDone();
					nextEventButton.setEnabled(false);
				}
			}
		});
	}
	
	
	/*
	 * Help menu helpers
	 */
	
	private void openWebPage(String url) {
		if(Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
				try {
					desktop.browse(new URI(url));
				} catch (IOException | URISyntaxException e1) {
					showWebPageError(url);
				}
		} else {
			showWebPageError(url);
		}
	}
	
	private void showWebPageError(String url) {
		JOptionPane.showMessageDialog(this,  "Could not open requested resource.  Please visit " + url + ".",
				"Help Error", JOptionPane.ERROR_MESSAGE);
	}
	
	private void showColorLegend() {
		ColorLegendFrame legendFrame = new ColorLegendFrame();
		legendFrame.setVisible(true);
	}
	
	
	/*
	 * Event Reading
	 */
	
	private void startReadingEvents(ConcurrencyVisualizerRunMode runMode) {
		if(runMode == ConcurrencyVisualizerRunMode.Live) {
			eventLoaderThread = new Thread(new ReadContinuouslyDataLoader());
		} else if (runMode == ConcurrencyVisualizerRunMode.OnDelay) {
			eventLoaderThread = new Thread(new ReadDelayDataLoader());
		} else if (runMode == ConcurrencyVisualizerRunMode.ReadAll) {
			eventLoaderThread = new Thread(new ReadAllDataLoader());
		}
		
		if(eventLoaderThread != null) {
			eventLoaderThread.start();
		}
	}
	
	public void showEventInputDone() {
		JFrame frame = this;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JOptionPane.showMessageDialog(frame, "Event replay has finished.", "No More Events", 
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
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
			showEventInputDone();
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
	
	@Override
	public String toString() {
		return "ConcurrencyVisualizerMainWindow";
	}
}