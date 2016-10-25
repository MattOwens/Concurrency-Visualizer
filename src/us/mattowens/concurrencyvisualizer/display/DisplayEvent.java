package us.mattowens.concurrencyvisualizer.display;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DisplayEvent {

	private static JSONParser jsonParser = new JSONParser();
	
	protected JSONObject eventMap;
	protected long timestamp;
	protected String threadName;
	protected long threadId;
	protected String eventClass;
	protected String joinPointName;
	protected String targetDescription;
	protected String eventType;
	
	
	public DisplayEvent(String jsonDescription) throws ParseException {
		eventMap = (JSONObject) jsonParser.parse(jsonDescription);
		
		//Using remove here so we don't get duplicated when we iterate over the map later
		timestamp = (long) eventMap.remove("Timestamp");
		threadName = (String) eventMap.remove("ThreadName");
		threadId = (long) eventMap.remove("ThreadId");
		eventClass = (String) eventMap.remove("EventClass");
		joinPointName = (String) eventMap.remove("JoinPointName");
		targetDescription = (String) eventMap.remove("TargetDescription");
		
		//Not part of the original Event class but every subclass should have it
		eventType =  (String) eventMap.remove("EventType");  
	}
	

	public JSONObject getEventMap() {
		return eventMap;
	}


	public long getTimestamp() {
		return timestamp;
	}


	public String getThreadName() {
		return threadName;
	}


	public long getThreadId() {
		return threadId;
	}


	public String getEventClass() {
		return eventClass;
	}


	public String getJoinPointName() {
		return joinPointName;
	}


	public String getTargetDescription() {
		return targetDescription;
	}


	public String getEventType() {
		return eventType;
	}
	
	
}
