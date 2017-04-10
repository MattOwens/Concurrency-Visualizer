package us.mattowens.concurrencyvisualizer.datacapture.lock;

import java.util.concurrent.TimeUnit;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

@Deprecated
public class LockEvent extends Event {

	private LockEventType eventType;
	private String conditionDescription;
	private boolean hasAccess;
	private long timeout;
	private TimeUnit timeUnit;
	
	public LockEvent(String lockDescription, LockEventType eventType) {
		super(lockDescription);
		
		setEventType(eventType);
	}

	public LockEventType getEventType() {
		return eventType;
	}

	public void setEventType(LockEventType eventType) {
		this.eventType = eventType;
		eventMap.put("EventType", eventType.toString());
	}

	public String getConditionDescription() {
		return conditionDescription;
	}

	public void setConditionDescription(String conditionDescription) {
		this.conditionDescription = conditionDescription;
		eventMap.put("ConditionDescription", conditionDescription);
	}

	public boolean hasAccess() {
		return hasAccess;
	}

	public void setHasAccess(boolean hasAccess) {
		this.hasAccess = hasAccess;
		eventMap.put("HasAccess", hasAccess);
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
