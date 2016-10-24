package us.mattowens.concurrencyvisualizer.datacapture.runnable;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class RunnableEvent extends Event {
	
	private RunnableEventType eventType;
	
	public RunnableEvent(String runnable, RunnableEventType eventType) {
		super(runnable);
		setEventType(eventType);
	}
	
	public RunnableEventType getEventType() {
		return eventType;
	}
	
	public void setEventType(RunnableEventType eventType) {
		this.eventType = eventType;
		eventMap.put("EventType", eventType.toString());
	}
}
