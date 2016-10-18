package us.mattowens.concurrencyvisualizer.datacapture.cyclicbarrier;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class CyclicBarrierEvent extends Event {

	private CyclicBarrierEventType eventType;
	private int parties;
	private String runnable;
	private long timeout;
	private TimeUnit timeoutUnit;
	private int arrivalIndex;
	
	public CyclicBarrierEvent(String barrier, CyclicBarrierEventType eventType) {
		super(barrier);
		
		this.eventType = eventType;
	}
	
	@Override
	public Map<String, Object> collapseToMap() {
		Map<String, Object> eventMap = super.collapseToMap();
		
		eventMap.put("EventType", eventType);
		eventMap.put("Timeout", timeout);
		eventMap.put("TimeUnit", timeoutUnit);
		eventMap.put("Parties", parties);
		eventMap.put("Runnable", runnable);
		eventMap.put("ArrivalIndex", arrivalIndex);
		
		return eventMap;
	}

	public CyclicBarrierEventType getEventType() {
		return eventType;
	}

	public void setEventType(CyclicBarrierEventType eventType) {
		this.eventType = eventType;
	}

	public int getParties() {
		return parties;
	}

	public void setParties(int parties) {
		this.parties = parties;
	}

	public String getRunnable() {
		return runnable;
	}

	public void setRunnable(String runnable) {
		this.runnable = runnable;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public TimeUnit getTimeoutUnit() {
		return timeoutUnit;
	}

	public void setTimeoutUnit(TimeUnit timeoutUnit) {
		this.timeoutUnit = timeoutUnit;
	}

	public int getArrivalIndex() {
		return arrivalIndex;
	}

	public void setArrivalIndex(int arrivalIndex) {
		this.arrivalIndex = arrivalIndex;
	}
	
	
}
