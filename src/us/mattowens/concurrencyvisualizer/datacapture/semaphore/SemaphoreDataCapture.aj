package us.mattowens.concurrencyvisualizer.datacapture.semaphore;

import java.util.concurrent.*;

import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

import java.util.HashMap;
import java.io.*;

public aspect SemaphoreDataCapture {
	
	ConcurrentHashMap<Semaphore, JavaSemaphore> semaphoreStates = 
			new ConcurrentHashMap<Semaphore, JavaSemaphore>();
	
	pointcut create(int permits):
		call(Semaphore.new(int)) &&
		args(permits);
	
	pointcut acquire(Semaphore s):
		call(void Semaphore.acquire*()) &&
		target(s);
	
	pointcut acquireMany(Semaphore s, int requestedPermits):
		call(void Semaphore.acquire*(int)) &&
		target(s) &&
		args(requestedPermits, ..);
	
	pointcut tryAcquire(Semaphore s):
		call(boolean Semaphore.tryAcquire()) &&
		target(s);
	
	pointcut tryAcquireMany(Semaphore s, int permits):
		call(boolean Semaphore.tryAcquire(int)) &&
		target(s) &&
		args(permits);
	
	pointcut tryAcquireTimeout(Semaphore s, long timeout, TimeUnit unit):
		call(boolean Semaphore.tryAcquire(long, TimeUnit)) &&
		target(s) &&
		args(timeout, unit);
	
	pointcut tryAcquireManyTimeout(Semaphore s, int permits, long timeout, TimeUnit unit):
		call(boolean Semaphore.tryAcquire(int, long, TimeUnit)) &&
		target(s) &&
		args(permits, timeout, unit);

	pointcut release(Semaphore s):
		call(void Semaphore.release*()) &&
		target(s);
	
	pointcut releaseMany(Semaphore s, int numPermits):
		call(void Semaphore.release*(int)) &&
		target(s) &&
		args(numPermits);
	
	pointcut reducePermits(Semaphore s, int reduction):
		call(void Semaphore.reducePermits(int)) &&
		target(s) &&
		args(reduction);
	
	pointcut drainPermits(Semaphore s):
		call(void Semaphore.drainPermits()) &&
		target(s);
		
	
	
	
	
	after(int permits) returning(Semaphore s) : create(permits) {
		JavaSemaphore newSemaphore = new JavaSemaphore(permits);
		semaphoreStates.put(s, newSemaphore);
		
		SemaphoreEvent createEvent = new SemaphoreEvent(SemaphoreEventType.Create);
		createEvent.setPermits(permits);
		
		EventQueue.addEvent(createEvent);
	}
	
	before(Semaphore s) : acquire(s) {
		//Event acquireEvent = buildBasicEvent();
		//acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		semaphoreStates.get(s).addThreadToWaitingQueue(Thread.currentThread());
	}
	
	after(Semaphore s) : acquire(s) {
		SemaphoreEvent acquireEvent = new SemaphoreEvent(SemaphoreEventType.Acquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setPermits(1);
		
		JavaSemaphore semaState = semaphoreStates.get(s);
		semaState.permitsAcquired(1);
		semaState.removeThreadFromWaitingQueue(Thread.currentThread());
		
		EventQueue.addEvent(acquireEvent);

	}
	
	before(Semaphore s, int permits) : acquireMany(s, permits) {
		semaphoreStates.get(s).addThreadToWaitingQueue(Thread.currentThread());
	}
	
	after(Semaphore s, int permits) : acquireMany(s, permits) {
		SemaphoreEvent acquireEvent = new SemaphoreEvent(SemaphoreEventType.Acquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setPermits(permits);
		
		JavaSemaphore semaState = semaphoreStates.get(s);
		semaState.permitsAcquired(permits);
		semaState.removeThreadFromWaitingQueue(Thread.currentThread());
		
		EventQueue.addEvent(acquireEvent);

	}
	
	after(Semaphore s) returning(boolean success) : tryAcquire(s) {
		SemaphoreEvent acquireEvent;
		
		
		if(success) {
			acquireEvent = new SemaphoreEvent(SemaphoreEventType.Acquire);
			JavaSemaphore semaState = semaphoreStates.get(s);
			semaState.permitsAcquired(1);
		}
		else {
			acquireEvent = new SemaphoreEvent(SemaphoreEventType.TryAcquireFailure);
		}
		
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setPermits(1);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(Semaphore s, long timeout, TimeUnit unit) : tryAcquireTimeout(s, timeout, unit) {
		semaphoreStates.get(s).addThreadToWaitingQueue(Thread.currentThread());
	}
	
	after(Semaphore s, long timeout, TimeUnit unit) returning(boolean success) 
			: tryAcquireTimeout(s, timeout, unit) {
		
		JavaSemaphore semaState = semaphoreStates.get(s);
		semaState.removeThreadFromWaitingQueue(Thread.currentThread());
		
		SemaphoreEvent acquireEvent;

		if(success) {
			acquireEvent = new SemaphoreEvent(SemaphoreEventType.Acquire);
			semaState.permitsAcquired(1);
		}
		else {
			acquireEvent = new SemaphoreEvent(SemaphoreEventType.TryAcquireFailure);
			acquireEvent.setTimeout(timeout);
			acquireEvent.setTimeoutUnit(unit);
		}
		
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setPermits(1);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(Semaphore s, int permits) returning(boolean success) : tryAcquireMany(s, permits) {
		SemaphoreEvent acquireEvent;
		
		if(success) {
			acquireEvent = new SemaphoreEvent(SemaphoreEventType.Acquire);
			
			JavaSemaphore semaState = semaphoreStates.get(s);
			semaState.permitsAcquired(permits);
		}
		else {
			acquireEvent = new SemaphoreEvent(SemaphoreEventType.TryAcquireFailure);
		}
		
		acquireEvent.setPermits(permits);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(Semaphore s, int permits, long timeout, TimeUnit unit) :
		tryAcquireManyTimeout(s, permits, timeout, unit) {
		
		semaphoreStates.get(s).addThreadToWaitingQueue(Thread.currentThread());	
	}
	
	after(Semaphore s, int permits, long timeout, TimeUnit unit) returning(boolean success) :
		tryAcquireManyTimeout(s, permits, timeout, unit) {
		
		SemaphoreEvent acquireEvent;
		
		JavaSemaphore semaState = semaphoreStates.get(s);
		semaState.removeThreadFromWaitingQueue(Thread.currentThread());
		
		if(success) {
			acquireEvent = new SemaphoreEvent(SemaphoreEventType.Acquire);
			acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
			acquireEvent.setPermits(permits);
			semaState.permitsAcquired(permits);
		}
		else {
			acquireEvent = new SemaphoreEvent(SemaphoreEventType.AcquireTimeout);
			acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
			acquireEvent.setPermits(permits);
			acquireEvent.setTimeout(timeout);
			acquireEvent.setTimeoutUnit(unit);
		}
		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(Semaphore s) : release(s) {
		SemaphoreEvent releaseEvent = new SemaphoreEvent(SemaphoreEventType.Release);
		releaseEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		releaseEvent.setPermits(1);
		
		semaphoreStates.get(s).permitsReleased(1);
		
		EventQueue.addEvent(releaseEvent);
	}
	
	before(Semaphore s, int permits) : releaseMany(s, permits) {
		SemaphoreEvent releaseEvent = new SemaphoreEvent(SemaphoreEventType.Release);
		releaseEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		releaseEvent.setPermits(permits);
		
		semaphoreStates.get(s).permitsReleased(permits);
		
		EventQueue.addEvent(releaseEvent);
	}
	
	after(Semaphore s, int reduction) : reducePermits(s, reduction) {
		SemaphoreEvent reductionEvent = new SemaphoreEvent(SemaphoreEventType.PermitReduction);
		reductionEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		reductionEvent.setPermits(reduction);
		
		EventQueue.addEvent(reductionEvent);
		//Need to do some state tracking here
		
	}
	
	after(Semaphore s) : drainPermits(s) {
		SemaphoreEvent drainEvent = new SemaphoreEvent(SemaphoreEventType.DrainPermits);
		drainEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(drainEvent);
	}
}
