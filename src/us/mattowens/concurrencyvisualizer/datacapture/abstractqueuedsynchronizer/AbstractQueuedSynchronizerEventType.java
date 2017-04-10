package us.mattowens.concurrencyvisualizer.datacapture.abstractqueuedsynchronizer;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventType;


public enum AbstractQueuedSynchronizerEventType implements EventType {
	Create(0),
	BeforeAcquire(1),
	AfterAcquire(2),
	BeforeAcquireShared(3),
	AfterAcquireShared(4),
	CompareAndSetState(5),
	SetState(6),
	Release(7),
	ReleaseShared(8),
	BeforeTryAcquire(9),
	AfterTryAcquire(10),
	BeforeTryAcquireShared(11),
	AfterTryAcquireShared(12),
	TryRelease(13),
	TryReleaseShared(14);
	
	private static final Map<Long, AbstractQueuedSynchronizerEventType> LOOKUP = 
			new HashMap<Long, AbstractQueuedSynchronizerEventType>();
	private long code;
	
	private AbstractQueuedSynchronizerEventType(long code) {
		this.code = code;
	}
	
	static {
		for(AbstractQueuedSynchronizerEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static AbstractQueuedSynchronizerEventType fromCode(long code) {
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
