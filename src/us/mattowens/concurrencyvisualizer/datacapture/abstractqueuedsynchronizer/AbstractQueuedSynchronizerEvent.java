package us.mattowens.concurrencyvisualizer.datacapture.abstractqueuedsynchronizer;

import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class AbstractQueuedSynchronizerEvent extends Event {

	private AbstractQueuedSynchronizerEventType eventType;
	private int arg;
	private boolean successful;
	private int expect;
	private int update;
	private int newState;
	private int tryAcquireResult;
	private long nanosTimeout;
	
	
	public AbstractQueuedSynchronizerEvent(String synchronizer, AbstractQueuedSynchronizerEventType eventType) {
		super(synchronizer);
		
		this.eventType = eventType;
	}

	@Override
	public Map<String, Object> collapseToMap() {
		Map<String, Object> eventMap = super.collapseToMap();
		
		eventMap.put("EventType", eventType);
		eventMap.put("Arg", arg);
		eventMap.put("Successful", successful);
		eventMap.put("Expect", expect);
		eventMap.put("Update", update);
		eventMap.put("NewState", newState);
		eventMap.put("TryAcquireResult", tryAcquireResult);
		eventMap.put("NanosTimeout", nanosTimeout);
		
		return eventMap;
	}
	

	public AbstractQueuedSynchronizerEventType getEventType() {
		return eventType;
	}


	public void setEventType(AbstractQueuedSynchronizerEventType eventType) {
		this.eventType = eventType;
	}


	public int getArg() {
		return arg;
	}


	public void setArg(int arg) {
		this.arg = arg;
	}


	public boolean isSuccessful() {
		return successful;
	}


	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}


	public int getExpect() {
		return expect;
	}


	public void setExpect(int expect) {
		this.expect = expect;
	}


	public int getUpdate() {
		return update;
	}


	public void setUpdate(int update) {
		this.update = update;
	}


	public int getNewState() {
		return newState;
	}


	public void setNewState(int newState) {
		this.newState = newState;
	}


	public int getTryAcquireResult() {
		return tryAcquireResult;
	}


	public void setTryAcquireResult(int tryAcquireResult) {
		this.tryAcquireResult = tryAcquireResult;
	}


	public long getNanosTimeout() {
		return nanosTimeout;
	}


	public void setNanosTimeout(long nanosTimeout) {
		this.nanosTimeout = nanosTimeout;
	}
	
	
}
