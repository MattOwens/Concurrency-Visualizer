package us.mattowens.concurrencyvisualizer.datacapture;

import java.util.HashMap;
import java.util.Map;


public enum ControlSignalEventType implements EventType {
	NewThread(0);
	
	private static final Map<Long, ControlSignalEventType> LOOKUP = 
			new HashMap<Long, ControlSignalEventType>();
	private long code;
	
	private ControlSignalEventType(long code) {
		this.code = code;
	}
	
	static {
		for(ControlSignalEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static ControlSignalEventType fromCode(long code) {
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
