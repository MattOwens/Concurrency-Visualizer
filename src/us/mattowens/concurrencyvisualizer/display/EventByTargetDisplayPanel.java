package us.mattowens.concurrencyvisualizer.display;

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

}
