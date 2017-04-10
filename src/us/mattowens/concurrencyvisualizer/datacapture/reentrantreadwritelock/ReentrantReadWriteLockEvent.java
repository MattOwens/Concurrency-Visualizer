package us.mattowens.concurrencyvisualizer.datacapture.reentrantreadwritelock;

import java.util.concurrent.TimeUnit;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

@Deprecated
public class ReentrantReadWriteLockEvent extends Event {

	private boolean fair;
	private ReentrantReadWriteLockEventType eventType;
	private String readLock;
	private String writeLock;
	private boolean hasLock;
	private long timeout;
	private TimeUnit timeUnit;
	
	public ReentrantReadWriteLockEvent(String objectDescription) {
		super(objectDescription);	
	}
	
	public void setFair(boolean fair) {
		this.fair = fair;
		eventMap.put("Fair", fair);
	}
	
	public void setEventType(ReentrantReadWriteLockEventType eventType) {
		this.eventType = eventType;
		eventMap.put("EventType", eventType);
	}
	
	public void setReadLock(String readLock) {
		this.readLock = readLock;
		eventMap.put("ReadLock", readLock);
	}
	
	public void setWriteLock(String writeLock) {
		this.writeLock = writeLock;
		eventMap.put("WriteLock", writeLock);
	}
	
	public void setHasLock(boolean hasLock) {
		this.hasLock = hasLock;
		eventMap.put("HasLock", hasLock);
	}
	
	public void setTimeout(long timeout) {
		this.timeout = timeout;
		eventMap.put("Timeout", timeout);
	}
	
	public void setTimeoutUnit(TimeUnit unit) {
		this.timeUnit = unit;
		eventMap.put("TimeoutUnit", unit);
	}

}
