package us.mattowens.concurrencyvisualizer.datacapture.object;

import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class MonitorEvent extends Event {
	private MonitorEventType eventType;
    private long timeout;
    private int nanos;
    
    public MonitorEvent(String objectDescription, MonitorEventType eventType) {
    	super(objectDescription);
    	
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
    
    public String toString() {
    	return super.toString() + "-" + eventType + "-" + timeout + "-" + nanos;
    }
    
    @Override
    public Map<String, Object> collapseToMap() {
    	Map<String, Object> eventMap = super.collapseToMap();
    	
    	eventMap.put("EventType", eventType);
    	eventMap.put("TimeoutMillis", timeout);
    	eventMap.put("TimeoutNanos", nanos);
    	
    	return eventMap;
    }
	
}
