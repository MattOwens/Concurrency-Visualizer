package us.mattowens.concurrencyvisualizer.datacapture.threadfactory;


import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class ThreadFactoryEvent extends Event {
	
	private ThreadFactoryEventType eventType;
	private String runnable;
	private String thread;
	
	public ThreadFactoryEvent(String factory, ThreadFactoryEventType eventType) {
		super(factory);
		
		setEventType(eventType);
	}

	public ThreadFactoryEventType getEventType() {
		return eventType;
	}

	public void setEventType(ThreadFactoryEventType eventType) {
		this.eventType = eventType;
		eventMap.put("EventType", eventType.toString());
	}

	public String getRunnable() {
		return runnable;
	}

	public void setRunnable(String runnable) {
		this.runnable = runnable;
		eventMap.put("Runnable", runnable);
	}

	public String getThread() {
		return thread;
	}

	public void setThread(String thread) {
		this.thread = thread;
		eventMap.put("Thread", thread);
	}

	
}
