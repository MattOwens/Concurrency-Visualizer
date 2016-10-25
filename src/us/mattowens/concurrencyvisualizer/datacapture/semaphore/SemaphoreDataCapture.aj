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
		
		SemaphoreEvent createEvent = new SemaphoreEvent(s.toString(),
				SemaphoreEventType.Create);
		createEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		createEvent.setPermits(permits);
		
		EventQueue.addEvent(createEvent);
	}
	
	before(Semaphore s) : acquire(s) {
		SemaphoreEvent acquireEvent = new SemaphoreEvent(s.toString(), SemaphoreEventType.BeforeAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		//semaphoreStates.get(s).addThreadToWaitingQueue(Thread.currentThread());
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(Semaphore s) : acquire(s) {
		SemaphoreEvent acquireEvent = new SemaphoreEvent(s.toString(),
				SemaphoreEventType.AfterAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		//JavaSemaphore semaState = semaphoreStates.get(s);
		//semaState.permitsAcquired(1);
		//semaState.removeThreadFromWaitingQueue(Thread.currentThread());
		
		EventQueue.addEvent(acquireEvent);

	}
	
	before(Semaphore s, int permits) : acquireMany(s, permits) {
		SemaphoreEvent acquireEvent = new SemaphoreEvent(s.toString(), SemaphoreEventType.BeforeAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setPermits(permits);
		//semaphoreStates.get(s).addThreadToWaitingQueue(Thread.currentThread());
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(Semaphore s, int permits) : acquireMany(s, permits) {
		SemaphoreEvent acquireEvent = new SemaphoreEvent(s.toString(),
				SemaphoreEventType.AfterAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setPermits(permits);
		
		//JavaSemaphore semaState = semaphoreStates.get(s);
		//semaState.permitsAcquired(permits);
		//semaState.removeThreadFromWaitingQueue(Thread.currentThread());
		
		EventQueue.addEvent(acquireEvent);

	}
	
	before(Semaphore s) : tryAcquire(s) {
		SemaphoreEvent acquireEvent = new SemaphoreEvent(s.toString(), SemaphoreEventType.BeforeTryAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(Semaphore s) returning(boolean success) : tryAcquire(s) {
		SemaphoreEvent acquireEvent = new SemaphoreEvent(s.toString(), SemaphoreEventType.AfterTryAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setTryAcquireSuccessful(success);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(Semaphore s, long timeout, TimeUnit unit) : tryAcquireTimeout(s, timeout, unit) {
		SemaphoreEvent acquireEvent = new SemaphoreEvent(s.toString(), SemaphoreEventType.BeforeTryAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setTimeout(timeout);
		acquireEvent.setTimeoutUnit(unit);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(Semaphore s, long timeout, TimeUnit unit) returning(boolean success) 
			: tryAcquireTimeout(s, timeout, unit) {
		
		SemaphoreEvent acquireEvent = new SemaphoreEvent(s.toString(), SemaphoreEventType.AfterTryAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setTryAcquireSuccessful(success);
		acquireEvent.setTimeout(timeout);
		acquireEvent.setTimeoutUnit(unit);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(Semaphore s, int permits) : tryAcquireMany(s, permits) {
		SemaphoreEvent acquireEvent = new SemaphoreEvent(s.toString(), SemaphoreEventType.BeforeTryAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setPermits(permits);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(Semaphore s, int permits) returning(boolean success) : tryAcquireMany(s, permits) {
		SemaphoreEvent acquireEvent = new SemaphoreEvent(s.toString(), SemaphoreEventType.AfterTryAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setTryAcquireSuccessful(success);

		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(Semaphore s, int permits, long timeout, TimeUnit unit) :
		tryAcquireManyTimeout(s, permits, timeout, unit) {
		
		SemaphoreEvent acquireEvent = new SemaphoreEvent(s.toString(), SemaphoreEventType.BeforeTryAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setTimeout(timeout);
		acquireEvent.setTimeoutUnit(unit);
		acquireEvent.setPermits(permits);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(Semaphore s, int permits, long timeout, TimeUnit unit) returning(boolean success) :
		tryAcquireManyTimeout(s, permits, timeout, unit) {
		
		SemaphoreEvent acquireEvent = new SemaphoreEvent(s.toString(), SemaphoreEventType.AfterTryAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setTryAcquireSuccessful(success);
		acquireEvent.setTimeout(timeout);
		acquireEvent.setTimeoutUnit(unit);
		acquireEvent.setPermits(permits);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(Semaphore s) : release(s) {
		SemaphoreEvent releaseEvent = new SemaphoreEvent(s.toString(),
				SemaphoreEventType.Release);
		releaseEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		//semaphoreStates.get(s).permitsReleased(1);
		
		EventQueue.addEvent(releaseEvent);
	}
	
	before(Semaphore s, int permits) : releaseMany(s, permits) {
		SemaphoreEvent releaseEvent = new SemaphoreEvent(s.toString(),
				SemaphoreEventType.Release);
		releaseEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		releaseEvent.setPermits(permits);
		
		//semaphoreStates.get(s).permitsReleased(permits);
		
		EventQueue.addEvent(releaseEvent);
	}
	
	after(Semaphore s, int reduction) : reducePermits(s, reduction) {
		SemaphoreEvent reductionEvent = new SemaphoreEvent(s.toString(),
				SemaphoreEventType.PermitReduction);
		reductionEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		reductionEvent.setPermits(reduction);
		
		EventQueue.addEvent(reductionEvent);		
	}
	
	after(Semaphore s) : drainPermits(s) {
		SemaphoreEvent drainEvent = new SemaphoreEvent(s.toString(),
				SemaphoreEventType.DrainPermits);
		drainEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(drainEvent);
	}
}
