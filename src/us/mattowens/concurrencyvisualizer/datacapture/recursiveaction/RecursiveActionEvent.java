package us.mattowens.concurrencyvisualizer.datacapture.recursiveaction;


import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class RecursiveActionEvent extends Event {
	
	private RecursiveActionEventType eventType;
	private boolean completedNormally;
	
	public RecursiveActionEvent(String action, RecursiveActionEventType eventType) {
		super(action);
		
		setEventType(eventType);
	}

	public RecursiveActionEventType getEventType() {
		return eventType;
	}

	public void setEventType(RecursiveActionEventType eventType) {
		this.eventType = eventType;
		eventMap.put("EventType", eventType.toString());
	}

	public boolean isCompletedNormally() {
		return completedNormally;
	}

	public void setCompletedNormally(boolean completedNormally) {
		this.completedNormally = completedNormally;
		eventMap.put("CompletedNormally", completedNormally);
	}
	
	

}
