package us.mattowens.concurrencyvisualizer.datacapture.thread;

import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class ThreadEvent extends Event {

	private ThreadEventType eventType;
	private String newName;
	private String runnable;
    private String threadGroup;
	private int newPriority;
	private long millis;
	private int nanos;
	private boolean isDaemon;
	private boolean isInterrupted;
	
	public ThreadEvent(String threadDescription, ThreadEventType eventType) {
		super(threadDescription);
		this.eventType = eventType;
	}
	
	@Override
	public Map<String, Object> collapseToMap() {
		Map<String, Object> eventMap = super.collapseToMap();
		
		eventMap.put("EventType", eventType);
		eventMap.put("NewName", newName);
		eventMap.put("Runnable", runnable);
		eventMap.put("ThreadGroup", threadGroup);
		eventMap.put("NewPriority", newPriority);
		eventMap.put("Millis", millis);
		eventMap.put("Nanos", nanos);
		eventMap.put("IsDaemon", isDaemon);
		eventMap.put("IsInterrupted", isInterrupted);
		
		return eventMap;
	}
	
	
	public ThreadEventType getEventType() {
		return eventType;
	}


	public void setEventType(ThreadEventType eventType) {
		this.eventType = eventType;
	}


	public void setNewPriority(int newPriority) {
		this.newPriority = newPriority;
	}


	public void setMillis(long sleepMillis) {
		this.millis = sleepMillis;
	}


	public void setNanos(int sleepNanos) {
		this.nanos = sleepNanos;
	}
	
	public int getNanos() {
		return nanos;
	}
	
	public void setNewName(String newName) {
		this.newName = newName;
	}
	
	public String getNewName() {
		return newName;
	}
	
	public void setRunnable(String runnable) {
		this.runnable = runnable;
	}


	public void setThreadGroup(String threadGroup) {
		this.threadGroup = threadGroup;
	}
	
	public void setIsDaemon(boolean on) {
		this.isDaemon = on;
	}
	
	public boolean getIsDaemon() {
		return isDaemon;
	}
	
	public void setIsInterrupted(boolean interrupted) {
		this.isInterrupted = interrupted;
	}
	
	public boolean getIsInterrupted() {
		return isInterrupted;
	}
	
	@Override
	public String toString() {
		return super.toString() + "-" + eventType + "-" + newName + "-" +
				newPriority + "-" + runnable + "-" + threadGroup + "-" + 
				millis + "-" + nanos + "-" + isDaemon + "-" + isInterrupted;
	}
}
