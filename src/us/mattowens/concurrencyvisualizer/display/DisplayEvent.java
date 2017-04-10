package us.mattowens.concurrencyvisualizer.display;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import us.mattowens.concurrencyvisualizer.StringConstants;

@Deprecated
public class DisplayEvent {

	private static JSONParser jsonParser = new JSONParser();
	
	protected HashMap<String, Object> eventMap;
	protected long timestamp;
	protected String threadName;
	protected long threadId;
	protected String eventClass;
	protected String joinPointName;
	protected String targetDescription;
	protected String eventType;
	
	
	@SuppressWarnings("unchecked")
	public DisplayEvent(String jsonDescription) throws ParseException {
		//eventMap = (JSONObject) jsonParser.parse(jsonDescription);
		
		eventMap = (HashMap<String, Object>)jsonParser.parse(jsonDescription, new ContainerFactory() {

			@Override
			public List creatArrayContainer() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map createObjectContainer() {
				return new HashMap<String, Object>();
			}
			
		});
		
		//Using remove here so we don't get duplicated when we iterate over the map later
		timestamp = (long) eventMap.remove(StringConstants.TIMESTAMP);
		threadName = (String) eventMap.remove("ThreadName");
		threadId = (long) eventMap.remove(StringConstants.THREAD_ID);
		eventClass = (String) eventMap.remove(StringConstants.EVENT_CLASS);
		joinPointName = (String) eventMap.remove("JoinPointName");
		targetDescription = (String) eventMap.remove(StringConstants.TARGET);
		
		//Not part of the original Event class but every subclass should have it
		eventType =  (String) eventMap.remove("EventType");  
	}
	

	/*public JSONObject getEventMap() {
		return eventMap;
	}
	*/

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
