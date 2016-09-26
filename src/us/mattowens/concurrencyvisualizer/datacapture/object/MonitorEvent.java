package us.mattowens.concurrencyvisualizer.datacapture.object;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class MonitorEvent extends Event {
	MonitorEventType eventType;
    long timeout;
    int nanos;
    
    public MonitorEvent(MonitorEventType eventType) {
    	super();
    	
    	this.eventType = eventType;
    }

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public int getNanos() {
		return nanos;
	}

	public void setNanos(int nanos) {
		this.nanos = nanos;
	}
    
    public MonitorEventType getEventType() {
    	return eventType;
    }
	
}
