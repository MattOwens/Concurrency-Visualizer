package us.mattowens.concurrencyvisualizer.display;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;

public class EventPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<JLabel> dataLabels;
	
	public EventPanel(DisplayEvent event) {
		dataLabels = new ArrayList<JLabel>();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		addNewLabel("Event Class", event.getEventClass());
		addNewLabel("Event Type", event.getEventType());
		addNewLabel("Target", event.getTargetDescription());
		addNewLabel("Timestamp", event.getTimestamp());
		addNewLabel("Thread Name", event.getThreadName());
		addNewLabel("Thread Id", event.getThreadId());
		addNewLabel("Join Point", event.getJoinPointName());
		
		
		for(Object pair : event.getEventMap().entrySet()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, Object> mapEntry = (Map.Entry<String, Object>) pair;
			addNewLabel(mapEntry.getKey(), mapEntry.getValue());
		}
		
		
	}
	
	private void addNewLabel(String key, Object value) {
		if(value == null) {
			System.out.println("Null pointer in addNewLabel : " + key);
		}
		else {
			JLabel newLabel = new JLabel(key + ": " + value.toString());
			dataLabels.add(newLabel);
			this.add(newLabel);
		}
	}

}
