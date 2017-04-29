package us.mattowens.concurrencyvisualizer.sampleprograms.ForkJoinPool;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventType;

public enum ForkJoinPoolEventType implements EventType {
	Event(0);
	
	private static final Map<Long, ForkJoinPoolEventType> LOOKUP = new HashMap<Long, ForkJoinPoolEventType>();
	private long code;
	
	private ForkJoinPoolEventType(long code) {
		this.code = code;
	}
	
	static {
		for(ForkJoinPoolEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static ForkJoinPoolEventType fromCode(long code) {
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
