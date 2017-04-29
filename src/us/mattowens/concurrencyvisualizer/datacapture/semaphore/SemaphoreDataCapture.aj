package us.mattowens.concurrencyvisualizer.datacapture.semaphore;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

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
		Event createEvent = new Event(EventClass.Semaphore, SemaphoreEventType.Create, s.toString());
		createEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		createEvent.addValue(StringConstants.PERMITS, permits);
		
		EventQueue.addEvent(createEvent);
	}
	
	before(Semaphore s) : acquire(s) {
		Event acquireEvent = new Event(EventClass.Semaphore, SemaphoreEventType.BeforeAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(Semaphore s) : acquire(s) {
		Event acquireEvent = new Event(EventClass.Semaphore, SemaphoreEventType.AfterAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(Semaphore s, int permits) : acquireMany(s, permits) {
		Event acquireEvent = new Event(EventClass.Semaphore, SemaphoreEventType.BeforeAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.PERMITS, permits);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(Semaphore s, int permits) : acquireMany(s, permits) {
		Event acquireEvent = new Event(EventClass.Semaphore, SemaphoreEventType.AfterAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(acquireEvent);

	}
	
	before(Semaphore s) : tryAcquire(s) {
		Event acquireEvent = new Event(EventClass.Semaphore, SemaphoreEventType.BeforeTryAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(Semaphore s) returning(boolean success) : tryAcquire(s) {
		Event acquireEvent = new Event(EventClass.Semaphore, SemaphoreEventType.AfterTryAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.SUCCESS, success);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(Semaphore s, long timeout, TimeUnit unit) : tryAcquireTimeout(s, timeout, unit) {
		Event acquireEvent = new Event(EventClass.Semaphore, SemaphoreEventType.BeforeTryAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.TIMEOUT, timeout);
		acquireEvent.addValue(StringConstants.TIME_UNIT, unit);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(Semaphore s, long timeout, TimeUnit unit) returning(boolean success) 
			: tryAcquireTimeout(s, timeout, unit) {
		
		Event acquireEvent = new Event(EventClass.Semaphore, SemaphoreEventType.AfterTryAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.SUCCESS, success);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(Semaphore s, int permits) : tryAcquireMany(s, permits) {
		Event acquireEvent = new Event(EventClass.Semaphore, SemaphoreEventType.BeforeTryAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.PERMITS, permits);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(Semaphore s, int permits) returning(boolean success) : tryAcquireMany(s, permits) {
		Event acquireEvent = new Event(EventClass.Semaphore, SemaphoreEventType.AfterTryAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.SUCCESS, success);

		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(Semaphore s, int permits, long timeout, TimeUnit unit) :
		tryAcquireManyTimeout(s, permits, timeout, unit) {
		
		Event acquireEvent = new Event(EventClass.Semaphore, SemaphoreEventType.BeforeTryAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.TIMEOUT, timeout);
		acquireEvent.addValue(StringConstants.TIME_UNIT, unit);
		acquireEvent.addValue(StringConstants.PERMITS, permits);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(Semaphore s, int permits, long timeout, TimeUnit unit) returning(boolean success) :
		tryAcquireManyTimeout(s, permits, timeout, unit) {
		
		Event acquireEvent = new Event(EventClass.Semaphore, SemaphoreEventType.AfterTryAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.SUCCESS, success);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(Semaphore s) : release(s) {
		Event releaseEvent = new Event(EventClass.Semaphore, SemaphoreEventType.Release, s.toString());
		releaseEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
				
		EventQueue.addEvent(releaseEvent);
	}
	
	before(Semaphore s, int permits) : releaseMany(s, permits) {
		Event releaseEvent = new Event(EventClass.Semaphore, SemaphoreEventType.Release, s.toString());
		releaseEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		releaseEvent.addValue(StringConstants.PERMITS, permits);
				
		EventQueue.addEvent(releaseEvent);
	}
	
	after(Semaphore s, int reduction) : reducePermits(s, reduction) {
		Event reductionEvent = new Event(EventClass.Semaphore, SemaphoreEventType.PermitReduction, s.toString());
		reductionEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		reductionEvent.addValue(StringConstants.PERMITS, reduction);
		
		EventQueue.addEvent(reductionEvent);		
	}
	
	after(Semaphore s) : drainPermits(s) {
		Event drainEvent = new Event(EventClass.Semaphore, SemaphoreEventType.DrainPermits, s.toString());
		drainEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(drainEvent);
	}
}
