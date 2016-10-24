package us.mattowens.concurrencyvisualizer.datacapture.condition;

import us.mattowens.concurrencyvisualizer.datacapture.Event;
import java.util.concurrent.TimeUnit;
import java.util.Date;

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
		
		setEventType(eventType);
	}

	public ConditionEventType getEventType() {
		return eventType;
	}

	public void setEventType(ConditionEventType eventType) {
		this.eventType = eventType;
		eventMap.put("EventType", eventType.toString());
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
		eventMap.put("Time", time);
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
		eventMap.put("TimeUnit", timeUnit);
	}

	public long getNanosTimeout() {
		return nanosTimeout;
	}

	public void setNanosTimeout(long nanosTimeout) {
		this.nanosTimeout = nanosTimeout;
		eventMap.put("NanosTimeout", nanosTimeout);
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
		eventMap.put("Deadline", deadline);
	}
	
	public void setTimeRemaining(long timeRemaining) {
		this.timeRemaining = timeRemaining;
		eventMap.put("TimeRemaining", timeRemaining);
	}
	
	public long getTimeRemaining() {
		return timeRemaining;
	}

	public boolean getHasAccess() {
		return hasAccess;
	}

	public void setHasAccess(boolean hasAccess) {
		this.hasAccess = hasAccess;
		eventMap.put("HasAccess", hasAccess);
	}
	
	
	
}
