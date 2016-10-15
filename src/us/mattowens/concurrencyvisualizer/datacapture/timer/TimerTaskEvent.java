package us.mattowens.concurrencyvisualizer.datacapture.timer;

import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class TimerTaskEvent extends Event {
	
	private TimerTaskEventType eventType;
	
	public TimerTaskEvent(String task, TimerTaskEventType eventType) {
		super(task);
		
		this.eventType = eventType;
	}
	
	@Override
	public Map<String, Object> collapseToMap() {
		Map<String, Object> eventMap = super.collapseToMap();
		
		eventMap.put("EventType", eventType);
		
		return eventMap;
	}
	
	public void setEventType(TimerTaskEventType eventType) {
		this.eventType = eventType;
	}
	
	public TimerTaskEventType getEventType() {
		return eventType;
	}

}
