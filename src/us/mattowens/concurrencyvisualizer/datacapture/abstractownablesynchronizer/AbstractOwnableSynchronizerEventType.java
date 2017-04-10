package us.mattowens.concurrencyvisualizer.datacapture.abstractownablesynchronizer;

import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.EventType;

public enum AbstractOwnableSynchronizerEventType implements EventType {
	Create(0),
	GetOwnerThread(1),
	SetOwnerThread(2);
	
	private static final Map<Long, AbstractOwnableSynchronizerEventType> LOOKUP = 
			new HashMap<Long, AbstractOwnableSynchronizerEventType>();
	private long code;
	
	private AbstractOwnableSynchronizerEventType(long code) {
		this.code = code;
	}
	
	static {
		for(AbstractOwnableSynchronizerEventType c : values()) {
			LOOKUP.put(c.code, c);
		}
	}
	
	public static AbstractOwnableSynchronizerEventType fromCode(long code) {
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
