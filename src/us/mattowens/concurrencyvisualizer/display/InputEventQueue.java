package us.mattowens.concurrencyvisualizer.display;

import java.util.concurrent.ConcurrentLinkedQueue;

import us.mattowens.concurrencyvisualizer.display.inputadapters.InputAdapter;

public class InputEventQueue {
	
	private static final InputEventQueue singletonEventQueue = new InputEventQueue();
	
	private ConcurrentLinkedQueue<DisplayEvent> eventQueue;
	private InputAdapter inputAdapter;
	
	private InputEventQueue() {
		eventQueue = new ConcurrentLinkedQueue<DisplayEvent>();
	}
	
	
	public static void setInputAdapter(InputAdapter inputAdapter) {
		singletonEventQueue.inputAdapter = inputAdapter;
	}
	
	public static void stopEventInput() {
		singletonEventQueue.inputAdapter.cleanup();
	}
	
	public static void addEvent(DisplayEvent event) {
		singletonEventQueue.eventQueue.add(event);
	}
	
	/***
	 * Gets the next DisplayEvent in the queue.
	 * @return Null if queue is empty, head of the queue otherwise
	 */
	public static DisplayEvent getNextEvent() {
		DisplayEvent nextEvent = singletonEventQueue.eventQueue.isEmpty() ? null : singletonEventQueue.eventQueue.remove();
		return nextEvent;
	}

}
