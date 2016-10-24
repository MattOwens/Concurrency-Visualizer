package us.mattowens.concurrencyvisualizer.datacapture.rejectedexecutionhandler;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class RejectedExecutionHandlerEvent extends Event {

	private RejectedExecutionHandlerEventType eventType;
	private String runnable;
	private String threadPoolExecutor;
	
	public RejectedExecutionHandlerEvent(String handler, 
			RejectedExecutionHandlerEventType eventType) {
		super(handler);
		
		setEventType(eventType);
	}

	public RejectedExecutionHandlerEventType getEventType() {
		return eventType;
	}

	public void setEventType(RejectedExecutionHandlerEventType eventType) {
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

	public String getThreadPoolExecutor() {
		return threadPoolExecutor;
	}

	public void setThreadPoolExecutor(String threadPoolExecutor) {
		this.threadPoolExecutor = threadPoolExecutor;
		eventMap.put("ThreadPoolExecutor", threadPoolExecutor);
	}
	
	
}
