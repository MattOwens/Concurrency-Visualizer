package us.mattowens.concurrencyvisualizer.datacapture.rejectedexecutionhandler;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventType;

public enum RejectedExecutionHandlerEventType implements EventType {	
	RejectedExecution(0);
	
	private static final Map<Long, RejectedExecutionHandlerEventType> LOOKUP = new HashMap<Long, RejectedExecutionHandlerEventType>();
	private long code;
	
	private RejectedExecutionHandlerEventType(long code) {
		this.code = code;
	}
	
	static {
		for(RejectedExecutionHandlerEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static RejectedExecutionHandlerEventType fromCode(long code) {
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
