package us.mattowens.concurrencyvisualizer.datacapture;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;

public class Event  implements JSONSerializable {
	
	private String threadName;
	private long threadId;
	private String objectDescription;
	private long timestamp;
	private String joinPointName;
	private String className;
	
	public Event(String objectDescription) {
		//Measure time first to hopefully be more accurate
		long currentTime = System.nanoTime();
		
		Thread currentThread = Thread.currentThread();
		threadId = currentThread.getId();
		threadName = currentThread.getName();
		timestamp = currentTime;		
		this.objectDescription = objectDescription;
		className = this.getClass().getName();
		className = className.substring(className.lastIndexOf(".")+1);
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
		return timestamp + "-" + this.getClass().getName() + "-" + threadId + "-" + threadName 
				+ "-" + objectDescription + "-" + joinPointName;
	}

	@Override
	public Map<String, Object> collapseToMap() {
		Map<String, Object> eventMap = new LinkedHashMap<String, Object>();
		
		eventMap.put("Timestamp", timestamp);
		eventMap.put("EventClass", className);
		eventMap.put("ThreadId", threadId);
		eventMap.put("ThreadName", threadName);
		eventMap.put("TargetDescription", objectDescription);
		eventMap.put("JoinPointName", joinPointName);
		
		return eventMap;
	}
}
