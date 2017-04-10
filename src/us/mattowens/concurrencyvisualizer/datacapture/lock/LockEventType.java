package us.mattowens.concurrencyvisualizer.datacapture.lock;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventType;
import us.mattowens.concurrencyvisualizer.datacapture.reentrantlock.ReentrantLockEventType;

public enum LockEventType implements EventType {
	BeforeLock(0),
	AfterLock(1),
	BeforeLockInterruptibly(2),
	AfterLockInterruptibly(3),
	NewCondition(4),
	BeforeTryLock(5),
	AfterTryLock(6),
	Unlock(7);
	
	private static final Map<Long, LockEventType> LOOKUP = new HashMap<Long, LockEventType>();
	private long code;
	
	private LockEventType(long code) {
		this.code = code;
	}
	
	static {
		for(LockEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static LockEventType fromCode(long code) {
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
