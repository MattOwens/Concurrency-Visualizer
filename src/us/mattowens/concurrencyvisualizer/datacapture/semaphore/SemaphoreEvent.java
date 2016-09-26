package us.mattowens.concurrencyvisualizer.datacapture.semaphore;

import java.util.concurrent.TimeUnit;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class SemaphoreEvent extends Event {
	
	private SemaphoreEventType eventType;
	private int permits;
	private long timeout;
	private TimeUnit timeoutUnit;
	
	
	
	public SemaphoreEvent(SemaphoreEventType eventType) {
		super();
		
		this.eventType = eventType;
	}
	
	@Override
	public String toString() {
		return super.toString() + "-" + eventType + "-" + permits + "-" + timeout + "-" + timeoutUnit;
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
