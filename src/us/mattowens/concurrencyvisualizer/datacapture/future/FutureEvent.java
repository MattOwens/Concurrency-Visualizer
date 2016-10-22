package us.mattowens.concurrencyvisualizer.datacapture.future;


import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class FutureEvent extends Event {

	private FutureEventType eventType;
	
	public FutureEvent(String future, FutureEventType eventType) {
		super(future);
		
		setEventType(eventType);
	}
	
	public FutureEventType getEventType() {
		return eventType;
	}
	
	public void setEventType(FutureEventType eventType) {
		this.eventType = eventType;
		eventMap.put("EventType", eventType);
	}
}
