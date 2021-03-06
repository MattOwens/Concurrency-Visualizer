package us.mattowens.concurrencyvisualizer.display;

import java.util.ArrayList;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class TargetZoomedExecutionPanel extends ZoomedExecutionPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TargetZoomedExecutionPanel(ArrayList<Event> events) {
		super(events);
	}

	@Override
	protected String getEventLabel(Event event) {
		return event.getJoinPointName() + "(" + event.getEventTypeLabel() 
		+ ")@" + event.getThreadName();
	}

}
