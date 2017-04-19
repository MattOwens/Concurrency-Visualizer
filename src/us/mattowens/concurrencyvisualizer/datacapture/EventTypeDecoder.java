package us.mattowens.concurrencyvisualizer.datacapture;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import us.mattowens.concurrencyvisualizer.Logging;
import us.mattowens.concurrencyvisualizer.datacapture.abstractownablesynchronizer.AbstractOwnableSynchronizerEventType;
import us.mattowens.concurrencyvisualizer.datacapture.abstractqueuedlongsynchronizer.AbstractQueuedLongSynchronizerEventType;
import us.mattowens.concurrencyvisualizer.datacapture.abstractqueuedsynchronizer.AbstractQueuedSynchronizerEventType;
import us.mattowens.concurrencyvisualizer.datacapture.condition.ConditionEventType;
import us.mattowens.concurrencyvisualizer.datacapture.countdownlatch.CountDownLatchEventType;
import us.mattowens.concurrencyvisualizer.datacapture.cyclicbarrier.CyclicBarrierEventType;
import us.mattowens.concurrencyvisualizer.datacapture.exeutor.ExecutorEventType;
import us.mattowens.concurrencyvisualizer.datacapture.forkjoinpool.forkjoinworkerthreadfactory.ForkJoinWorkerThreadFactoryEventType;
import us.mattowens.concurrencyvisualizer.datacapture.forkjoinpool.managedblocker.ManagedBlockerEventType;
import us.mattowens.concurrencyvisualizer.datacapture.lock.LockEventType;
import us.mattowens.concurrencyvisualizer.datacapture.object.MonitorEventType;
import us.mattowens.concurrencyvisualizer.datacapture.phaser.PhaserEventType;
import us.mattowens.concurrencyvisualizer.datacapture.recursiveaction.RecursiveActionEventType;
import us.mattowens.concurrencyvisualizer.datacapture.reentrantlock.ReentrantLockEventType;
import us.mattowens.concurrencyvisualizer.datacapture.reentrantreadwritelock.ReentrantReadWriteLockEventType;
import us.mattowens.concurrencyvisualizer.datacapture.rejectedexecutionhandler.RejectedExecutionHandlerEventType;
import us.mattowens.concurrencyvisualizer.datacapture.runnable.RunnableEventType;
import us.mattowens.concurrencyvisualizer.datacapture.semaphore.SemaphoreEventType;
import us.mattowens.concurrencyvisualizer.datacapture.thread.ThreadEventType;
import us.mattowens.concurrencyvisualizer.datacapture.threadfactory.ThreadFactoryEventType;
import us.mattowens.concurrencyvisualizer.datacapture.threadgroup.ThreadGroupEventType;
import us.mattowens.concurrencyvisualizer.datacapture.timer.TimerEventType;
import us.mattowens.concurrencyvisualizer.datacapture.timer.TimerTaskEventType;

public class EventTypeDecoder {
	private static final String METHOD_NAME = "fromCode";
	
	private static Map<EventClass, Method> FROM_INT_MAP;
	
	static {
		FROM_INT_MAP = new HashMap<EventClass, Method>();
		try {
			FROM_INT_MAP.put(EventClass.Semaphore, SemaphoreEventType.class.getMethod(METHOD_NAME, long.class));
			FROM_INT_MAP.put(EventClass.ReentrantLock, ReentrantLockEventType.class.getMethod(METHOD_NAME, long.class));
			FROM_INT_MAP.put(EventClass.Runnable, RunnableEventType.class.getMethod(METHOD_NAME, long.class));
			FROM_INT_MAP.put(EventClass.Monitor, MonitorEventType.class.getMethod(METHOD_NAME, long.class));
			FROM_INT_MAP.put(EventClass.Lock, LockEventType.class.getMethod(METHOD_NAME, long.class));
			FROM_INT_MAP.put(EventClass.Thread, ThreadEventType.class.getMethod(METHOD_NAME, long.class));
			FROM_INT_MAP.put(EventClass.AbstractOwnableSynchronizer, AbstractOwnableSynchronizerEventType.class.getMethod(METHOD_NAME, long.class));
			FROM_INT_MAP.put(EventClass.AbstractQueuedLongSynchronizer, AbstractQueuedLongSynchronizerEventType.class.getMethod(METHOD_NAME, long.class));
			FROM_INT_MAP.put(EventClass.AbstractQueuedSynchronizer, AbstractQueuedSynchronizerEventType.class.getMethod(METHOD_NAME, long.class));
			FROM_INT_MAP.put(EventClass.Condition, ConditionEventType.class.getMethod(METHOD_NAME, long.class));
			FROM_INT_MAP.put(EventClass.CountDownLatch, CountDownLatchEventType.class.getMethod(METHOD_NAME, long.class));
			FROM_INT_MAP.put(EventClass.CyclicBarrier, CyclicBarrierEventType.class.getMethod(METHOD_NAME, long.class));
			FROM_INT_MAP.put(EventClass.Executor, ExecutorEventType.class.getMethod(METHOD_NAME, long.class));
			FROM_INT_MAP.put(EventClass.ForkJoinWorkerThreadFactory, ForkJoinWorkerThreadFactoryEventType.class.getMethod(METHOD_NAME, long.class));
			FROM_INT_MAP.put(EventClass.ManagedBlocker, ManagedBlockerEventType.class.getMethod(METHOD_NAME, long.class));
			FROM_INT_MAP.put(EventClass.Phaser, PhaserEventType.class.getMethod(METHOD_NAME, long.class));
			FROM_INT_MAP.put(EventClass.ReentrantReadWriteLock, ReentrantReadWriteLockEventType.class.getMethod(METHOD_NAME, long.class));
			FROM_INT_MAP.put(EventClass.RejectedExecutionHandler, RejectedExecutionHandlerEventType.class.getMethod(METHOD_NAME, long.class));
			FROM_INT_MAP.put(EventClass.ThreadFactory, ThreadFactoryEventType.class.getMethod(METHOD_NAME, long.class));
			FROM_INT_MAP.put(EventClass.ThreadGroup, ThreadGroupEventType.class.getMethod(METHOD_NAME, long.class));
			FROM_INT_MAP.put(EventClass.Timer, TimerEventType.class.getMethod(METHOD_NAME, long.class));
			FROM_INT_MAP.put(EventClass.TimerTask, TimerTaskEventType.class.getMethod(METHOD_NAME, long.class));
			FROM_INT_MAP.put(EventClass.RecursiveAction, RecursiveActionEventType.class.getMethod(METHOD_NAME, long.class));
			FROM_INT_MAP.put(EventClass.ControlSignal, ControlSignalEventType.class.getMethod(METHOD_NAME, long.class));
		} catch (Exception e) {
			Logging.exception(e);
		}
	}
	public static EventType getEventType(EventClass eventClass, long type) {
		Method fromIntMethod = FROM_INT_MAP.get(eventClass);
		
		EventType eventType = null;
		try {
			eventType = (EventType) fromIntMethod.invoke(null, type);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Logging.exception(e);
		}
		return eventType;
	}

}
