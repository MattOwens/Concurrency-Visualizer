package us.mattowens.concurrencyvisualizer.datacapture.object;


import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class MonitorEvent extends Event {
	private MonitorEventType eventType;
    private long timeout;
    private int nanos;
    
    public MonitorEvent(String objectDescription, MonitorEventType eventType) {
    	super(objectDescription);
    	
    	setEventType(eventType);
    }

    public MonitorEventType getEventType() {
    	return eventType;
    }
    
    public void setEventType(MonitorEventType eventType) {
    	this.eventType = eventType;
    	eventMap.put("EventType", eventType);
    }
	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
		eventMap.put("TimeoutMillis", timeout);
	}

	public int getNanos() {
		return nanos;
	}

	public void setNanos(int nanos) {
		this.nanos = nanos;
		eventMap.put("TimeoutNanos", nanos);
	}
}
