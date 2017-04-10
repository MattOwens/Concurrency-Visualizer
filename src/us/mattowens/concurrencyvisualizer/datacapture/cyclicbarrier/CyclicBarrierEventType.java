package us.mattowens.concurrencyvisualizer.datacapture.cyclicbarrier;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventType;


public enum CyclicBarrierEventType implements EventType {
	Create(0),
	BeforeAwait(1),
	AfterAwait(2),
	Reset(3);
	
	private static final Map<Long, CyclicBarrierEventType> LOOKUP = new HashMap<Long, CyclicBarrierEventType>();
	private long code;
	
	private CyclicBarrierEventType(long code) {
		this.code = code;
	}
	
	static {
		for(CyclicBarrierEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static CyclicBarrierEventType fromCode(long code) {
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
