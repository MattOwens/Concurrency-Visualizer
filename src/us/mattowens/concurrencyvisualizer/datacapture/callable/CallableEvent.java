package us.mattowens.concurrencyvisualizer.datacapture.callable;


import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class CallableEvent extends Event {

	private CallableEventType eventType;
	private Object result;
	
	public CallableEvent(String callable, CallableEventType eventType) {
		super(callable);
		
		setEventType(eventType);
	}

	public CallableEventType getEventType() {
		return eventType;
	}

	public void setEventType(CallableEventType eventType) {
		this.eventType = eventType;
		eventMap.put("EventType", eventType.toString());
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
		eventMap.put("Result", result);
	}
	
	
	
}
