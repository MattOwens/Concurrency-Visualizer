package us.mattowens.concurrencyvisualizer.datacapture.runnable;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventType;
import us.mattowens.concurrencyvisualizer.datacapture.reentrantlock.ReentrantLockEventType;

public enum RunnableEventType implements EventType {
	Create(0),
	BeforeRun(1),
	AfterRun(2);

	private static final Map<Long, RunnableEventType> LOOKUP = new HashMap<Long, RunnableEventType>();
	private long code;
	
	private RunnableEventType(long code) {
		this.code = code;
	}
	
	static {
		for(RunnableEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static RunnableEventType fromCode(long code) {
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
