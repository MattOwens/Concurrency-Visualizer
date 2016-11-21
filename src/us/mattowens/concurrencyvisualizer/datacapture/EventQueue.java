package us.mattowens.concurrencyvisualizer.datacapture;


import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import us.mattowens.concurrencyvisualizer.Logging;


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
		if(singletonEventQueue.outputThread != null) {
			singletonEventQueue.outputThread.interrupt();
		}
	}
	
	public static void addOutputAdapter(OutputAdapter adapter) {
		if(eventOutputStarted) {
			Logging.warning("Output adapter added after event output started");
		}
		
		singletonEventQueue.outputAdapters.add(adapter);
	}
	
	private void startOutputThread() {
		outputThread = new Thread(new EventOutputThread());
		outputThread.setName("Output Thread");
		outputThread.start();
	}
	
	class EventOutputThread implements Runnable {
		private boolean continueOutput = true;

		@Override
		public void run() {
			while(continueOutput) {
				
				
				while(!eventQueue.isEmpty()) {
					Event event = eventQueue.remove();
					
					for(OutputAdapter adapter : outputAdapters) {
						adapter.sendEvent(event);
					}
				}
					
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {	
					cleanup();
				}
				
				if(Thread.interrupted()) {
					cleanup();
				}	
			}
		}

		private void cleanup() {
			for(OutputAdapter adapter : outputAdapters) {
				adapter.cleanup();
			}
			Logging.message("Event Queue Output thread ending");
			continueOutput = false;
		}
	}
}
