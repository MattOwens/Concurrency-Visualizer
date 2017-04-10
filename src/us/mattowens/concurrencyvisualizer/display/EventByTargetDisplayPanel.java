package us.mattowens.concurrencyvisualizer.display;

import java.util.ArrayList;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class EventByTargetDisplayPanel extends EventDisplayPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected Object getEventGroupKey(Event event) {
		return event.getTarget();
	}

	@Override
	protected String getEventGroupName(Event fromEvent) {
		return fromEvent.getTarget();
	}

	@Override
	protected ZoomedExecutionPanel createZoomedExecutionPanel(ArrayList<Event> events) {
		return new TargetZoomedExecutionPanel(events);
	}

}
