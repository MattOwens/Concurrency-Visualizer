package us.mattowens.concurrencyvisualizer.display;

import java.util.ArrayList;

public class EventByTargetDisplayPanel extends EventDisplayPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected Object getEventGroupKey(DisplayEvent event) {
		return event.getTargetDescription();
	}

	@Override
	protected String getEventGroupName(DisplayEvent fromEvent) {
		return fromEvent.getTargetDescription();
	}

	@Override
	protected ZoomedExecutionPanel createZoomedExecutionPanel(ArrayList<DisplayEvent> events) {
		return new TargetZoomedExecutionPanel(events);
	}

}
