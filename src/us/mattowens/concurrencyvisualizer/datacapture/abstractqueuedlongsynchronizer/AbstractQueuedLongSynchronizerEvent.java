package us.mattowens.concurrencyvisualizer.datacapture.abstractqueuedlongsynchronizer;


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
		
		setEventType(eventType);
	}
	

	public AbstractQueuedLongSynchronizerEventType getEventType() {
		return eventType;
	}


	public void setEventType(AbstractQueuedLongSynchronizerEventType eventType) {
		this.eventType = eventType;
		eventMap.put("EventType", eventType.toString());
	}


	public long getArg() {
		return arg;
	}


	public void setArg(long arg) {
		this.arg = arg;
		eventMap.put("Arg", arg);
	}


	public boolean isSuccessful() {
		return successful;
	}


	public void setSuccessful(boolean successful) {
		this.successful = successful;
		eventMap.put("Successful", successful);
	}


	public long getExpect() {
		return expect;
	}


	public void setExpect(long expect) {
		this.expect = expect;
		eventMap.put("Expect", expect);
	}


	public long getUpdate() {
		return update;
	}


	public void setUpdate(long update) {
		this.update = update;
		eventMap.put("Update", update);
	}


	public long getNewState() {
		return newState;
	}


	public void setNewState(long newState) {
		this.newState = newState;
		eventMap.put("NewState", newState);
	}


	public long getTryAcquireResult() {
		return tryAcquireResult;
	}


	public void setTryAcquireResult(long tryAcquireResult) {
		this.tryAcquireResult = tryAcquireResult;
		eventMap.put("TryAcquireResult", tryAcquireResult);
	}


	public long getNanosTimeout() {
		return nanosTimeout;
	}


	public void setNanosTimeout(long nanosTimeout) {
		this.nanosTimeout = nanosTimeout;
		eventMap.put("NanosTimeout", nanosTimeout);
	}
	
	
}
