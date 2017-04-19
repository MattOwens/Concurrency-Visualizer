package us.mattowens.concurrencyvisualizer.display;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import us.mattowens.concurrencyvisualizer.Logging;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.display.inputadapters.InputAdapter;

public class MultipleAccessInputEventQueue {
	
	//Static things for event writer
	private static ArrayList<Event> eventQueue = new ArrayList<Event>();
	private static InputAdapter inputAdapter;
	
	//Synchronization things
	private static int numReaders = 0;
	private static Semaphore eventAccess = new Semaphore(1);
	private static Semaphore numReadersAccess = new Semaphore(1);
	private static Semaphore serviceQueue = new Semaphore(1);

	
	public static void setInputAdapter(InputAdapter adapter) {
		inputAdapter = adapter;
	}
	
	public static void stopEventInput() {
		if(inputAdapter != null) {
			inputAdapter.cleanup();
		}
	}
	
	public static void openForWriting() {
		try {
			serviceQueue.acquire();
			eventAccess.acquire();
		} catch (InterruptedException e) {
			//This shouldn't happen
			Logging.warning(e.getMessage(), e);
		}
		
		serviceQueue.release();
	}
	
	public static void closeWriting() {
		eventAccess.release();
	}
	
	public static void addEvent(Event event) {
		
		eventQueue.add(event);
		
	}
	
	public static void addManyEvents(ArrayList<Event> events) {
		
		eventQueue.addAll(events);
		
	}
	
	
	//Instance things for readers
	private int eventIndex;
	private String eventClass;
	
	public MultipleAccessInputEventQueue() {
		eventIndex = 0;
	}
	
	public MultipleAccessInputEventQueue(String eventClass) {
		this.eventClass = eventClass;
		eventIndex = 0;
	}
	
	public void openForReading() {
		try {
			serviceQueue.acquire();
			numReadersAccess.acquire();
		} catch (InterruptedException e) {
			// This shouldn't happen
			Logging.warning(e.getMessage(), e);
		}
		
		
		if(numReaders == 0) {
			try {
				eventAccess.acquire();
			} catch (InterruptedException e) {
				//This shouldn't happen
				Logging.warning(e.getMessage(), e);
			}
		}
		numReaders++;
		serviceQueue.release();
		numReadersAccess.release();
	}
	
	public void closeReading() {
		try {
			numReadersAccess.acquire();
		} catch (InterruptedException e) {
			// This shouldn't happen
			Logging.warning(e.getMessage(), e);
		}
		numReaders--;
		if(numReaders == 0) {
			eventAccess.release();
		}
		numReadersAccess.release();
	}
	
	public boolean hasNextEvent() {
		return eventIndex < eventQueue.size();
	}
	
	public Event getNextEvent() {
		return eventQueue.get(++eventIndex);
	}
	
	//Used for event specific readers so they don't consume unnecessary events
	public Event getNextEventOfType() {
		Event nextEvent;
		do{
			nextEvent = getNextEvent();
		} while(hasNextEvent() && !nextEvent.getEventClassString().equals(eventClass));
		
		return nextEvent.getEventClassString().equals(eventClass) ? nextEvent : null;
	}

}
