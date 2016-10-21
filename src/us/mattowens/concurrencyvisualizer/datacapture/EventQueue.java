package us.mattowens.concurrencyvisualizer.datacapture;


import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;


public final class EventQueue {
	
	private static final EventQueue singletonEventQueue = new EventQueue();
	private static boolean eventOutputStarted = false;
	
	private  ConcurrentLinkedQueue<Event> eventQueue;
	private  ArrayList<OutputAdapter> outputAdapters;
	private  Thread outputThread;
	
	
	private EventQueue() {
		eventQueue = new ConcurrentLinkedQueue<Event>();
		outputAdapters = new ArrayList<OutputAdapter>();
	}
	
	public static void addEvent(Event newEvent) {
		singletonEventQueue.eventQueue.add(newEvent);
	}
	
	public static void startEventOutput() {
		singletonEventQueue.startOutputThread();
	}
	
	public static void stopEventOutput() {
		singletonEventQueue.outputThread.interrupt();
	}
	
	public static void addOutputAdapter(OutputAdapter adapter) {
		if(eventOutputStarted) {
			throw new IllegalStateException("EventQueue : Cannot add output adapter after data collection has started");
		}
		
		singletonEventQueue.outputAdapters.add(adapter);
	}
	
	private void startOutputThread() {
		outputThread = new Thread(new EventOutputThread());
		outputThread.setName("Output Thread");
		outputThread.start();
	}
	
	class EventOutputThread implements Runnable {

		@Override
		public void run() {
			while(true) {
				
				
				while(!eventQueue.isEmpty()) {
					Event event = eventQueue.remove();
					
					for(OutputAdapter adapter : outputAdapters) {
						adapter.sendEvent(event);
					}
				}
					
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {	
					for(OutputAdapter adapter : outputAdapters) {
						adapter.cleanup();
					}
					System.out.println("Event Queue Output thread ending");
					return;
				}
				
				if(Thread.interrupted()) {
					for(OutputAdapter adapter : outputAdapters) {
						adapter.cleanup();
					}
					System.out.println("Event Queue Output thread ending");
					return;
				}	
			}
		}
	}
}
