package us.mattowens.concurrencyvisualizer.datacapture.locksupport;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventType;

public enum LockSupportEventType implements EventType {
	BeforePark(0),
	AfterPark(1),
	BeforeParkUntil(2),
	AfterParkUntil(3),
	Unpark(4);

	private static final Map<Long, LockSupportEventType> LOOKUP = new HashMap<Long, LockSupportEventType>();
	private long code;
	
	private LockSupportEventType(long code) {
		this.code = code;
	}
	
	static {
		for(LockSupportEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static LockSupportEventType fromCode(long code) {
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
