package us.mattowens.concurrencyvisualizer.datacapture.runnable;

import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class RunnableEvent extends Event {
	
	private RunnableEventType eventType;
	
	public RunnableEvent(String runnable, RunnableEventType eventType) {
		super(runnable);
		this.eventType = eventType;
	}

	@Override
	public Map<String, Object> collapseToMap() {
		Map<String, Object> eventMap = super.collapseToMap();
		
		eventMap.put("EventType", eventType);
		
		return eventMap;
	}
	
	public RunnableEventType getEventType() {
		return eventType;
	}
}
