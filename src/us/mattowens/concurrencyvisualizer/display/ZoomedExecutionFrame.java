package us.mattowens.concurrencyvisualizer.display;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class ZoomedExecutionFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String threadIdentifier;
	
	public ZoomedExecutionFrame(ArrayList<DisplayEvent> events) {
		super();
				
		if(events.size() > 0) {
			DisplayEvent event = events.get(0);
			threadIdentifier = "Thread " + event.getThreadId() + ": " + event.getThreadName();
			setTitle(threadIdentifier);
			ZoomedExecutionPanel panel = new ZoomedExecutionPanel(events);
			JScrollPane scrollPane = new JScrollPane(panel);
			add(scrollPane);
			setSize(500, 500);
		}
	}

}
