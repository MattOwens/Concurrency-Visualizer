package us.mattowens.concurrencyvisualizer.datacapture.threadgroup;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventType;

public enum ThreadGroupEventType implements EventType {
	Create(0),
	Destroy(1),
	Interrupt(2),
	DaemonChange(3),
	PriorityChange(4);
	
	private static final Map<Long, ThreadGroupEventType> LOOKUP = new HashMap<Long, ThreadGroupEventType>();
	private long code;
	
	private ThreadGroupEventType(long code) {
		this.code = code;
	}
	
	static {
		for(ThreadGroupEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static ThreadGroupEventType fromCode(long code) {
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
