package us.mattowens.concurrencyvisualizer.datacapture.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public aspect CyclicBarrierDataCapture {

	pointcut create(int parties) :
		call(CyclicBarrier.new(int)) &&
		args(parties);
	
	pointcut createAction(int parties, Runnable action) :
		call(CyclicBarrier.new(int, Runnable)) &&
		args(parties, action);
	
	pointcut await(CyclicBarrier b) :
		call(int CyclicBarrier.await()) &&
		target(b);
	
	pointcut awaitTimeout(CyclicBarrier b, long timeout, TimeUnit unit) :
		call(int CyclicBarrier.await(long, TimeUnit)) &&
		target(b) &&
		args(timeout, unit);
	
	pointcut reset(CyclicBarrier b) :
		call(void CyclicBarrier.reset()) &&
		target(b);
	
	
	after(int parties) returning(CyclicBarrier b) : create(parties) {
		CyclicBarrierEvent createEvent = new CyclicBarrierEvent(b.toString(), 
				CyclicBarrierEventType.Create);
		createEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		createEvent.setParties(parties);
		
		EventQueue.addEvent(createEvent);
	}
	
	after(int parties, Runnable action) returning(CyclicBarrier b) : 
		createAction(parties, action) {
		
		CyclicBarrierEvent createEvent = new CyclicBarrierEvent(b.toString(), 
				CyclicBarrierEventType.Create);
		createEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		createEvent.setParties(parties);
		createEvent.setRunnable(action.toString());
		
		EventQueue.addEvent(createEvent);
	}
	
	before(CyclicBarrier b) : await(b) {
		CyclicBarrierEvent awaitEvent = new CyclicBarrierEvent(b.toString(), 
				CyclicBarrierEventType.BeforeAwait);
		awaitEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(awaitEvent);
	}
	
	after(CyclicBarrier b) returning(int arrivalIndex): await(b) {
		CyclicBarrierEvent awaitEvent = new CyclicBarrierEvent(b.toString(), 
				CyclicBarrierEventType.AfterAwait);
		awaitEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		awaitEvent.setArrivalIndex(arrivalIndex);
		
		EventQueue.addEvent(awaitEvent);
	}
	
	before(CyclicBarrier b, long timeout, TimeUnit unit) : 
		awaitTimeout(b, timeout, unit) {
		
		CyclicBarrierEvent awaitEvent = new CyclicBarrierEvent(b.toString(), 
				CyclicBarrierEventType.BeforeAwait);
		awaitEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		awaitEvent.setTimeout(timeout);
		awaitEvent.setTimeoutUnit(unit);
		
		EventQueue.addEvent(awaitEvent);
	}
	
	after(CyclicBarrier b, long timeout, TimeUnit unit) returning(int arrivalIndex) : 
		awaitTimeout(b, timeout, unit) {
		
		CyclicBarrierEvent awaitEvent = new CyclicBarrierEvent(b.toString(), 
				CyclicBarrierEventType.AfterAwait);
		awaitEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		awaitEvent.setTimeout(timeout);
		awaitEvent.setTimeoutUnit(unit);
		awaitEvent.setArrivalIndex(arrivalIndex);
		
		EventQueue.addEvent(awaitEvent);
	}
	
	before(CyclicBarrier b) : reset(b) {
		CyclicBarrierEvent resetEvent = new CyclicBarrierEvent(b.toString(), 
				CyclicBarrierEventType.Reset);
		resetEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(resetEvent);
	}
	

}
