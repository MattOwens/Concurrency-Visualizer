package us.mattowens.concurrencyvisualizer.datacapture.countdownlatch;

import java.util.Map;
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
		
		this.eventType = eventType;
	}
	
	@Override
	public Map<String, Object> collapseToMap() {
		Map<String, Object> eventMap = super.collapseToMap();
		
		eventMap.put("EventType", eventType);
		eventMap.put("Count", count);
		eventMap.put("Successful", successful);
		eventMap.put("Timeout", timeout);
		eventMap.put("TimeUnit", timeUnit);
		
		return eventMap;
	}

	public CountDownLatchEventType getEventType() {
		return eventType;
	}

	public void setEventType(CountDownLatchEventType eventType) {
		this.eventType = eventType;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	
}
