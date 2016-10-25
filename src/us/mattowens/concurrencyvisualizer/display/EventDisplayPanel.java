package us.mattowens.concurrencyvisualizer.display;

import java.awt.Dimension;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.*;

public class EventDisplayPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Stores ThreadDisplayPanel Objects by ThreadId
	private ConcurrentHashMap<Long, ThreadDisplayPanel> threadPanels;

	
	public EventDisplayPanel() {
		threadPanels = new ConcurrentHashMap<Long, ThreadDisplayPanel>();
		//setMinimumSize(new Dimension(500,500));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		Thread eventLoaderThread = new Thread(new Runnable() {
			public void run() {
				
				while(true) {
					DisplayEvent nextEvent = InputEventQueue.getNextEvent();
					if(nextEvent != null) {
						EventPanel eventPanel = new EventPanel(nextEvent);
						
						if(!threadPanels.containsKey(nextEvent.getThreadId())) {
							ThreadDisplayPanel newThreadPanel = new ThreadDisplayPanel(nextEvent.getThreadId(), nextEvent.getThreadName());
							threadPanels.put(nextEvent.getThreadId(), newThreadPanel);
							
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									add(new JSeparator(SwingConstants.VERTICAL));
									add(newThreadPanel);
								}
							});
							
						}
						
						//Dispatch task to add the event panel to the display
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								threadPanels.get(nextEvent.getThreadId()).addEventPanel(eventPanel);
								revalidate();
								repaint();
							}
						});
					} else {
						try {
							Thread.sleep(500);
						}
						catch(InterruptedException e) {
							//TODO do something here
							e.printStackTrace();
						}
					}
				}
			}
		});
		eventLoaderThread.start();
	}
}
