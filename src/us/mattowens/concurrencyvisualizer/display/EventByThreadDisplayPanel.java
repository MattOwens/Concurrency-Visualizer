package us.mattowens.concurrencyvisualizer.display;

import java.util.ArrayList;

public class EventByThreadDisplayPanel extends EventDisplayPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected Object getEventGroupKey(DisplayEvent event) {
		return event.getThreadId();
	}

	@Override
	protected String getEventGroupName(DisplayEvent fromEvent) {
		return String.format("Thread-%d: %s", fromEvent.getThreadId(), fromEvent.getThreadName());
	}

	@Override
	protected ZoomedExecutionPanel createZoomedExecutionPanel(ArrayList<DisplayEvent> events) {
		return new ThreadZoomedExecutionPanel(events);
	}

}
