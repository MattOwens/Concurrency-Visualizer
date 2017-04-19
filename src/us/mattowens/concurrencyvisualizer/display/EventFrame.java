package us.mattowens.concurrencyvisualizer.display;

import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class EventFrame extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	
	public EventFrame(Event event) {
		super(event.getEventTypeLabel() + "@" + event.getTarget(), true, true, false, false);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		addNewLabel("Event Class", event.getEventClassString());
		addNewLabel("Event Type", event.getEventTypeLabel());
		addNewLabel("Target", event.getTarget());
		addNewLabel("Timestamp", event.getTimestamp());
		//addNewLabel("Thread Name", event.getThreadName());
		addNewLabel("Thread Id", event.getThreadId());
		addNewLabel("Join Point", event.getJoinPointName());
		
		for(Object pair : event.getEventMap().entrySet()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, Object> mapEntry = (Map.Entry<String, Object>) pair;
			addNewLabel(mapEntry.getKey(), mapEntry.getValue());
		}
		pack();
	}
	
	private void addNewLabel(String key, Object value) {
		if(value == null) {
			System.out.println("Null pointer in addNewLabel : " + key);
		}
		else {			
			JLabel fieldLabel = new JLabel(key + ": " + value.toString());
			this.add(fieldLabel);
		}
	}

}
