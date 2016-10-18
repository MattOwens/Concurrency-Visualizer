package us.mattowens.concurrencyvisualizer.datacapture.callable;

import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class CallableEvent extends Event {

	private CallableEventType eventType;
	private Object result;
	
	public CallableEvent(String callable, CallableEventType eventType) {
		super(callable);
		
		this.eventType = eventType;
	}
	
	@Override
	public Map<String, Object> collapseToMap() {
		Map<String, Object> eventMap = super.collapseToMap();
		
		eventMap.put("EventType", eventType);
		eventMap.put("Result", result);
		
		return eventMap;
	}

	public CallableEventType getEventType() {
		return eventType;
	}

	public void setEventType(CallableEventType eventType) {
		this.eventType = eventType;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
	
	
}
