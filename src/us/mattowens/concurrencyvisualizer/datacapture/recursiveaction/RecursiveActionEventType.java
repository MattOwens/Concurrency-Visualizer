package us.mattowens.concurrencyvisualizer.datacapture.recursiveaction;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventType;

public enum RecursiveActionEventType implements EventType {
	Create(0),
	BeforeCompute(1),
	AfterCompute(2),
	BeforeExec(3),
	AfterExec(4);
	
	private static final Map<Long, RecursiveActionEventType> LOOKUP = new HashMap<Long, RecursiveActionEventType>();
	private long code;
	
	private RecursiveActionEventType(long code) {
		this.code = code;
	}
	
	static {
		for(RecursiveActionEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static RecursiveActionEventType fromCode(long code) {
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
