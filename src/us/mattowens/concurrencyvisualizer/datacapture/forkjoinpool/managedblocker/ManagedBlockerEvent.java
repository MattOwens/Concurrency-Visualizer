package us.mattowens.concurrencyvisualizer.datacapture.forkjoinpool.managedblocker;


import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class ManagedBlockerEvent extends Event {

	private ManagedBlockerEventType eventType;
	private boolean blockingUnnecessary;
	
	public ManagedBlockerEvent(String blocker, ManagedBlockerEventType eventType) {
		super(blocker);
		
		setEventType(eventType);
	}

	public ManagedBlockerEventType getEventType() {
		return eventType;
	}

	public void setEventType(ManagedBlockerEventType eventType) {
		this.eventType = eventType;
		eventMap.put("EventType", eventType);
	}

	public boolean isBlockingUnnecessary() {
		return blockingUnnecessary;
	}

	public void setBlockingUnnecessary(boolean blockingUnnecessary) {
		this.blockingUnnecessary = blockingUnnecessary;
		eventMap.put("BlockingUnnecessary", blockingUnnecessary);
	}
	
	
}
