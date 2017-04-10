package us.mattowens.concurrencyvisualizer.datacapture.thread;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventType;
import us.mattowens.concurrencyvisualizer.datacapture.reentrantlock.ReentrantLockEventType;

public enum ThreadEventType implements EventType {
	Create(0),
	Interrupt(1),
	Interrupted(2),
	IsInterrupted(3),
	BeforeJoin(4),
	AfterJoin(5),
	PriorityChange(6),
	DaemonChange(7),
	NameChange(8),
	BeforeSleep(9),
	AfterSleep(10),
	Start(11),
	Yield(12);

	private static final Map<Long, ThreadEventType> LOOKUP = new HashMap<Long, ThreadEventType>();
	private long code;
	
	private ThreadEventType(long code) {
		this.code = code;
	}
	
	static {
		for(ThreadEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static ThreadEventType fromCode(long code) {
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
