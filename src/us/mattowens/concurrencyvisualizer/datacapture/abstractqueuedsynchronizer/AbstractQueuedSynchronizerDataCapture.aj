package us.mattowens.concurrencyvisualizer.datacapture.abstractqueuedsynchronizer;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

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
		AbstractQueuedSynchronizerEvent createEvent = new AbstractQueuedSynchronizerEvent(s.toString(),
				AbstractQueuedSynchronizerEventType.Create);
		createEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(createEvent);
	}
	
	before(AbstractQueuedSynchronizer s, int arg) : acquire(s, arg) {
		AbstractQueuedSynchronizerEvent acquireEvent = new AbstractQueuedSynchronizerEvent(s.toString(),
				AbstractQueuedSynchronizerEventType.BeforeAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setArg(arg);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int arg) : acquire(s, arg) {
		AbstractQueuedSynchronizerEvent acquireEvent = new AbstractQueuedSynchronizerEvent(s.toString(),
				AbstractQueuedSynchronizerEventType.AfterAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setArg(arg);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(AbstractQueuedSynchronizer s, int arg) : acquireShared(s, arg) {
		AbstractQueuedSynchronizerEvent acquireEvent = new AbstractQueuedSynchronizerEvent(s.toString(),
				AbstractQueuedSynchronizerEventType.BeforeAcquireShared);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setArg(arg);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int arg) : acquireShared(s, arg) {
		AbstractQueuedSynchronizerEvent acquireEvent = new AbstractQueuedSynchronizerEvent(s.toString(),
				AbstractQueuedSynchronizerEventType.AfterAcquireShared);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setArg(arg);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int expect, int update) returning(boolean successful) : 
		compareAndSetState(s, expect, update) {
		
		AbstractQueuedSynchronizerEvent updateEvent = new AbstractQueuedSynchronizerEvent(s.toString(),
				AbstractQueuedSynchronizerEventType.CompareAndSetState);
		updateEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		updateEvent.setExpect(expect);
		updateEvent.setUpdate(update);
		updateEvent.setSuccessful(successful);
		
		EventQueue.addEvent(updateEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int arg) returning(boolean successful) : release(s, arg) {
		AbstractQueuedSynchronizerEvent releaseEvent = new AbstractQueuedSynchronizerEvent(s.toString(),
				AbstractQueuedSynchronizerEventType.Release);
		releaseEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		releaseEvent.setArg(arg);
		releaseEvent.setSuccessful(successful);
		
		EventQueue.addEvent(releaseEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int arg) returning(boolean successful) : releaseShared(s, arg) {
		AbstractQueuedSynchronizerEvent releaseEvent = new AbstractQueuedSynchronizerEvent(s.toString(),
				AbstractQueuedSynchronizerEventType.ReleaseShared);
		releaseEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		releaseEvent.setArg(arg);
		releaseEvent.setSuccessful(successful);
		
		EventQueue.addEvent(releaseEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int newState) : setState(s, newState) {
		AbstractQueuedSynchronizerEvent updateEvent = new AbstractQueuedSynchronizerEvent(s.toString(),
				AbstractQueuedSynchronizerEventType.CompareAndSetState);
		updateEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		updateEvent.setNewState(newState);
		
		EventQueue.addEvent(updateEvent);
	}
	
	before(AbstractQueuedSynchronizer s, int arg) : tryAcquire(s, arg) {
		AbstractQueuedSynchronizerEvent acquireEvent = new AbstractQueuedSynchronizerEvent(s.toString(),
				AbstractQueuedSynchronizerEventType.BeforeTryAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setArg(arg);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int arg) returning(boolean successful) : tryAcquire(s, arg) {
		AbstractQueuedSynchronizerEvent acquireEvent = new AbstractQueuedSynchronizerEvent(s.toString(),
				AbstractQueuedSynchronizerEventType.AfterTryAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setArg(arg);
		acquireEvent.setSuccessful(successful);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(AbstractQueuedSynchronizer s, int arg, long nanosTimeout) : tryAcquireNanos(s, arg, nanosTimeout) {
		AbstractQueuedSynchronizerEvent acquireEvent = new AbstractQueuedSynchronizerEvent(s.toString(),
				AbstractQueuedSynchronizerEventType.BeforeTryAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setArg(arg);
		acquireEvent.setNanosTimeout(nanosTimeout);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int arg, long nanosTimeout) returning(boolean successful) : 
		tryAcquireNanos(s, arg, nanosTimeout) {
		
		AbstractQueuedSynchronizerEvent acquireEvent = new AbstractQueuedSynchronizerEvent(s.toString(),
				AbstractQueuedSynchronizerEventType.AfterTryAcquire);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setArg(arg);
		acquireEvent.setNanosTimeout(nanosTimeout);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int arg) returning(int tryAcquireResult) : tryAcquireShared(s, arg) {
		AbstractQueuedSynchronizerEvent acquireEvent = new AbstractQueuedSynchronizerEvent(s.toString(),
				AbstractQueuedSynchronizerEventType.AfterTryAcquireShared);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setArg(arg);
		acquireEvent.setTryAcquireResult(tryAcquireResult);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	before(AbstractQueuedSynchronizer s, int arg, long nanosTimeout) : tryAcquireSharedNanos(s, arg, nanosTimeout) {
		AbstractQueuedSynchronizerEvent acquireEvent = new AbstractQueuedSynchronizerEvent(s.toString(),
				AbstractQueuedSynchronizerEventType.BeforeTryAcquireShared);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setArg(arg);
		acquireEvent.setNanosTimeout(nanosTimeout);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int arg, long nanosTimeout) returning(boolean successful) : 
		tryAcquireSharedNanos(s, arg, nanosTimeout) {
		
		AbstractQueuedSynchronizerEvent acquireEvent = new AbstractQueuedSynchronizerEvent(s.toString(),
				AbstractQueuedSynchronizerEventType.AfterTryAcquireShared);
		acquireEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		acquireEvent.setArg(arg);
		acquireEvent.setNanosTimeout(nanosTimeout);
		acquireEvent.setSuccessful(successful);
		
		EventQueue.addEvent(acquireEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int arg) returning(boolean successful) : tryRelease(s, arg) {
		AbstractQueuedSynchronizerEvent releaseEvent = new AbstractQueuedSynchronizerEvent(s.toString(),
				AbstractQueuedSynchronizerEventType.TryRelease);
		releaseEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		releaseEvent.setArg(arg);
		
		EventQueue.addEvent(releaseEvent);
	}
	
	after(AbstractQueuedSynchronizer s, int arg) returning(boolean successful) : tryReleaseShared(s, arg) {
		AbstractQueuedSynchronizerEvent releaseEvent = new AbstractQueuedSynchronizerEvent(s.toString(),
				AbstractQueuedSynchronizerEventType.TryReleaseShared);
		releaseEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		releaseEvent.setArg(arg);
		
		EventQueue.addEvent(releaseEvent);
	}
}
