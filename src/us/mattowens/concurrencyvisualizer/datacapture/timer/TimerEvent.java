package us.mattowens.concurrencyvisualizer.datacapture.timer;

import us.mattowens.concurrencyvisualizer.datacapture.Event;
import java.util.Date;

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
		setEventType(type);
		
	}
	
	public TimerEventType getEventType() {
		return eventType;
	}

	public void setEventType(TimerEventType eventType) {
		this.eventType = eventType;
		eventMap.put("EventType", eventType.toString());
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
		eventMap.put("StartTime", startTime);
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
		eventMap.put("TaskDescription", taskDescription);
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
		eventMap.put("Delay", delay);
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		this.period = period;
		eventMap.put("Period", period);
	}

	public boolean isDaemon() {
		return isDaemon;
	}

	public void setDaemon(boolean isDaemon) {
		this.isDaemon = isDaemon;
		eventMap.put("IsDaemon", isDaemon);
	}

	public int getNumPurged() {
		return numPurged;
	}

	public void setNumPurged(int numPurged) {
		this.numPurged = numPurged;
		eventMap.put("NumPurged", numPurged);
	}
}
