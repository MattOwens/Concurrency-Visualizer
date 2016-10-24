package us.mattowens.concurrencyvisualizer.datacapture.reentrantlock;

import java.util.concurrent.TimeUnit;
import us.mattowens.concurrencyvisualizer.datacapture.Event;


public class ReentrantLockEvent extends Event {

	private ReentrantLockEventType eventType;
	private boolean isFair;
	private boolean isLocked;
	private boolean hasLock;
	private long timeout;
	private TimeUnit unit;
	private String newCondition;
	
	public ReentrantLockEvent(String lockDescription, ReentrantLockEventType eventType) {
		super(lockDescription);
		setEventType(eventType);
	}

	public ReentrantLockEventType getEventType() {
		return eventType;
	}

	public void setEventType(ReentrantLockEventType eventType) {
		this.eventType = eventType;
		eventMap.put("EventType", eventType.toString());
	}

	public boolean isFair() {
		return isFair;
	}

	public void setFair(boolean isFair) {
		this.isFair = isFair;
		eventMap.put("IsFair", isFair);
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
		eventMap.put("IsLocked", isLocked);
	}

	public boolean hasLock() {
		return hasLock;
	}

	public void setHasLock(boolean hasLock) {
		this.hasLock = hasLock;
		eventMap.put("HasLock", hasLock);
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
		eventMap.put("Timeout", timeout);
	}

	public TimeUnit getUnit() {
		return unit;
	}

	public void setUnit(TimeUnit unit) {
		this.unit = unit;
		eventMap.put("TimeoutUnit", unit);
	}

	public String getNewCondition() {
		return newCondition;
	}

	public void setNewCondition(String newCondition) {
		this.newCondition = newCondition;
		eventMap.put("NewCondition", newCondition);
	}
	
	
}
