package us.mattowens.concurrencyvisualizer.display;

import java.util.ArrayList;

public class ThreadZoomedExecutionPanel extends ZoomedExecutionPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ThreadZoomedExecutionPanel(ArrayList<DisplayEvent> events) {
		super(events);
		
	}

	@Override
	protected String getEventLabel(DisplayEvent event) {
		return event.getJoinPointName() + "(" +
				event.getEventType() + ")@" + event.getTargetDescription();
	}

}
