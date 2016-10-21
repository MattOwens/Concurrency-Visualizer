package us.mattowens.concurrencyvisualizer.datacapture.recursiveaction;

import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class RecursiveActionEvent extends Event {
	
	private RecursiveActionEventType eventType;
	private boolean completedNormally;
	
	public RecursiveActionEvent(String action, RecursiveActionEventType eventType) {
		super(action);
		
		this.eventType = eventType;
	}
	
	@Override
	public Map<String, Object> collapseToMap() {
		Map<String, Object> eventMap = super.collapseToMap();
		
		eventMap.put("EventType", eventType);
		eventMap.put("CompletedNormally", completedNormally);

		
		return eventMap;
	}

	public RecursiveActionEventType getEventType() {
		return eventType;
	}

	public void setEventType(RecursiveActionEventType eventType) {
		this.eventType = eventType;
	}

	public boolean isCompletedNormally() {
		return completedNormally;
	}

	public void setCompletedNormally(boolean completedNormally) {
		this.completedNormally = completedNormally;
	}
	
	

}
