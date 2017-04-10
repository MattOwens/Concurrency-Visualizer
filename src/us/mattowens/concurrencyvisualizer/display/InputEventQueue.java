package us.mattowens.concurrencyvisualizer.display;

import java.util.concurrent.ConcurrentLinkedQueue;

import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.display.inputadapters.InputAdapter;

public class InputEventQueue {
	
	private static final InputEventQueue singletonEventQueue = new InputEventQueue();
	
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
		singletonEventQueue.eventQueue.add(event);
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
