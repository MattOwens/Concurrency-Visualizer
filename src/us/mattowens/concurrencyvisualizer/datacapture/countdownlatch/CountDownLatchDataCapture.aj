package us.mattowens.concurrencyvisualizer.datacapture.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public aspect CountDownLatchDataCapture {

	pointcut create(int count) :
		call(CountDownLatch.new(int)) &&
		args(count);
	
	pointcut await(CountDownLatch l) :
		call(void CountDownLatch.await()) &&
		target(l);
	
	pointcut awaitTimeout(CountDownLatch l, long timeout, TimeUnit unit) :
		call(boolean CountDownLatch.await(long, TimeUnit)) &&
		target(l) &&
		args(timeout, unit);
	
	pointcut countDown(CountDownLatch l) :
		call(void CountDownLatch.countDown()) &&
		target(l);
	
	
	after(int count) returning(CountDownLatch l) : create(count) {
		Event createEvent = new Event(EventClass.CountDownLatch,  
				CountDownLatchEventType.Create, l.toString());
		createEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		createEvent.addValue(StringConstants.COUNT, count);
		
		EventQueue.addEvent(createEvent);
	}
	
	before(CountDownLatch l)  : await(l) {
		Event awaitEvent = new Event(EventClass.CountDownLatch, 
				CountDownLatchEventType.BeforeAwait, l.toString());
		awaitEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(awaitEvent);
	}
	
	after(CountDownLatch l)  : await(l) {
		Event awaitEvent = new Event(EventClass.CountDownLatch,  
				CountDownLatchEventType.AfterAwait, l.toString());
		awaitEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(awaitEvent);
	}
	
	before(CountDownLatch l, long timeout, TimeUnit timeoutUnit)  
		: awaitTimeout(l, timeout, timeoutUnit) {
		Event awaitEvent = new Event(EventClass.CountDownLatch, 
				CountDownLatchEventType.BeforeAwait, l.toString());
		awaitEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		awaitEvent.addValue(StringConstants.TIMEOUT, timeout);
		awaitEvent.addValue(StringConstants.TIME_UNIT, timeoutUnit);
		
		EventQueue.addEvent(awaitEvent);
	}
	
	after(CountDownLatch l, long timeout, TimeUnit timeoutUnit)  
		returning(boolean successful) : awaitTimeout(l, timeout, timeoutUnit) {
			
		Event awaitEvent = new Event(EventClass.CountDownLatch,  
				CountDownLatchEventType.AfterAwait, l.toString());
		awaitEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		awaitEvent.addValue(StringConstants.SUCCESS, successful);
		
		EventQueue.addEvent(awaitEvent);
	}
	
	before(CountDownLatch l) : countDown(l) {
		Event downEvent = new Event(EventClass.CountDownLatch, 
				CountDownLatchEventType.CountDown, l.toString());
		downEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(downEvent);
	}
	 
	 
}
