package us.mattowens.concurrencyvisualizer.display;

import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import us.mattowens.concurrencyvisualizer.ControlSignalEventType;
import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
import us.mattowens.concurrencyvisualizer.display.inputadapters.InputAdapter;

public class InputEventQueue {
	
	private static final InputEventQueue singletonEventQueue = new InputEventQueue();
	private static final HashMap<Long, String> threadNames = new HashMap<Long, String>();
	
	private ConcurrentLinkedQueue<Event> eventQueue;
	private InputAdapter inputAdapter;
	
	private InputEventQueue() {
		eventQueue = new ConcurrentLinkedQueue<Event>();
	}
	
	
	public static void setInputAdapter(InputAdapter inputAdapter) {
		singletonEventQueue.inputAdapter = inputAdapter;
	}
	
	public static void stopEventInput() {
		if(singletonEventQueue.inputAdapter != null) {
			singletonEventQueue.inputAdapter.cleanup();
		}
	}
	
	public static void addEvent(Event event) {
		if(event.getEventClass() == EventClass.ControlSignal) {
			handleControlSignal(event);
		} else {
			event.setThreadName(threadNames.get(event.getThreadId()));
			singletonEventQueue.eventQueue.add(event);
		}
	}
	
	private static void handleControlSignal(Event controlSignal) {
		//Well this is ugly
		if(controlSignal.getEventTypeLabel().equals(ControlSignalEventType.NewThread.getString())) {
			threadNames.put(controlSignal.getThreadId(), 
					(String)controlSignal.getValue(StringConstants.THREAD_NAME));
		}
	}
	
	/***
	 * Gets the next DisplayEvent in the queue.
	 * @return Null if queue is empty, head of the queue otherwise
	 */
	public static Event getNextEvent() {
		Event nextEvent = singletonEventQueue.eventQueue.isEmpty() ? null : singletonEventQueue.eventQueue.remove();
		return nextEvent;
	}

}
