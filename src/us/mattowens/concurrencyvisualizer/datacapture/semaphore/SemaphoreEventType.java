package us.mattowens.concurrencyvisualizer.datacapture.semaphore;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventType;
import us.mattowens.concurrencyvisualizer.datacapture.reentrantlock.ReentrantLockEventType;

public enum SemaphoreEventType implements EventType {
	Create(0),
	Release(1),
	BeforeAcquire(2),
	AfterAcquire(3),
	BeforeAcquireTimeout(4),
	AfterAcquireTimeout(5),
	BeforeTryAcquire(6),
	AfterTryAcquire(7),
	PermitReduction(8),
	DrainPermits(9);

	private static final Map<Long, SemaphoreEventType> LOOKUP = new HashMap<Long, SemaphoreEventType>();
	private long code;
	
	private SemaphoreEventType(long code) {
		this.code = code;
	}
	
	static {
		for(SemaphoreEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static SemaphoreEventType fromCode(long code) {
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
