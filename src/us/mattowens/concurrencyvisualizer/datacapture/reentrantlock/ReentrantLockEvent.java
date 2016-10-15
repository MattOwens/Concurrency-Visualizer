package us.mattowens.concurrencyvisualizer.datacapture.reentrantlock;

import java.util.Map;
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
		this.eventType = eventType;
	}
	
	@Override
	public Map<String, Object> collapseToMap() {
		Map<String, Object> eventMap = super.collapseToMap();
		
		eventMap.put("EventType", eventType);
		eventMap.put("IsFair", isFair);
		eventMap.put("IsLocked", isLocked);
		eventMap.put("HasLock", hasLock);
		eventMap.put("Timeout", timeout);
		eventMap.put("TimeUnit", unit);
		eventMap.put("NewCondition", newCondition);
		
		return eventMap;
	}

	public ReentrantLockEventType getEventType() {
		return eventType;
	}

	public void setEventType(ReentrantLockEventType eventType) {
		this.eventType = eventType;
	}

	public boolean isFair() {
		return isFair;
	}

	public void setFair(boolean isFair) {
		this.isFair = isFair;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public boolean isHasLock() {
		return hasLock;
	}

	public void setHasLock(boolean hasLock) {
		this.hasLock = hasLock;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public TimeUnit getUnit() {
		return unit;
	}

	public void setUnit(TimeUnit unit) {
		this.unit = unit;
	}

	public String getNewCondition() {
		return newCondition;
	}

	public void setNewCondition(String newCondition) {
		this.newCondition = newCondition;
	}
	
	
}
