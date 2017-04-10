package us.mattowens.concurrencyvisualizer.datacapture.reentrantreadwritelock;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventType;

public enum ReentrantReadWriteLockEventType implements EventType {
	Create(0),
	GetReadLock(2),
	GetWriteLock(3),
	BeforeLockRead(4),
	AfterLockRead(5),
	BeforeLockWrite(6),
	AfterLockWrite(7),
	BeforeLockInterruptiblyRead(8),
	AfterLockInterruptiblyRead(9),
	BeforeLockInterruptiblyWrite(10),
	AfterLockInterruptiblyWrite(11),
	BeforeTryLockRead(12),
	AfterTryLockRead(13),
	BeforeTryLockWrite(14),
	AfterTryLockWrite(15),
	UnlockRead(16),
	UnlockWrite(17);
	
	private static final Map<Long, ReentrantReadWriteLockEventType> LOOKUP = new HashMap<Long, ReentrantReadWriteLockEventType>();
	private long code;
	
	private ReentrantReadWriteLockEventType(long code) {
		this.code = code;
	}
	
	static {
		for(ReentrantReadWriteLockEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static ReentrantReadWriteLockEventType fromCode(long code) {
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
