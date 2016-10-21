package us.mattowens.concurrencyvisualizer.datacapture.threadfactory;

import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class ThreadFactoryEvent extends Event {
	
	private ThreadFactoryEventType eventType;
	private String runnable;
	private String thread;
	
	public ThreadFactoryEvent(String factory, ThreadFactoryEventType eventType) {
		super(factory);
		
		this.eventType = eventType;
	}
	
	@Override
	public Map<String, Object> collapseToMap() {
		Map<String, Object> eventMap = super.collapseToMap();
		
		eventMap.put("EventType", eventType);
		eventMap.put("Runnable", runnable);
		eventMap.put("Thread", thread);
		return eventMap;
	}

	public ThreadFactoryEventType getEventType() {
		return eventType;
	}

	public void setEventType(ThreadFactoryEventType eventType) {
		this.eventType = eventType;
	}

	public String getRunnable() {
		return runnable;
	}

	public void setRunnable(String runnable) {
		this.runnable = runnable;
	}

	public String getThread() {
		return thread;
	}

	public void setThread(String thread) {
		this.thread = thread;
	}

	
}
