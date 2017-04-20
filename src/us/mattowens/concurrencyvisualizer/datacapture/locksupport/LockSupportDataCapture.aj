package us.mattowens.concurrencyvisualizer.datacapture.locksupport;

import java.util.concurrent.locks.LockSupport;

import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public aspect LockSupportDataCapture {
	
	pointcut park() :
		call(static void LockSupport.park());
	
	pointcut parkBlocker(Object blocker) :
		call(static void LockSupport.park(Object)) &&
		args(blocker);
	
	pointcut parkNanos(long nanos) :
		call(static void LockSupport.parkNanos(long)) &&
		args(nanos);
	
	pointcut parkNanosBlocker(Object blocker, long nanos) :
		call(static void LockSupport.parkNanos(Object, long)) &&
		args(blocker, nanos);
	
	pointcut parkUntil(long deadline) :
		call(static void LockSupport.parkUntil(long)) &&
		args(deadline);
	
	pointcut parkUntilBlocker(Object blocker, long deadline) :
		call(static void LockSupport.parkUntil(Object, long)) &&
		args(blocker, deadline);
	
	pointcut unpark(Thread thread) :
		call(static void LockSupport.unpark(Thread)) &&
		args(thread);
	
	
	
	before() : park() {
		Event parkEvent = new Event(EventClass.LockSupport, 
				LockSupportEventType.BeforePark, Thread.currentThread().getName());
		parkEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		EventQueue.addEvent(parkEvent);
	}
	
	after() : park() {
		Event parkEvent = new Event(EventClass.LockSupport,
				LockSupportEventType.AfterPark, Thread.currentThread().getName());
		parkEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		EventQueue.addEvent(parkEvent);
	}
	
	before(Object blocker) : parkBlocker(blocker) {
		Event parkEvent = new Event(EventClass.LockSupport,
				LockSupportEventType.BeforePark, Thread.currentThread().getName());
		parkEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		parkEvent.addValue(StringConstants.BLOCKER, blocker);
		EventQueue.addEvent(parkEvent);
	}
	
	after(Object blocker) : parkBlocker(blocker) {
		Event parkEvent = new Event(EventClass.LockSupport,
				LockSupportEventType.AfterPark, Thread.currentThread().getName());
		parkEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		parkEvent.addValue(StringConstants.BLOCKER, blocker);
		EventQueue.addEvent(parkEvent);
	}
	
	before(long nanos) : parkNanos(nanos) {
		Event parkEvent = new Event(EventClass.LockSupport,
				LockSupportEventType.BeforePark, Thread.currentThread().getName());
		parkEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		parkEvent.addValue(StringConstants.NANOS, nanos);
		EventQueue.addEvent(parkEvent);
	}
	
	after(long nanos) : parkNanos(nanos) {
		Event parkEvent = new Event(EventClass.LockSupport,
				LockSupportEventType.AfterPark, Thread.currentThread().getName());
		parkEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		parkEvent.addValue(StringConstants.NANOS, nanos);
		EventQueue.addEvent(parkEvent);
	}
	
	before(Object blocker, long nanos) : parkNanosBlocker(blocker, nanos) {
		Event parkEvent = new Event(EventClass.LockSupport,
				LockSupportEventType.BeforePark, Thread.currentThread().getName());
		parkEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		parkEvent.addValue(StringConstants.BLOCKER, blocker);
		parkEvent.addValue(StringConstants.NANOS, nanos);
		EventQueue.addEvent(parkEvent);
	}
	
	after(Object blocker, long nanos) : parkNanosBlocker(blocker, nanos) {
		Event parkEvent = new Event(EventClass.LockSupport,
				LockSupportEventType.AfterPark, Thread.currentThread().getName());
		parkEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		parkEvent.addValue(StringConstants.BLOCKER, blocker);
		parkEvent.addValue(StringConstants.NANOS, nanos);
		EventQueue.addEvent(parkEvent);
	}
	
	before(long deadline) : parkUntil(deadline) {
		Event parkEvent = new Event(EventClass.LockSupport,
				LockSupportEventType.BeforeParkUntil, Thread.currentThread().getName());
		parkEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		parkEvent.addValue(StringConstants.DEADLINE, deadline);
		EventQueue.addEvent(parkEvent);
	}
	
	after(long deadline) : parkUntil(deadline) {
		Event parkEvent = new Event(EventClass.LockSupport,
				LockSupportEventType.AfterParkUntil, Thread.currentThread().getName());
		parkEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		parkEvent.addValue(StringConstants.DEADLINE, deadline);
		EventQueue.addEvent(parkEvent);
	}
	
	before(Object blocker, long deadline) : parkUntilBlocker(blocker, deadline) {
		Event parkEvent = new Event(EventClass.LockSupport,
				LockSupportEventType.BeforeParkUntil, Thread.currentThread().getName());
		parkEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		parkEvent.addValue(StringConstants.BLOCKER, blocker);
		parkEvent.addValue(StringConstants.DEADLINE, deadline);
		EventQueue.addEvent(parkEvent);
	}
	
	after(Object blocker, long deadline) : parkUntilBlocker(blocker, deadline) {
		Event parkEvent = new Event(EventClass.LockSupport,
				LockSupportEventType.AfterParkUntil, Thread.currentThread().getName());
		parkEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		parkEvent.addValue(StringConstants.BLOCKER, blocker);
		parkEvent.addValue(StringConstants.DEADLINE, deadline);
		EventQueue.addEvent(parkEvent);
	}
	
	before(Thread thread) : unpark(thread) {
		Event unparkEvent = new Event(EventClass.LockSupport, 
				LockSupportEventType.Unpark, Thread.currentThread().getName());
		unparkEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		unparkEvent.addValue(StringConstants.THREAD, thread);
		EventQueue.addEvent(unparkEvent);
	}
	
	

}
