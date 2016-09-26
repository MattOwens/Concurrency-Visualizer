package us.mattowens.concurrencyvisualizer.datacapture;


public class Event  {
	
	private String threadName;
	private long threadId;
	private long timestamp;
	private String joinPointName;
	
	public Event() {
		//Measure time first to hopefully be more accurate
		long currentTime = System.nanoTime();
		
		Thread currentThread = Thread.currentThread();
		threadId = currentThread.getId();
		threadName = currentThread.getName();
		timestamp = currentTime;		
	}
	
	public String getThreadName() {
		return threadName;
	}
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
	public long getThreadId() {
		return threadId;
	}
	public void setThreadId(long threadId) {
		this.threadId = threadId;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long eventNanoTime) {
		this.timestamp = eventNanoTime - StartTime.SystemStartTime;
	}
	public String getJoinPointName() {
		return joinPointName;
	}
	public void setJoinPointName(String joinPointName) {
		this.joinPointName = joinPointName;
	}
	
	@Override
	public String toString() {
		return timestamp + "-" + threadId + "-" + threadName + "-" + joinPointName;
	}
}
