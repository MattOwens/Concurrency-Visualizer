package us.mattowens.concurrencyvisualizer.datacapture.exeutor;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class ExecutorEvent extends Event {

	private ExecutorEventType eventType;
	private String runnable;
	
	public ExecutorEvent(String executor, ExecutorEventType eventType) {
		super(executor);
		
		setEventType(eventType);
	}

	public ExecutorEventType getEventType() {
		return eventType;
	}

	public void setEventType(ExecutorEventType eventType) {
		this.eventType = eventType;
		eventMap.put("EventType", eventType);
	}

	public String getRunnable() {
		return runnable;
	}

	public void setRunnable(String runnable) {
		this.runnable = runnable;
		eventMap.put("Runnable", runnable);
	}
	
	
}
