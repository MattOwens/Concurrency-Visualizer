package us.mattowens.concurrencyvisualizer.datacapture;

import java.util.Map;
import java.util.LinkedHashMap;

public class Event  implements JSONSerializable {
	
	protected String threadName;
	protected long threadId;
	protected String objectDescription;
	protected long timestamp;
	protected String joinPointName;
	protected String className;
	protected Map<String, Object> eventMap;
	
	public Event(String objectDescription) {
		//Measure time first to hopefully be more accurate
		long currentTime = System.nanoTime();
		
		Thread currentThread = Thread.currentThread();
		threadId = currentThread.getId();
		threadName = currentThread.getName();
		this.setTimestamp(currentTime);		
		this.objectDescription = objectDescription;
		className = this.getClass().getName();
		className = className.substring(className.lastIndexOf(".")+1);
		setUpEventMap();
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
		eventMap.put("JoinPointName", joinPointName);
	}

	@Override
	public Map<String, Object> collapseToMap() {
		return eventMap;
	}
	
	private void setUpEventMap() {
		eventMap = new LinkedHashMap<String, Object>();

		eventMap.put("Timestamp", timestamp);
		eventMap.put("EventClass", className);
		eventMap.put("ThreadId", threadId);
		eventMap.put("ThreadName", threadName);
		eventMap.put("TargetDescription", objectDescription);
	}
}
