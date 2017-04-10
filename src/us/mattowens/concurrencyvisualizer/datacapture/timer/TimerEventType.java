package us.mattowens.concurrencyvisualizer.datacapture.timer;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventType;

public enum TimerEventType implements EventType {
	Create(0),
	Cancel(1),
	Purge(2),
	Schedule(3),
	ScheduleAtFixedRate(4);
	
	private static final Map<Long, TimerEventType> LOOKUP = new HashMap<Long, TimerEventType>();
	private long code;
	
	private TimerEventType(long code) {
		this.code = code;
	}
	
	static {
		for(TimerEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static TimerEventType fromCode(long code) {
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
