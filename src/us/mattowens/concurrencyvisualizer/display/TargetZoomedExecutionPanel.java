package us.mattowens.concurrencyvisualizer.display;

import java.util.ArrayList;

public class TargetZoomedExecutionPanel extends ZoomedExecutionPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TargetZoomedExecutionPanel(ArrayList<DisplayEvent> events) {
		super(events);
	}

	@Override
	protected String getEventLabel(DisplayEvent event) {
		return event.getJoinPointName() + "(" + event.getEventType() 
		+ ")@" + event.getThreadName();
	}

}
