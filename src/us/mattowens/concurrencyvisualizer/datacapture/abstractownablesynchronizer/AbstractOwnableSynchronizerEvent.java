package us.mattowens.concurrencyvisualizer.datacapture.abstractownablesynchronizer;


import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class AbstractOwnableSynchronizerEvent extends Event {

	private AbstractOwnableSynchronizerEventType eventType;
	private String threadName;
	
	public AbstractOwnableSynchronizerEvent(String synchronizerDescription, 
			AbstractOwnableSynchronizerEventType eventType) {
		super(synchronizerDescription);
		setEventType(eventType);
	}

	public AbstractOwnableSynchronizerEventType getEventType() {
		return eventType;
	}

	public void setEventType(AbstractOwnableSynchronizerEventType eventType) {
		this.eventType = eventType;
		eventMap.put("EventType", eventType);
	}
	
	public String getThreadName() {
		return threadName;
	}
	
	public void setThreadName(String threadName) {
		this.threadName = threadName;
		eventMap.put("ThreadName", threadName);
	}
	
	
}
