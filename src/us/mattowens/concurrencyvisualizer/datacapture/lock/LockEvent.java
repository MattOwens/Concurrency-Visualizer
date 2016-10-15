package us.mattowens.concurrencyvisualizer.datacapture.lock;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class LockEvent extends Event {

	private LockEventType eventType;
	private String conditionDescription;
	private boolean hasAccess;
	private long timeout;
	private TimeUnit timeUnit;
	
	public LockEvent(String lockDescription, LockEventType eventType) {
		super(lockDescription);
		
		this.eventType = eventType;
	}
	
	@Override
	public Map<String, Object> collapseToMap() {
		Map<String, Object> eventMap = super.collapseToMap();
		
		eventMap.put("EventType", eventType);
		eventMap.put("ConditionDescription", conditionDescription);
		eventMap.put("HasAccess", hasAccess);
		eventMap.put("Timeout", timeout);
		eventMap.put("TimeUnit", timeUnit);

		
		return eventMap;
	}

	public LockEventType getEventType() {
		return eventType;
	}

	public void setEventType(LockEventType eventType) {
		this.eventType = eventType;
	}

	public String getConditionDescription() {
		return conditionDescription;
	}

	public void setConditionDescription(String conditionDescription) {
		this.conditionDescription = conditionDescription;
	}

	public boolean isHasAccess() {
		return hasAccess;
	}

	public void setHasAccess(boolean hasAccess) {
		this.hasAccess = hasAccess;
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
