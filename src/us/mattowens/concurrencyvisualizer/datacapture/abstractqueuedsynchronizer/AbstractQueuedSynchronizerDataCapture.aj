package us.mattowens.concurrencyvisualizer.datacapture.abstractqueuedsynchronizer;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;


public aspect AbstractQueuedSynchronizerDataCapture {

	pointcut create() :
		call(AbstractQueuedSynchronizer.new());
	
	pointcut acquire(AbstractQueuedSynchronizer s, int arg) :
		(call(void AbstractQueuedSynchronizer.acquire(int)) ||
			call(void AbstractQueuedSynchronizer.acquireInterruptibly(int))) &&
		target(s) &&
		args(arg);
	
	pointcut acquireShared(AbstractQueuedSynchronizer s, int arg) :
		(call(void AbstractQueuedSynchronizer.acquireShared(int)) ||
			call(void AbstractQueuedSynchronizer.acquireSharedInterruptibly(int))) &&
		target(s) &&
		args(arg);
	
	pointcut compareAndSetState(AbstractQueuedSynchronizer s, int expect, int update) :
		call(boolean AbstractQueuedSynchronizer.compareAndSetState(int, int)) &&
		target(s) &&
		args(expect, update);
	
	pointcut release(AbstractQueuedSynchronizer s, int arg) :
		call(boolean AbstractQueuedSynchronizer.release(int)) &&
		target(s) &&
		args(arg);
	
	pointcut releaseShared(AbstractQueuedSynchronizer s, int arg) :
		call(boolean AbstractQueuedSynchronizer.releaseShared(int)) &&
		target(s) &&
		args(arg);
	
	pointcut setState(AbstractQueuedSynchronizer s, int newState) :
		call(void AbstractQueuedSynchronizer.setState(int)) &&
		target(s) &&
		args(newState);
	
	pointcut tryAcquire(AbstractQueuedSynchronizer s, int arg) :
		call(boolean AbstractQueuedSynchronizer.tryAcquire(int)) &&
		target(s) &&
		args(arg);
	
	pointcut tryAcquireNanos(AbstractQueuedSynchronizer s, int arg, long nanosTimeout) :
		call(boolean AbstractQueuedSynchronizer.tryAcquireNanos(int, long)) &&
		target(s) &&
		args(arg, nanosTimeout);
	
	pointcut tryAcquireShared(AbstractQueuedSynchronizer s, int arg) :
		call(int AbstractQueuedSynchronizer.tryAcquireShared(int)) &&
		target(s) &&
		args(arg);
	
	pointcut tryAcquireSharedNanos(AbstractQueuedSynchronizer s, int arg, long nanosTimeout) :
		call(boolean AbstractQueuedSynchronizer.tryAcquireSharedNanos(int, long)) &&
		target(s) &&
		args(arg, nanosTimeout);
	
	pointcut tryRelease(AbstractQueuedSynchronizer s, int arg) :
		call(boolean AbstractQueuedSynchronizer.tryRelease(int)) &&
		target(s) &&
		args(arg);
	
	pointcut tryReleaseShared(AbstractQueuedSynchronizer s, int arg) :
		call(boolean AbstractQueuedSynchronizer.tryReleaseShared(int)) &&
		target(s) &&
		args(arg);
	
	after() returning(AbstractQueuedSynchronizer s) : create() {
		Event createEvent = new Event(EventClass.AbstractQueuedSynchronizer, 
				AbstractQueuedSynchronizerEventType.Create, s.toString());
		createEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(createEvent);
	}
	
	before(AbstractQueuedSynchronizer s, int arg) : acquire(s, arg) {
		Event acquireEvent = new Event(EventClass.AbstractQueuedSynchronizer,
				AbstractQueuedSynchronizerEventType.BeforeAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.ARG, arg);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int arg) : acquire(s, arg) {
		Event acquireEvent = new Event(EventClass.AbstractQueuedSynchronizer,
				AbstractQueuedSynchronizerEventType.AfterAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(AbstractQueuedSynchronizer s, int arg) : acquireShared(s, arg) {
		Event acquireEvent = new Event(EventClass.AbstractQueuedSynchronizer,
				AbstractQueuedSynchronizerEventType.BeforeAcquireShared, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.ARG, arg);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int arg) : acquireShared(s, arg) {
		Event acquireEvent = new Event(EventClass.AbstractQueuedSynchronizer,
				AbstractQueuedSynchronizerEventType.AfterAcquireShared, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int expect, int update) returning(boolean successful) : 
		compareAndSetState(s, expect, update) {
		
		Event updateEvent = new Event(EventClass.AbstractQueuedSynchronizer,
				AbstractQueuedSynchronizerEventType.CompareAndSetState, s.toString());
		updateEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		updateEvent.addValue(StringConstants.EXPECT, expect);
		updateEvent.addValue(StringConstants.UPDATE, update);
		updateEvent.addValue(StringConstants.SUCCESS, successful);
		
		EventQueue.addEvent(updateEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int arg) returning(boolean successful) : release(s, arg) {
		Event releaseEvent = new Event(EventClass.AbstractQueuedSynchronizer,
				AbstractQueuedSynchronizerEventType.Release, s.toString());
		releaseEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		releaseEvent.addValue(StringConstants.ARG, arg);
		releaseEvent.addValue(StringConstants.SUCCESS, successful);
		
		EventQueue.addEvent(releaseEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int arg) returning(boolean successful) : releaseShared(s, arg) {
		Event releaseEvent = new Event(EventClass.AbstractQueuedSynchronizer,
				AbstractQueuedSynchronizerEventType.ReleaseShared, s.toString());
		releaseEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		releaseEvent.addValue(StringConstants.ARG, arg);
		releaseEvent.addValue(StringConstants.SUCCESS, successful);
		
		EventQueue.addEvent(releaseEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int newState) : setState(s, newState) {
		Event updateEvent = new Event(EventClass.AbstractQueuedSynchronizer,
				AbstractQueuedSynchronizerEventType.CompareAndSetState, s.toString());
		updateEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		updateEvent.addValue(StringConstants.NEW_STATE, newState);
		
		EventQueue.addEvent(updateEvent);
	}
	
	before(AbstractQueuedSynchronizer s, int arg) : tryAcquire(s, arg) {
		Event acquireEvent = new Event(EventClass.AbstractQueuedSynchronizer,
				AbstractQueuedSynchronizerEventType.BeforeTryAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.ARG, arg);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int arg) returning(boolean successful) : tryAcquire(s, arg) {
		Event acquireEvent = new Event(EventClass.AbstractQueuedSynchronizer,
				AbstractQueuedSynchronizerEventType.AfterTryAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.SUCCESS, successful);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(AbstractQueuedSynchronizer s, int arg, long nanosTimeout) : tryAcquireNanos(s, arg, nanosTimeout) {
		Event acquireEvent = new Event(EventClass.AbstractQueuedSynchronizer,
				AbstractQueuedSynchronizerEventType.BeforeTryAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.ARG, arg);
		acquireEvent.addValue(StringConstants.NANOS, nanosTimeout);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int arg, long nanosTimeout) returning(boolean successful) : 
		tryAcquireNanos(s, arg, nanosTimeout) {
		
		Event acquireEvent = new Event(EventClass.AbstractQueuedSynchronizer,
				AbstractQueuedSynchronizerEventType.AfterTryAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.SUCCESS, successful);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int arg) returning(int tryAcquireResult) : tryAcquireShared(s, arg) {
		Event acquireEvent = new Event(EventClass.AbstractQueuedSynchronizer,
				AbstractQueuedSynchronizerEventType.AfterTryAcquireShared, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.ARG, arg);
		acquireEvent.addValue(StringConstants.TRY_ACQUIRE_RESULT, tryAcquireResult);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(AbstractQueuedSynchronizer s, int arg, long nanosTimeout) : tryAcquireSharedNanos(s, arg, nanosTimeout) {
		Event acquireEvent = new Event(EventClass.AbstractQueuedSynchronizer,
				AbstractQueuedSynchronizerEventType.BeforeTryAcquireShared, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.ARG, arg);
		acquireEvent.addValue(StringConstants.NANOS, nanosTimeout);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int arg, long nanosTimeout) returning(boolean successful) : 
		tryAcquireSharedNanos(s, arg, nanosTimeout) {
		
		Event acquireEvent = new Event(EventClass.AbstractQueuedSynchronizer,
				AbstractQueuedSynchronizerEventType.AfterTryAcquireShared, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.SUCCESS, successful);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int arg) returning(boolean successful) : tryRelease(s, arg) {
		Event releaseEvent = new Event(EventClass.AbstractQueuedSynchronizer,
				AbstractQueuedSynchronizerEventType.TryRelease, s.toString());
		releaseEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		releaseEvent.addValue(StringConstants.ARG, arg);
		
		EventQueue.addEvent(releaseEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int arg) returning(boolean successful) : tryReleaseShared(s, arg) {
		Event releaseEvent = new Event(EventClass.AbstractQueuedSynchronizer,
				AbstractQueuedSynchronizerEventType.TryReleaseShared, s.toString());
		releaseEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		releaseEvent.addValue(StringConstants.ARG, arg);
		
		EventQueue.addEvent(releaseEvent);
	}
}
