package us.mattowens.concurrencyvisualizer.datacapture.semaphore;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class SemaphoreEvent extends Event {
	
	private SemaphoreEventType eventType;
	private int permits;
	private long timeout;
	private TimeUnit timeoutUnit;
	
	
	
	public SemaphoreEvent(String semaphoreDescription, SemaphoreEventType eventType) {
		super(semaphoreDescription);
		this.eventType = eventType;
	}
	
	@Override
	public String toString() {
		return super.toString() + "-" + eventType + "-" + permits + "-" + 
				timeout + "-" + timeoutUnit;
	}

	@Override
	public Map<String, Object> collapseToMap() {
		Map<String, Object> eventMap = super.collapseToMap();
		
		eventMap.put("EventType", eventType);
		eventMap.put("Permits", permits);
		eventMap.put("Timeout", timeout);
		eventMap.put("TimeoutUnit", timeoutUnit);
		
		return eventMap;
	}
	
	public SemaphoreEventType getEventType() {
		return eventType;
	}

	public int getPermits() {
		return permits;
	}

	public void setPermits(int permits) {
		this.permits = permits;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public TimeUnit getTimeoutUnit() {
		return timeoutUnit;
	}

	public void setTimeoutUnit(TimeUnit timeoutUnit) {
		this.timeoutUnit = timeoutUnit;
	}	
}
