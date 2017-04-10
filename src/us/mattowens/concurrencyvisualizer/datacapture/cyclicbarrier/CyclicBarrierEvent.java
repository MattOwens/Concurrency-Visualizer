package us.mattowens.concurrencyvisualizer.datacapture.cyclicbarrier;

import java.util.concurrent.TimeUnit;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

@Deprecated
public class CyclicBarrierEvent extends Event {

	private CyclicBarrierEventType eventType;
	private int parties;
	private String runnable;
	private long timeout;
	private TimeUnit timeoutUnit;
	private int arrivalIndex;
	
	public CyclicBarrierEvent(String barrier, CyclicBarrierEventType eventType) {
		super(barrier);
		
		setEventType(eventType);
	}


	public CyclicBarrierEventType getEventType() {
		return eventType;
	}

	public void setEventType(CyclicBarrierEventType eventType) {
		this.eventType = eventType;
		eventMap.put("EventType", eventType.toString());
	}

	public int getParties() {
		return parties;
	}

	public void setParties(int parties) {
		this.parties = parties;
		eventMap.put("Parties", parties);
	}

	public String getRunnable() {
		return runnable;
	}

	public void setRunnable(String runnable) {
		this.runnable = runnable;
		eventMap.put("Runnable", runnable);
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
		eventMap.put("Timeout", timeout);
	}

	public TimeUnit getTimeoutUnit() {
		return timeoutUnit;
	}

	public void setTimeoutUnit(TimeUnit timeoutUnit) {
		this.timeoutUnit = timeoutUnit;
		eventMap.put("TimeoutUnit", timeoutUnit);
	}

	public int getArrivalIndex() {
		return arrivalIndex;
	}

	public void setArrivalIndex(int arrivalIndex) {
		this.arrivalIndex = arrivalIndex;
		eventMap.put("ArrivalIndex", arrivalIndex);
	}
	
	
}
