package us.mattowens.concurrencyvisualizer.datacapture.exeutor;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class ExecutorEvent extends Event {

	private ExecutorEventType eventType;
	private String runnable;
	
	public ExecutorEvent(String executor, ExecutorEventType eventType) {
		super(executor);
		
		this.eventType = eventType;
	}

	public ExecutorEventType getEventType() {
		return eventType;
	}

	public void setEventType(ExecutorEventType eventType) {
		this.eventType = eventType;
	}

	public String getRunnable() {
		return runnable;
	}

	public void setRunnable(String runnable) {
		this.runnable = runnable;
	}
	
	
}
