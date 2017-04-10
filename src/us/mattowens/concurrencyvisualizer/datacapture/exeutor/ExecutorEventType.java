package us.mattowens.concurrencyvisualizer.datacapture.exeutor;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventType;

public enum ExecutorEventType implements EventType {
	BeforeExecute(0),
	AfterExecute(1);
	
	private static final Map<Long, ExecutorEventType> LOOKUP = new HashMap<Long, ExecutorEventType>();
	private long code;
	
	private ExecutorEventType(long code) {
		this.code = code;
	}
	
	static {
		for(ExecutorEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static ExecutorEventType fromCode(long code) {
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
