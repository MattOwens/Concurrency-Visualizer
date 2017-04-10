package us.mattowens.concurrencyvisualizer.datacapture;

import java.util.HashMap;
import java.util.Map;

public enum EventClass {
	Semaphore(0),
	ReentrantLock(1),
	Runnable(2),
	Monitor(3),
	Lock(4),
	Thread(5),
	AbstractOwnableSynchronizer(6),
	AbstractQueuedLongSynchronizer(7),
	AbstractQueuedSynchronizer(8),
	Condition(9),
	CountDownLatch(10),
	CyclicBarrier(11),
	Executor(12),
	ForkJoinWorkerThreadFactory(13),
	ManagedBlocker(14),
	Phaser(15),
	ReentrantReadWriteLock(16),
	RejectedExecutionHandler(17),
	ThreadFactory(18),
	ThreadGroup(19),
	Timer(20),
	TimerTask(21),
	RecursiveAction(22);
	
	private static final Map<Long, EventClass> LOOKUP = new HashMap<Long, EventClass>();
	private long classCode;
	
	static {
		for(EventClass c : values()) {
			LOOKUP.put(c.classCode, c);
		}
	}
	
	private EventClass(int code) {
		classCode = code;
	}
	
	public long getCode() {
		return classCode;
	}
	
	public static EventClass fromLong(long eventClass) {
		return LOOKUP.get(eventClass);
	}
	
	public static String getString(int eventClass) {
		return String.valueOf(fromLong(eventClass));
	}

}
