package us.mattowens.concurrencyvisualizer.datacapture.threadgroup;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class ThreadGroupEvent extends Event {
	
	private int maxPriority;
	private boolean isDaemon;
	private ThreadGroupEventType eventType;
	private String parentName;
	
	public ThreadGroupEvent(String name, ThreadGroupEventType eventType) {
		super(name);
		setEventType(eventType);
	}

	public void setEventType(ThreadGroupEventType eventType) {
		this.eventType = eventType;
		eventMap.put("EventType", eventType.toString());
	}
	public void setMaxPriority(int maxPriority) {
		this.maxPriority = maxPriority;
		eventMap.put("MaxPriority", maxPriority);
	}

	public void setDameon(boolean isDaemon) {
		this.isDaemon = isDaemon;
		eventMap.put("IsDaemon", isDaemon);
	}

	public int getMaxPriority() {
		return maxPriority;
	}

	public boolean isDameon() {
		return isDaemon;
	}

	public ThreadGroupEventType getEventType() {
		return eventType;
	}
	
	public void setParentName(String name) {
		parentName = name;
		eventMap.put("ParentName", parentName);
	}
	
	public String getParentName() {
		return parentName;
	}

	
	
	

}
