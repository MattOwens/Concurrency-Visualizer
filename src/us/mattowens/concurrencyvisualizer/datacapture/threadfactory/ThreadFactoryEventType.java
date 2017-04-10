package us.mattowens.concurrencyvisualizer.datacapture.threadfactory;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventType;

public enum ThreadFactoryEventType implements EventType {
	NewThread(0);
	
	private static final Map<Long, ThreadFactoryEventType> LOOKUP = new HashMap<Long, ThreadFactoryEventType>();
	private long code;
	
	private ThreadFactoryEventType(long code) {
		this.code = code;
	}
	
	static {
		for(ThreadFactoryEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static ThreadFactoryEventType fromCode(long code) {
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
