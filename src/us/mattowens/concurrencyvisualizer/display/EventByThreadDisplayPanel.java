package us.mattowens.concurrencyvisualizer.display;

import java.util.ArrayList;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class EventByThreadDisplayPanel extends EventDisplayPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected Object getEventGroupKey(Event event) {
		return event.getThreadId();
	}

	@Override
	protected String getEventGroupName(Event fromEvent) {
		return String.format("Thread-%d: %s", fromEvent.getThreadId(), fromEvent.getThreadName());
	}

	@Override
	protected ZoomedExecutionPanel createZoomedExecutionPanel(ArrayList<Event> events) {
		return new ThreadZoomedExecutionPanel(events);
	}

}
