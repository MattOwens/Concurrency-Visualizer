package us.mattowens.concurrencyvisualizer.datacapture.abstractqueuedlongsynchronizer;

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;


public aspect AbstractQueuedLongSynchronizerDataCapture {

	pointcut create() :
		call(AbstractQueuedLongSynchronizer.new());
	
	pointcut acquire(AbstractQueuedLongSynchronizer s, long arg) :
		(call(void AbstractQueuedLongSynchronizer.acquire(int)) ||
			call(void AbstractQueuedLongSynchronizer.acquireInterruptibly(int))) &&
		target(s) &&
		args(arg);
	
	pointcut acquireShared(AbstractQueuedLongSynchronizer s, long arg) :
		(call(void AbstractQueuedLongSynchronizer.acquireShared(int)) ||
			call(void AbstractQueuedLongSynchronizer.acquireSharedInterruptibly(int))) &&
		target(s) &&
		args(arg);
	
	pointcut compareAndSetState(AbstractQueuedLongSynchronizer s, long expect, long update) :
		call(boolean AbstractQueuedLongSynchronizer.compareAndSetState(long, long)) &&
		target(s) &&
		args(expect, update);
	
	pointcut release(AbstractQueuedLongSynchronizer s, long arg) :
		call(boolean AbstractQueuedLongSynchronizer.release(long)) &&
		target(s) &&
		args(arg);
	
	pointcut releaseShared(AbstractQueuedLongSynchronizer s, long arg) :
		call(boolean AbstractQueuedLongSynchronizer.releaseShared(long)) &&
		target(s) &&
		args(arg);
	
	pointcut setState(AbstractQueuedLongSynchronizer s, long newState) :
		call(void AbstractQueuedLongSynchronizer.setState(long)) &&
		target(s) &&
		args(newState);
	
	pointcut tryAcquire(AbstractQueuedLongSynchronizer s, long arg) :
		call(boolean AbstractQueuedLongSynchronizer.tryAcquire(long)) &&
		target(s) &&
		args(arg);
	
	pointcut tryAcquireNanos(AbstractQueuedLongSynchronizer s, long arg, long nanosTimeout) :
		call(boolean AbstractQueuedLongSynchronizer.tryAcquireNanos(long, long)) &&
		target(s) &&
		args(arg, nanosTimeout);
	
	pointcut tryAcquireShared(AbstractQueuedLongSynchronizer s, long arg) :
		call(int AbstractQueuedLongSynchronizer.tryAcquireShared(long)) &&
		target(s) &&
		args(arg);
	
	pointcut tryAcquireSharedNanos(AbstractQueuedLongSynchronizer s, long arg, long nanosTimeout) :
		call(boolean AbstractQueuedLongSynchronizer.tryAcquireSharedNanos(long, long)) &&
		target(s) &&
		args(arg, nanosTimeout);
	
	pointcut tryRelease(AbstractQueuedLongSynchronizer s, long arg) :
		call(boolean AbstractQueuedLongSynchronizer.tryRelease(long)) &&
		target(s) &&
		args(arg);
	
	pointcut tryReleaseShared(AbstractQueuedLongSynchronizer s, long arg) :
		call(boolean AbstractQueuedLongSynchronizer.tryReleaseShared(int)) &&
		target(s) &&
		args(arg);
	
	after() returning(AbstractQueuedLongSynchronizer s) : create() {
		Event createEvent = new Event(EventClass.AbstractQueuedLongSynchronizer,
				AbstractQueuedLongSynchronizerEventType.Create,s.toString());
		createEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(createEvent);
	}
	
	before(AbstractQueuedLongSynchronizer s, long arg) : acquire(s, arg) {
		Event acquireEvent = new Event(EventClass.AbstractQueuedLongSynchronizer,
				AbstractQueuedLongSynchronizerEventType.BeforeAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.ARG, arg);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long arg) : acquire(s, arg) {
		Event acquireEvent = new Event(EventClass.AbstractQueuedLongSynchronizer,
				AbstractQueuedLongSynchronizerEventType.AfterAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.ARG, arg);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(AbstractQueuedLongSynchronizer s, long arg) : acquireShared(s, arg) {
		Event acquireEvent = new Event(EventClass.AbstractQueuedLongSynchronizer,
				AbstractQueuedLongSynchronizerEventType.BeforeAcquireShared, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.ARG, arg);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long arg) : acquireShared(s, arg) {
		Event acquireEvent = new Event(EventClass.AbstractQueuedLongSynchronizer,
				AbstractQueuedLongSynchronizerEventType.AfterAcquireShared, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.ARG, arg);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long expect, long update) returning(boolean successful) : 
		compareAndSetState(s, expect, update) {
		
		Event updateEvent = new Event(EventClass.AbstractQueuedLongSynchronizer, 
				AbstractQueuedLongSynchronizerEventType.CompareAndSetState, s.toString());
		updateEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		updateEvent.addValue(StringConstants.EXPECT, expect);
		updateEvent.addValue(StringConstants.UPDATE, update);
		updateEvent.addValue(StringConstants.SUCCESS, successful);
		
		EventQueue.addEvent(updateEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long arg) returning(boolean successful) : release(s, arg) {
		Event releaseEvent = new Event(EventClass.AbstractQueuedLongSynchronizer,
				AbstractQueuedLongSynchronizerEventType.Release, s.toString());
		releaseEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		releaseEvent.addValue(StringConstants.ARG, arg);
		releaseEvent.addValue(StringConstants.SUCCESS, successful);
		
		EventQueue.addEvent(releaseEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long arg) returning(boolean successful) : releaseShared(s, arg) {
		Event releaseEvent = new Event(EventClass.AbstractQueuedLongSynchronizer, 
				AbstractQueuedLongSynchronizerEventType.ReleaseShared, s.toString());
		releaseEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		releaseEvent.addValue(StringConstants.ARG, arg);
		releaseEvent.addValue(StringConstants.SUCCESS, successful);
		
		EventQueue.addEvent(releaseEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long newState) : setState(s, newState) {
		Event updateEvent = new Event(EventClass.AbstractQueuedLongSynchronizer, 
				AbstractQueuedLongSynchronizerEventType.SetState, s.toString());
		updateEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		updateEvent.addValue(StringConstants.NEW_STATE, newState);
		
		EventQueue.addEvent(updateEvent);
	}
	
	before(AbstractQueuedLongSynchronizer s, long arg) : tryAcquire(s, arg) {
		Event acquireEvent = new Event(EventClass.AbstractQueuedLongSynchronizer,
				AbstractQueuedLongSynchronizerEventType.BeforeTryAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.ARG, arg);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long arg) returning(boolean successful) : tryAcquire(s, arg) {
		Event acquireEvent = new Event(EventClass.AbstractQueuedLongSynchronizer, 
				AbstractQueuedLongSynchronizerEventType.AfterTryAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.SUCCESS, successful);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(AbstractQueuedLongSynchronizer s, long arg, long nanosTimeout) : tryAcquireNanos(s, arg, nanosTimeout) {
		Event acquireEvent = new Event(EventClass.AbstractQueuedLongSynchronizer,
				AbstractQueuedLongSynchronizerEventType.BeforeTryAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.ARG, arg);
		acquireEvent.addValue(StringConstants.NANOS, nanosTimeout);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long arg, long nanosTimeout) returning(boolean successful) : 
		tryAcquireNanos(s, arg, nanosTimeout) {
		
		Event acquireEvent = new Event(EventClass.AbstractQueuedLongSynchronizer,
				AbstractQueuedLongSynchronizerEventType.AfterTryAcquire, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.SUCCESS, successful);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long arg) returning(int tryAcquireResult) : tryAcquireShared(s, arg) {
		Event acquireEvent = new Event(EventClass.AbstractQueuedLongSynchronizer, 
				AbstractQueuedLongSynchronizerEventType.AfterTryAcquireShared, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.ARG, arg);
		acquireEvent.addValue(StringConstants.TRY_ACQUIRE_RESULT, tryAcquireResult);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(AbstractQueuedLongSynchronizer s, long arg, long nanosTimeout) : tryAcquireSharedNanos(s, arg, nanosTimeout) {
		Event acquireEvent = new Event(EventClass.AbstractQueuedLongSynchronizer,
				AbstractQueuedLongSynchronizerEventType.BeforeTryAcquireShared, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.ARG, arg);
		acquireEvent.addValue(StringConstants.NANOS, nanosTimeout);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long arg, long nanosTimeout) returning(boolean successful) : 
		tryAcquireSharedNanos(s, arg, nanosTimeout) {
		
		Event acquireEvent = new Event(EventClass.AbstractQueuedLongSynchronizer,
				AbstractQueuedLongSynchronizerEventType.AfterTryAcquireShared, s.toString());
		acquireEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		acquireEvent.addValue(StringConstants.SUCCESS, successful);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long arg) returning(boolean successful) : tryRelease(s, arg) {
		Event releaseEvent = new Event(EventClass.AbstractQueuedLongSynchronizer,
				AbstractQueuedLongSynchronizerEventType.TryRelease, s.toString());
		releaseEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		releaseEvent.addValue(StringConstants.ARG, arg);
		releaseEvent.addValue(StringConstants.SUCCESS, successful);
		
		EventQueue.addEvent(releaseEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long arg) returning(boolean successful) : tryReleaseShared(s, arg) {
		Event releaseEvent = new Event(EventClass.AbstractQueuedLongSynchronizer,
				AbstractQueuedLongSynchronizerEventType.TryReleaseShared, s.toString());
		releaseEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		releaseEvent.addValue(StringConstants.ARG, arg);
		releaseEvent.addValue(StringConstants.SUCCESS, successful);
		
		EventQueue.addEvent(releaseEvent);
	}
}
