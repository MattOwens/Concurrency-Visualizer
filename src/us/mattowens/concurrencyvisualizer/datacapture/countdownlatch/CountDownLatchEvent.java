package us.mattowens.concurrencyvisualizer.datacapture.countdownlatch;

import java.util.concurrent.TimeUnit;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class CountDownLatchEvent extends Event {
	
	private CountDownLatchEventType eventType;
	private int count;
	private boolean successful;
	private long timeout;
	private TimeUnit timeUnit;
	
	public CountDownLatchEvent(String latch, CountDownLatchEventType eventType) {
		super(latch);
		
		setEventType(eventType);
	}

	public CountDownLatchEventType getEventType() {
		return eventType;
	}

	public void setEventType(CountDownLatchEventType eventType) {
		this.eventType = eventType;
		eventMap.put("EventType", eventType.toString());
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
		eventMap.put("Count", count);
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
		eventMap.put("Successful", successful);
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
		eventMap.put("Timeout", timeout);
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
		eventMap.put("TimeUnit", timeUnit);
	}

	
}
