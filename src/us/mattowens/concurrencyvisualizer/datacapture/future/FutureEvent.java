package us.mattowens.concurrencyvisualizer.datacapture.future;

import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class FutureEvent extends Event {

	private FutureEventType eventType;
	
	public FutureEvent(String future, FutureEventType eventType) {
		super(future);
		
		this.eventType = eventType;
	}
	
	@Override
	public Map<String, Object> collapseToMap() {
		Map<String, Object> eventMap = super.collapseToMap();
		
		eventMap.put("EventType", eventType);

		
		return eventMap;
	}
}
