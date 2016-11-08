package us.mattowens.concurrencyvisualizer.display;

import java.util.ArrayList;
import java.util.Iterator;

public class ThreadDisplayPanel {

	private int leftBound, rightBound;
	private long threadId;
	private String threadName, displayName;
	private ArrayList<DisplayEvent> events;

	public ThreadDisplayPanel(long threadId, String threadName) {
		this.threadId = threadId;
		this.threadName = threadName;
		displayName = String.format("Thread %d: %s", threadId, threadName);
		events = new ArrayList<DisplayEvent>();
	}
	
	public long getId() {
		return threadId;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public String getName() {
		return threadName;
	}
	
	public void addEvent(DisplayEvent nextEvent) {
		events.add(nextEvent);
	}
	
	public void setBounds(int left, int right) {
		leftBound = left;
		rightBound = right;
	}
	
	public int getLeftBound() {
		return leftBound;
	}
	
	public int getRightBound() {
		return rightBound;
	}
	
	/**
	 * Returns the timestamp of the most recent event in this thread. Relies
	 * on the assumption that events are added in the order they occurred.
	 * @return Timestamp of the most recent event occurring in this thread
	 */
	public long getMostRecentTimestamp() {
		if(events.size() == 0) {
			throw new IllegalStateException("ThreadPanel contains no events");
		}
		return events.get(events.size()-1).getTimestamp();
	}
	
	public long getFirstTimeStamp() {
		if(events.size() == 0) {
			throw new IllegalStateException("ThreadPanel contains no events");
		}
		return events.get(0).getTimestamp();
	}
	
	public Iterator<DisplayEvent> getEventsIterator() {
		return events.listIterator();
	}
	
	public int getMidPoint() {
		return (leftBound+rightBound)/2;
	}
}
