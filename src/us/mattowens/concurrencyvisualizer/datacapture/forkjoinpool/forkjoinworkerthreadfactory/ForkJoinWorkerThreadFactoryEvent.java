package us.mattowens.concurrencyvisualizer.datacapture.forkjoinpool.forkjoinworkerthreadfactory;

import java.util.Map;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class ForkJoinWorkerThreadFactoryEvent extends Event {

	private ForkJoinWorkerThreadFactoryEventType eventType;
	private String forkJoinPool;
	private String forkJoinWorkerThread;
	
	public ForkJoinWorkerThreadFactoryEvent(String factory,
			ForkJoinWorkerThreadFactoryEventType eventType) {
		super(factory);
		
		this.eventType = eventType;
	}
	
	@Override
	public Map<String, Object> collapseToMap() {
		Map<String, Object> eventMap = super.collapseToMap();
		
		eventMap.put("EventType", eventType);
		eventMap.put("ForkJoinPool", forkJoinPool);
		eventMap.put("ForkJoinWorkerThread", forkJoinWorkerThread);
		
		return eventMap;
	}

	public ForkJoinWorkerThreadFactoryEventType getEventType() {
		return eventType;
	}

	public void setEventType(ForkJoinWorkerThreadFactoryEventType eventType) {
		this.eventType = eventType;
	}

	public String getForkJoinPool() {
		return forkJoinPool;
	}

	public void setForkJoinPool(String forkJoinPool) {
		this.forkJoinPool = forkJoinPool;
	}

	public String getForkJoinWorkerThread() {
		return forkJoinWorkerThread;
	}

	public void setForkJoinWorkerThread(String forkJoinWorkerThread) {
		this.forkJoinWorkerThread = forkJoinWorkerThread;
	}
	
	
	
}
