package us.mattowens.concurrencyvisualizer.datacapture.forkjoinpool.forkjoinworkerthreadfactory;


import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class ForkJoinWorkerThreadFactoryEvent extends Event {

	private ForkJoinWorkerThreadFactoryEventType eventType;
	private String forkJoinPool;
	private String forkJoinWorkerThread;
	
	public ForkJoinWorkerThreadFactoryEvent(String factory,
			ForkJoinWorkerThreadFactoryEventType eventType) {
		super(factory);
		
		setEventType(eventType);
	}

	public ForkJoinWorkerThreadFactoryEventType getEventType() {
		return eventType;
	}

	public void setEventType(ForkJoinWorkerThreadFactoryEventType eventType) {
		this.eventType = eventType;
		eventMap.put("EventType", eventType);
	}

	public String getForkJoinPool() {
		return forkJoinPool;
	}

	public void setForkJoinPool(String forkJoinPool) {
		this.forkJoinPool = forkJoinPool;
		eventMap.put("ForkJoinPool", forkJoinPool);
	}

	public String getForkJoinWorkerThread() {
		return forkJoinWorkerThread;
	}

	public void setForkJoinWorkerThread(String forkJoinWorkerThread) {
		this.forkJoinWorkerThread = forkJoinWorkerThread;
		eventMap.put("ForkJoinWorkerThread", forkJoinWorkerThread);
	}
	
	
	
}
