package us.mattowens.concurrencyvisualizer.datacapture.abstractqueuedsynchronizer;


import us.mattowens.concurrencyvisualizer.datacapture.Event;

@Deprecated
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

	public AbstractQueuedSynchronizerEventType getEventType() {
		return eventType;
	}


	public void setEventType(AbstractQueuedSynchronizerEventType eventType) {
		this.eventType = eventType;
		eventMap.put("EventType", eventType.toString());
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
		eventMap.put("Successful", successful);
	}


	public int getExpect() {
		return expect;
	}


	public void setExpect(int expect) {
		this.expect = expect;
		eventMap.put("Expect", expect);
	}


	public int getUpdate() {
		return update;
	}


	public void setUpdate(int update) {
		this.update = update;
		eventMap.put("Update", update);
	}


	public int getNewState() {
		return newState;
	}


	public void setNewState(int newState) {
		this.newState = newState;
		eventMap.put("NewState", newState);
	}


	public int getTryAcquireResult() {
		return tryAcquireResult;
	}


	public void setTryAcquireResult(int tryAcquireResult) {
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
