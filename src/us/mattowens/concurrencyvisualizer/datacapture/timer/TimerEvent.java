package us.mattowens.concurrencyvisualizer.datacapture.timer;

import us.mattowens.concurrencyvisualizer.datacapture.Event;
import java.util.Date;
import java.util.Map;

public class TimerEvent extends Event {
	private TimerEventType eventType;
	private Date startTime;
	private String taskDescription;
	private long delay;
	private long period;
	private boolean isDaemon;
	private int numPurged;
	
	public TimerEvent(String timer, TimerEventType type) {
		super(timer);
		eventType = type;
		
	}

	
	@Override
	public Map<String, Object> collapseToMap() {
		Map<String, Object> eventMap = super.collapseToMap();
		
		eventMap.put("EventType", eventType);
		eventMap.put("StartTime", startTime);
		eventMap.put("TaskDescription", taskDescription);
		eventMap.put("Delay", delay);
		eventMap.put("Period", period);
		eventMap.put("IsDaemon", isDaemon);
		eventMap.put("NumPurged", numPurged);
		
		return eventMap;
	}
	
	public TimerEventType getEventType() {
		return eventType;
	}

	public void setEventType(TimerEventType eventType) {
		this.eventType = eventType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		this.period = period;
	}

	public boolean isDaemon() {
		return isDaemon;
	}

	public void setDaemon(boolean isDaemon) {
		this.isDaemon = isDaemon;
	}

	public int getNumPurged() {
		return numPurged;
	}

	public void setNumPurged(int numPurged) {
		this.numPurged = numPurged;
	}
	
	

}
