package us.mattowens.concurrencyvisualizer.datacapture.timer;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class TimerTaskEvent extends Event {
	
	private TimerTaskEventType eventType;
	
	public TimerTaskEvent(String task, TimerTaskEventType eventType) {
		super(task);
		
		setEventType(eventType);
	}

	
	public void setEventType(TimerTaskEventType eventType) {
		this.eventType = eventType;
		eventMap.put("EventType", eventType.toString());
	}
	
	public TimerTaskEventType getEventType() {
		return eventType;
	}

}
