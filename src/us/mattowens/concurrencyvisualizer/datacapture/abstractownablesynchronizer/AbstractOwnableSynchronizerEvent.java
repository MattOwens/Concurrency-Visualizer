package us.mattowens.concurrencyvisualizer.datacapture.abstractownablesynchronizer;

import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class AbstractOwnableSynchronizerEvent extends Event {

	private AbstractOwnableSynchronizerEventType eventType;
	private String threadName;
	
	public AbstractOwnableSynchronizerEvent(String synchronizerDescription, 
			AbstractOwnableSynchronizerEventType eventType) {
		super(synchronizerDescription);
		this.eventType = eventType;
	}
	
	@Override
	public Map<String, Object> collapseToMap() {
		Map<String, Object> eventMap = super.collapseToMap();
		
		eventMap.put("EventType", eventType);
		eventMap.put("ThreadName", threadName);
		
		return eventMap;
	}

	public AbstractOwnableSynchronizerEventType getEventType() {
		return eventType;
	}

	public void setEventType(AbstractOwnableSynchronizerEventType eventType) {
		this.eventType = eventType;
	}
	
	public String getThreadName() {
		return threadName;
	}
	
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
	
	
}
