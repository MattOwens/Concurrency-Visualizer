package us.mattowens.concurrencyvisualizer.datacapture.reentrantlock;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
import us.mattowens.concurrencyvisualizer.datacapture.EventType;
import us.mattowens.concurrencyvisualizer.datacapture.lock.LockEventType;

public enum ReentrantLockEventType implements EventType{
	Create(0),
	IsLocked(1),
	BeforeLock(2),
	AfterLock(3),
	BeforeLockInterruptibly(4),
	AfterLockInterruptibly(5),
	NewCondition(6),
	BeforeTryLock(7),
	AfterTryLock(8),
	Unlock(9);

	private static final Map<Long, ReentrantLockEventType> LOOKUP = new HashMap<Long, ReentrantLockEventType>();
	private long code;
	
	private ReentrantLockEventType(long code) {
		this.code = code;
	}
	
	static {
		for(ReentrantLockEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static ReentrantLockEventType fromCode(long code) {
		return LOOKUP.get(code);
	}
	
	@Override
	public String getString() {
		return String.valueOf(this);
	}

	@Override
	public long getCode() {
		return this.code;
	}
}
