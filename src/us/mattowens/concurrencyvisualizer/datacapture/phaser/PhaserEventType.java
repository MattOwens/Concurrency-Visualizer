package us.mattowens.concurrencyvisualizer.datacapture.phaser;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventType;

public enum PhaserEventType implements EventType {
	Create(0),
	Arrive(1),
	BeforeArriveAndAwait(2),
	AfterArriveAndAwait(3);
	
	private static final Map<Long, PhaserEventType> LOOKUP = new HashMap<Long, PhaserEventType>();
	private long code;
	
	private PhaserEventType(long code) {
		this.code = code;
	}
	
	static {
		for(PhaserEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static PhaserEventType fromCode(long code) {
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
