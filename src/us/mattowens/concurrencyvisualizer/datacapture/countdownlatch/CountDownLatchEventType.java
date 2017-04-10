package us.mattowens.concurrencyvisualizer.datacapture.countdownlatch;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventType;

public enum CountDownLatchEventType implements EventType {
	Create(0),
	BeforeAwait(1),
	AfterAwait(2),
	CountDown(3);
	
	private static final Map<Long, CountDownLatchEventType> LOOKUP = 
			new HashMap<Long, CountDownLatchEventType>();
	private long code;
	
	private CountDownLatchEventType(long code) {
		this.code = code;
	}
	
	static {
		for(CountDownLatchEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static CountDownLatchEventType fromCode(long code) {
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
