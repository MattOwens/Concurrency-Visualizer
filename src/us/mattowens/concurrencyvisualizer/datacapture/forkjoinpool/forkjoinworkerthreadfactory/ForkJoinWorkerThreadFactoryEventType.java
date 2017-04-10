package us.mattowens.concurrencyvisualizer.datacapture.forkjoinpool.forkjoinworkerthreadfactory;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventType;

public enum ForkJoinWorkerThreadFactoryEventType implements EventType {
	NewThread(0);
	
	private static final Map<Long, ForkJoinWorkerThreadFactoryEventType> LOOKUP = 
			new HashMap<Long, ForkJoinWorkerThreadFactoryEventType>();
	private long code;
	
	private ForkJoinWorkerThreadFactoryEventType(long code) {
		this.code = code;
	}
	
	static {
		for(ForkJoinWorkerThreadFactoryEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static ForkJoinWorkerThreadFactoryEventType fromCode(long code) {
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
