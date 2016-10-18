package us.mattowens.concurrencyvisualizer.datacapture.forkjoinpool.managedblocker;

import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class ManagedBlockerEvent extends Event {

	private ManagedBlockerEventType eventType;
	private boolean blockingUnnecessary;
	
	public ManagedBlockerEvent(String blocker, ManagedBlockerEventType eventType) {
		super(blocker);
		
		this.eventType = eventType;
	}
	
	@Override
	public Map<String, Object> collapseToMap() {
		Map<String, Object> eventMap = super.collapseToMap();
		
		eventMap.put("EventType", eventType);
		eventMap.put("BlockingUnnecessary", blockingUnnecessary);

		
		return eventMap;
	}

	public ManagedBlockerEventType getEventType() {
		return eventType;
	}

	public void setEventType(ManagedBlockerEventType eventType) {
		this.eventType = eventType;
	}

	public boolean isBlockingUnnecessary() {
		return blockingUnnecessary;
	}

	public void setBlockingUnnecessary(boolean blockingUnnecessary) {
		this.blockingUnnecessary = blockingUnnecessary;
	}
	
	
}
