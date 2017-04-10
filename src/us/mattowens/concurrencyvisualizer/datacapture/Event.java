package us.mattowens.concurrencyvisualizer.datacapture;

import java.util.Map;

import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import us.mattowens.concurrencyvisualizer.Logging;
import us.mattowens.concurrencyvisualizer.StringConstants;

import java.util.LinkedHashMap;
import java.util.List;

public class Event  implements JSONSerializable {
	
	protected String threadName;
	protected long threadId;
	protected String target;
	protected long timestamp;
	protected String joinPointName;
	protected String className;
	protected EventClass eventClass;
	protected EventType eventType;
	protected Map<String, Object> eventMap;
	
	protected static JSONParser jsonParser = new JSONParser();
	protected static ContainerFactory containerFactory = new ContainerFactory() {
		@Override
		public List creatArrayContainer() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map createObjectContainer() {
			return new LinkedHashMap<String, Object>();
		}
	};
	
	/*
	 * This one is going to be deprecated as soon as I get rid of all the old subclasses
	 */
	public Event(String objectDescription) {
		//Measure time first to hopefully be more accurate
		long currentTime = System.nanoTime();
		
		Thread currentThread = Thread.currentThread();
		threadId = currentThread.getId();
		threadName = currentThread.getName();
		this.setTimestamp(currentTime);		
		this.target = objectDescription;
		className = this.getClass().getName();
		className = className.substring(className.lastIndexOf(".")+1);
		setUpEventMap();
	}
	
	/*
	 * For creating a new Event
	 */
	public Event(EventClass eventClass, EventType eventType, String target) {
		//Measure time first to hopefully be more accurate
		long currentTime = System.nanoTime();
		
		Thread currentThread = Thread.currentThread();
		threadId = currentThread.getId();
		this.setTimestamp(currentTime);		
		this.target = target;
		this.eventClass = eventClass;
		this.eventType = eventType;
		setUpEventMap();
	}
	
	/*
	 * For creating an Event stored as JSON
	 */
	@SuppressWarnings("unchecked")
	public Event(String jsonDescription, int becauseItCantBeTheSame) throws ParseException {
		try {
		eventMap = (LinkedHashMap<String, Object>)jsonParser.parse(jsonDescription, containerFactory);
		//Using remove here so we don't get duplicated when we iterate over the map later
		timestamp = (long) eventMap.remove(StringConstants.TIMESTAMP);
		threadId = (long) eventMap.remove(StringConstants.THREAD_ID);
		eventClass = EventClass.fromLong((long) eventMap.remove(StringConstants.EVENT_CLASS));
		joinPointName = (String) eventMap.remove(StringConstants.JOIN_POINT);
		target = (String) eventMap.remove(StringConstants.TARGET);
		eventType =  EventTypeDecoder.getEventType(eventClass, (long) eventMap.remove(StringConstants.EVENT_TYPE));  
		}
		catch (Exception e) {
			Logging.exception(e);
		}
	}
	
	private void setUpEventMap() {
		eventMap = new LinkedHashMap<String, Object>();

		eventMap.put(StringConstants.TIMESTAMP, timestamp);
		eventMap.put(StringConstants.EVENT_CLASS, eventClass.getCode());
		eventMap.put(StringConstants.THREAD_ID, threadId);
		eventMap.put(StringConstants.TARGET, target);
		eventMap.put(StringConstants.EVENT_TYPE, eventType.getCode());
	}
	
	public void addValue(String key, Object value) {
		eventMap.put(key, value);
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
		eventMap.put(StringConstants.JOIN_POINT, joinPointName);
	}
	
	public String getClassName() {
		return className;
	}
	
	public String getEventClass() {
		return String.valueOf(eventClass);
	}
	
	public String getEventTypeLabel() {
		return eventType.getString();
	}
	
	public String getTarget() {
		return target;
	}
	
	public Map<String, Object> getEventMap() {
		return eventMap;
	}

	@Override
	public Map<String, Object> collapseToMap() {
		return eventMap;
	}
	

}
