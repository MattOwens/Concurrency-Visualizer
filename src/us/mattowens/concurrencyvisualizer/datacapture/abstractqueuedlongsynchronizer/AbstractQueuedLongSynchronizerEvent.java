package us.mattowens.concurrencyvisualizer.datacapture.abstractqueuedlongsynchronizer;

import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class AbstractQueuedLongSynchronizerEvent extends Event {

	private AbstractQueuedLongSynchronizerEventType eventType;
	private long arg;
	private boolean successful;
	private long expect;
	private long update;
	private long newState;
	private long tryAcquireResult;
	private long nanosTimeout;
	
	
	public AbstractQueuedLongSynchronizerEvent(String synchronizer, AbstractQueuedLongSynchronizerEventType eventType) {
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
	

	public AbstractQueuedLongSynchronizerEventType getEventType() {
		return eventType;
	}


	public void setEventType(AbstractQueuedLongSynchronizerEventType eventType) {
		this.eventType = eventType;
	}


	public long getArg() {
		return arg;
	}


	public void setArg(long arg) {
		this.arg = arg;
	}


	public boolean isSuccessful() {
		return successful;
	}


	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}


	public long getExpect() {
		return expect;
	}


	public void setExpect(long expect) {
		this.expect = expect;
	}


	public long getUpdate() {
		return update;
	}


	public void setUpdate(long update) {
		this.update = update;
	}


	public long getNewState() {
		return newState;
	}


	public void setNewState(long newState) {
		this.newState = newState;
	}


	public long getTryAcquireResult() {
		return tryAcquireResult;
	}


	public void setTryAcquireResult(long tryAcquireResult) {
		this.tryAcquireResult = tryAcquireResult;
	}


	public long getNanosTimeout() {
		return nanosTimeout;
	}


	public void setNanosTimeout(long nanosTimeout) {
		this.nanosTimeout = nanosTimeout;
	}
	
	
}
