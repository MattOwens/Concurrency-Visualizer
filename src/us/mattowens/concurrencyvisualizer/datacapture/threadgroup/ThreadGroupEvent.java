package us.mattowens.concurrencyvisualizer.datacapture.threadgroup;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class ThreadGroupEvent extends Event {
	
	private int maxPriority;
	private boolean isDameon;
	private ThreadGroupEventType eventType;
	private String parentName;
	
	public ThreadGroupEvent(String name, ThreadGroupEventType eventType) {
		super(name);
	}

	public void setMaxPriority(int maxPriority) {
		this.maxPriority = maxPriority;
	}

	public void setDameon(boolean isDameon) {
		this.isDameon = isDameon;
	}

	public int getMaxPriority() {
		return maxPriority;
	}

	public boolean isDameon() {
		return isDameon;
	}

	public ThreadGroupEventType getEventType() {
		return eventType;
	}
	
	public void setParentName(String name) {
		parentName = name;
	}
	
	public String getParentName() {
		return parentName;
	}

	
	
	

}
