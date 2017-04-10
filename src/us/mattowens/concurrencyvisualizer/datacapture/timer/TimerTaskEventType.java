package us.mattowens.concurrencyvisualizer.datacapture.timer;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventType;

public enum TimerTaskEventType implements EventType {
	Create(0),
	Cancel(1),
	Run(2);
	
	private static final Map<Long, TimerTaskEventType> LOOKUP = new HashMap<Long, TimerTaskEventType>();
	private long code;
	
	private TimerTaskEventType(long code) {
		this.code = code;
	}
	
	static {
		for(TimerTaskEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static TimerTaskEventType fromCode(long code) {
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
