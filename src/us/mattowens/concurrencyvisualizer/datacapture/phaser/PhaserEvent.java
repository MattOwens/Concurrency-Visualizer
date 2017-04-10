package us.mattowens.concurrencyvisualizer.datacapture.phaser;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class PhaserEvent extends Event {
	
	private PhaserEventType eventType;
	private int parties;
	private String parent;

	public PhaserEvent(String objectDescription) {
		super(objectDescription);
		
	}
	
	public void setEventType(PhaserEventType eventType) {
		this.eventType = eventType;
		eventMap.put("EventType", eventType);
	}
	
	public void setParties(int parties) {
		this.parties = parties;
		eventMap.put("Parties", parties);
	}

	public void setParent(String parent) {
		this.parent = parent;
		eventMap.put("Parent", parent);
	}
}
