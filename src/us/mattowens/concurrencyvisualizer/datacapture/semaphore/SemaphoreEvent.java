package us.mattowens.concurrencyvisualizer.datacapture.semaphore;

import java.util.concurrent.TimeUnit;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

@Deprecated
public class SemaphoreEvent extends Event {
	
	private SemaphoreEventType eventType;
	private int permits;
	private long timeout;
	private TimeUnit timeoutUnit;
	private boolean tryAcquireSuccessful;
	
	
	
	public SemaphoreEvent(String semaphoreDescription, SemaphoreEventType eventType) {
		super(semaphoreDescription);
		setEventType(eventType);
		setPermits(1);
	}
	
	public SemaphoreEventType getEventType() {
		return eventType;
	}
	
	public void setEventType(SemaphoreEventType eventType) {
		this.eventType = eventType;
		eventMap.put("EventType", eventType.toString());
	}

	public int getPermits() {
		return permits;
	}

	public void setPermits(int permits) {
		this.permits = permits;
		eventMap.put("Permits", permits);
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
		eventMap.put("Timeout", timeout);
	}

	public TimeUnit getTimeoutUnit() {
		return timeoutUnit;
	}

	public void setTimeoutUnit(TimeUnit timeoutUnit) {
		this.timeoutUnit = timeoutUnit;
		eventMap.put("TimeoutUnit", timeoutUnit);
	}

	public boolean isTryAcquireSuccessful() {
		return tryAcquireSuccessful;
	}

	public void setTryAcquireSuccessful(boolean tryAcquireSuccessful) {
		this.tryAcquireSuccessful = tryAcquireSuccessful;
		eventMap.put("TryAcquireSuccessful", tryAcquireSuccessful);
	}
}
