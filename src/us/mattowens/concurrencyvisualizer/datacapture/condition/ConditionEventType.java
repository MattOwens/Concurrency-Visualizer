package us.mattowens.concurrencyvisualizer.datacapture.condition;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventType;

public enum ConditionEventType implements EventType {
	BeforeAwait(0),
	AfterAwait(1),
	BeforeAwaitUninterruptibly(2),
	AfterAwaitUninterruptibly(3),
	BeforeAwaitUntil(4),
	AfterAwaitUntil(5),
	Signal(6),
	SignalAll(7);
	
	private static final Map<Long, ConditionEventType> LOOKUP = new HashMap<Long, ConditionEventType>();
	private long code;
	
	private ConditionEventType(long code) {
		this.code = code;
	}
	
	static {
		for(ConditionEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static ConditionEventType fromCode(long code) {
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
