package us.mattowens.concurrencyvisualizer.datacapture.thread;


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
		setEventType(eventType);
	}

	public ThreadEventType getEventType() {
		return eventType;
	}

	public void setEventType(ThreadEventType eventType) {
		this.eventType = eventType;
		eventMap.put("EventType", this.eventType.toString());
	}

	public void setNewPriority(int newPriority) {
		this.newPriority = newPriority;
		eventMap.put("NewPriority", this.newPriority);
	}


	public void setMillis(long sleepMillis) {
		this.millis = sleepMillis;
		eventMap.put("SleepMillis", millis);
	}

	public void setNanos(int sleepNanos) {
		this.nanos = sleepNanos;
		eventMap.put("SleepNanos", nanos);
	}
	
	public int getNanos() {
		return nanos;
	}
	
	public void setNewName(String newName) {
		this.newName = newName;
		eventMap.put("NewName", this.newName);
	}
	
	public String getNewName() {
		return newName;
	}
	
	public void setRunnable(String runnable) {
		this.runnable = runnable;
		eventMap.put("Runnable", this.runnable);
	}

	public void setThreadGroup(String threadGroup) {
		this.threadGroup = threadGroup;
		eventMap.put("ThreadGroup", this.threadGroup);
	}
	
	public void setIsDaemon(boolean on) {
		this.isDaemon = on;
		eventMap.put("IsDaemon", on);
	}
	
	public boolean getIsDaemon() {
		return isDaemon;
	}
	
	public void setIsInterrupted(boolean interrupted) {
		this.isInterrupted = interrupted;
		eventMap.put("Interrupted", isInterrupted);
	}
	
	public boolean getIsInterrupted() {
		return isInterrupted;
	}
}
