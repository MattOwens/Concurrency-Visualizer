package us.mattowens.concurrencyvisualizer.datacapture.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
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
		Event createEvent = new Event(EventClass.CyclicBarrier, 
				CyclicBarrierEventType.Create, b.toString());
		createEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		createEvent.addValue(StringConstants.PARTIES, parties);
		
		EventQueue.addEvent(createEvent);
	}
	
	after(int parties, Runnable action) returning(CyclicBarrier b) : 
		createAction(parties, action) {
		
		Event createEvent = new Event(EventClass.CyclicBarrier, 
				CyclicBarrierEventType.Create, b.toString());
		createEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		createEvent.addValue(StringConstants.PARTIES, parties);
		createEvent.addValue(StringConstants.RUNNABLE, action.toString());
		
		EventQueue.addEvent(createEvent);
	}
	
	before(CyclicBarrier b) : await(b) {
		Event awaitEvent = new Event(EventClass.CyclicBarrier, 
				CyclicBarrierEventType.BeforeAwait, b.toString());
		awaitEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(awaitEvent);
	}
	
	after(CyclicBarrier b) returning(int arrivalIndex): await(b) {
		Event awaitEvent = new Event(EventClass.CyclicBarrier, 
				CyclicBarrierEventType.AfterAwait, b.toString());
		awaitEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		awaitEvent.addValue(StringConstants.ARRIVAL, arrivalIndex);
		
		EventQueue.addEvent(awaitEvent);
	}
	
	before(CyclicBarrier b, long timeout, TimeUnit unit) : 
		awaitTimeout(b, timeout, unit) {
		
		Event awaitEvent = new Event(EventClass.CyclicBarrier, 
				CyclicBarrierEventType.BeforeAwait, b.toString());
		awaitEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		awaitEvent.addValue(StringConstants.TIMEOUT, timeout);
		awaitEvent.addValue(StringConstants.TIME_UNIT, unit);
		
		EventQueue.addEvent(awaitEvent);
	}
	
	after(CyclicBarrier b, long timeout, TimeUnit unit) returning(int arrivalIndex) : 
		awaitTimeout(b, timeout, unit) {
		
		Event awaitEvent = new Event(EventClass.CyclicBarrier, 
				CyclicBarrierEventType.AfterAwait, b.toString());
		awaitEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		awaitEvent.addValue(StringConstants.ARRIVAL, arrivalIndex);
		
		EventQueue.addEvent(awaitEvent);
	}
	
	before(CyclicBarrier b) : reset(b) {
		Event resetEvent = new Event(EventClass.CyclicBarrier,  
				CyclicBarrierEventType.Reset, b.toString());
		resetEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(resetEvent);
	}
	

}
