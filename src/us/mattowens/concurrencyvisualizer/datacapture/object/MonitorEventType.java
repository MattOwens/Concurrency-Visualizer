package us.mattowens.concurrencyvisualizer.datacapture.object;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventType;
import us.mattowens.concurrencyvisualizer.datacapture.reentrantlock.ReentrantLockEventType;

public enum MonitorEventType implements EventType {
	BeforeWait(0),
	AfterWait(1),
	Notify(2),
	NotifyAll(3),
	BeforeSynchronized(4),
	AfterSynchronized(5);

	private static final Map<Long, MonitorEventType> LOOKUP = new HashMap<Long, MonitorEventType>();
	private long code;
	
	private MonitorEventType(long code) {
		this.code = code;
	}
	
	static {
		for(MonitorEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static MonitorEventType fromCode(long code) {
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
