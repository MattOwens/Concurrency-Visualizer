package us.mattowens.concurrencyvisualizer.datacapture.rejectedexecutionhandler;

import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class RejectedExecutionHandlerEvent extends Event {

	private RejectedExecutionHandlerEventType eventType;
	private String runnable;
	private String threadPoolExecutor;
	
	public RejectedExecutionHandlerEvent(String handler, 
			RejectedExecutionHandlerEventType eventType) {
		super(handler);
		
		this.eventType = eventType;
	}
	
	@Override
	public Map<String, Object> collapseToMap() {
		Map<String, Object> eventMap = super.collapseToMap();
		
		eventMap.put("EventType", eventType);
		eventMap.put("Runnable", runnable);
		eventMap.put("ThreadPoolExecutor", threadPoolExecutor);
		
		return eventMap;
	}

	public RejectedExecutionHandlerEventType getEventType() {
		return eventType;
	}

	public void setEventType(RejectedExecutionHandlerEventType eventType) {
		this.eventType = eventType;
	}

	public String getRunnable() {
		return runnable;
	}

	public void setRunnable(String runnable) {
		this.runnable = runnable;
	}

	public String getThreadPoolExecutor() {
		return threadPoolExecutor;
	}

	public void setThreadPoolExecutor(String threadPoolExecutor) {
		this.threadPoolExecutor = threadPoolExecutor;
	}
	
	
}
