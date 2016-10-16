package us.mattowens.concurrencyvisualizer.datacapture.abstractqueuedlongsynchronizer;

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

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
		AbstractQueuedLongSynchronizerEvent createEvent = new AbstractQueuedLongSynchronizerEvent(s.toString(),
				AbstractQueuedLongSynchronizerEventType.Create);
		createEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(createEvent);
	}
	
	before(AbstractQueuedLongSynchronizer s, long arg) : acquire(s, arg) {
		AbstractQueuedLongSynchronizerEvent acquireEvent = new AbstractQueuedLongSynchronizerEvent(s.toString(),
				AbstractQueuedLongSynchronizerEventType.BeforeAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setArg(arg);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long arg) : acquire(s, arg) {
		AbstractQueuedLongSynchronizerEvent acquireEvent = new AbstractQueuedLongSynchronizerEvent(s.toString(),
				AbstractQueuedLongSynchronizerEventType.AfterAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setArg(arg);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(AbstractQueuedLongSynchronizer s, long arg) : acquireShared(s, arg) {
		AbstractQueuedLongSynchronizerEvent acquireEvent = new AbstractQueuedLongSynchronizerEvent(s.toString(),
				AbstractQueuedLongSynchronizerEventType.BeforeAcquireShared);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setArg(arg);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long arg) : acquireShared(s, arg) {
		AbstractQueuedLongSynchronizerEvent acquireEvent = new AbstractQueuedLongSynchronizerEvent(s.toString(),
				AbstractQueuedLongSynchronizerEventType.AfterAcquireShared);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setArg(arg);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long expect, long update) returning(boolean successful) : 
		compareAndSetState(s, expect, update) {
		
		AbstractQueuedLongSynchronizerEvent updateEvent = new AbstractQueuedLongSynchronizerEvent(s.toString(),
				AbstractQueuedLongSynchronizerEventType.CompareAndSetState);
		updateEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		updateEvent.setExpect(expect);
		updateEvent.setUpdate(update);
		updateEvent.setSuccessful(successful);
		
		EventQueue.addEvent(updateEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long arg) returning(boolean successful) : release(s, arg) {
		AbstractQueuedLongSynchronizerEvent releaseEvent = new AbstractQueuedLongSynchronizerEvent(s.toString(),
				AbstractQueuedLongSynchronizerEventType.Release);
		releaseEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		releaseEvent.setArg(arg);
		releaseEvent.setSuccessful(successful);
		
		EventQueue.addEvent(releaseEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long arg) returning(boolean successful) : releaseShared(s, arg) {
		AbstractQueuedLongSynchronizerEvent releaseEvent = new AbstractQueuedLongSynchronizerEvent(s.toString(),
				AbstractQueuedLongSynchronizerEventType.ReleaseShared);
		releaseEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		releaseEvent.setArg(arg);
		releaseEvent.setSuccessful(successful);
		
		EventQueue.addEvent(releaseEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long newState) : setState(s, newState) {
		AbstractQueuedLongSynchronizerEvent updateEvent = new AbstractQueuedLongSynchronizerEvent(s.toString(),
				AbstractQueuedLongSynchronizerEventType.CompareAndSetState);
		updateEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		updateEvent.setNewState(newState);
		
		EventQueue.addEvent(updateEvent);
	}
	
	before(AbstractQueuedLongSynchronizer s, long arg) : tryAcquire(s, arg) {
		AbstractQueuedLongSynchronizerEvent acquireEvent = new AbstractQueuedLongSynchronizerEvent(s.toString(),
				AbstractQueuedLongSynchronizerEventType.BeforeTryAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setArg(arg);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long arg) returning(boolean successful) : tryAcquire(s, arg) {
		AbstractQueuedLongSynchronizerEvent acquireEvent = new AbstractQueuedLongSynchronizerEvent(s.toString(),
				AbstractQueuedLongSynchronizerEventType.AfterTryAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setArg(arg);
		acquireEvent.setSuccessful(successful);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(AbstractQueuedLongSynchronizer s, long arg, long nanosTimeout) : tryAcquireNanos(s, arg, nanosTimeout) {
		AbstractQueuedLongSynchronizerEvent acquireEvent = new AbstractQueuedLongSynchronizerEvent(s.toString(),
				AbstractQueuedLongSynchronizerEventType.BeforeTryAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setArg(arg);
		acquireEvent.setNanosTimeout(nanosTimeout);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long arg, long nanosTimeout) returning(boolean successful) : 
		tryAcquireNanos(s, arg, nanosTimeout) {
		
		AbstractQueuedLongSynchronizerEvent acquireEvent = new AbstractQueuedLongSynchronizerEvent(s.toString(),
				AbstractQueuedLongSynchronizerEventType.AfterTryAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setArg(arg);
		acquireEvent.setNanosTimeout(nanosTimeout);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long arg) returning(int tryAcquireResult) : tryAcquireShared(s, arg) {
		AbstractQueuedLongSynchronizerEvent acquireEvent = new AbstractQueuedLongSynchronizerEvent(s.toString(),
				AbstractQueuedLongSynchronizerEventType.AfterTryAcquireShared);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setArg(arg);
		acquireEvent.setTryAcquireResult(tryAcquireResult);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(AbstractQueuedLongSynchronizer s, long arg, long nanosTimeout) : tryAcquireSharedNanos(s, arg, nanosTimeout) {
		AbstractQueuedLongSynchronizerEvent acquireEvent = new AbstractQueuedLongSynchronizerEvent(s.toString(),
				AbstractQueuedLongSynchronizerEventType.BeforeTryAcquireShared);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setArg(arg);
		acquireEvent.setNanosTimeout(nanosTimeout);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long arg, long nanosTimeout) returning(boolean successful) : 
		tryAcquireSharedNanos(s, arg, nanosTimeout) {
		
		AbstractQueuedLongSynchronizerEvent acquireEvent = new AbstractQueuedLongSynchronizerEvent(s.toString(),
				AbstractQueuedLongSynchronizerEventType.AfterTryAcquireShared);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setArg(arg);
		acquireEvent.setNanosTimeout(nanosTimeout);
		acquireEvent.setSuccessful(successful);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long arg) returning(boolean successful) : tryRelease(s, arg) {
		AbstractQueuedLongSynchronizerEvent releaseEvent = new AbstractQueuedLongSynchronizerEvent(s.toString(),
				AbstractQueuedLongSynchronizerEventType.TryRelease);
		releaseEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		releaseEvent.setArg(arg);
		
		EventQueue.addEvent(releaseEvent);
	}
	
	after(AbstractQueuedLongSynchronizer s, long arg) returning(boolean successful) : tryReleaseShared(s, arg) {
		AbstractQueuedLongSynchronizerEvent releaseEvent = new AbstractQueuedLongSynchronizerEvent(s.toString(),
				AbstractQueuedLongSynchronizerEventType.TryReleaseShared);
		releaseEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		releaseEvent.setArg(arg);
		
		EventQueue.addEvent(releaseEvent);
	}
}
