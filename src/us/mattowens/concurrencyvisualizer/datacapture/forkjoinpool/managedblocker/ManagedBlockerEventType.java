package us.mattowens.concurrencyvisualizer.datacapture.forkjoinpool.managedblocker;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventType;

public enum ManagedBlockerEventType implements EventType {
	BeforeBlock(0),
	AfterBlock(1),
	IsReleasable(2);
	
	private static final Map<Long, ManagedBlockerEventType> LOOKUP = new HashMap<Long, ManagedBlockerEventType>();
	private long code;
	
	private ManagedBlockerEventType(long code) {
		this.code = code;
	}
	
	static {
		for(ManagedBlockerEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static ManagedBlockerEventType fromCode(long code) {
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
