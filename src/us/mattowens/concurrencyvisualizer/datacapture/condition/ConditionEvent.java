package us.mattowens.concurrencyvisualizer.datacapture.condition;

import us.mattowens.concurrencyvisualizer.datacapture.Event;
import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.util.Map;

public class ConditionEvent extends Event {

	private ConditionEventType eventType;
	private long time;
	private TimeUnit timeUnit;
	private long nanosTimeout;
	private Date deadline;
	private long timeRemaining;
	private boolean hasAccess;
	
	public ConditionEvent(String conditionDescription, ConditionEventType eventType) {
		super(conditionDescription);
		
		this.eventType = eventType;
	}
	
	@Override
	public Map<String, Object> collapseToMap() {
		Map<String, Object> eventMap = super.collapseToMap();
		
		eventMap.put("EventType", eventType);
		eventMap.put("Time", time);
		eventMap.put("TimeUnit", timeUnit);
		eventMap.put("NanosTimeout", nanosTimeout);
		eventMap.put("Deadline", deadline);
		eventMap.put("TimeRemaining", timeRemaining);
		eventMap.put("HasAccess", hasAccess);
		
		return eventMap;
	}

	public ConditionEventType getEventType() {
		return eventType;
	}

	public void setEventType(ConditionEventType eventType) {
		this.eventType = eventType;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	public long getNanosTimeout() {
		return nanosTimeout;
	}

	public void setNanosTimeout(long nanosTimeout) {
		this.nanosTimeout = nanosTimeout;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
	public void setTimeRemaining(long timeRemaining) {
		this.timeRemaining = timeRemaining;
	}
	
	public long getTimeRemaining() {
		return timeRemaining;
	}

	public boolean getHasAccess() {
		return hasAccess;
	}

	public void setHasAccess(boolean hasAccess) {
		this.hasAccess = hasAccess;
	}
	
	
	
}
