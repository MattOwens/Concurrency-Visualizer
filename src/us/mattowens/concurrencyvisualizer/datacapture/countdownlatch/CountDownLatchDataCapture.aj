package us.mattowens.concurrencyvisualizer.datacapture.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
		CountDownLatchEvent createEvent = new CountDownLatchEvent(l.toString(), 
				CountDownLatchEventType.Create);
		createEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		createEvent.setCount(count);
		
		EventQueue.addEvent(createEvent);
	}
	
	before(CountDownLatch l)  : await(l) {
		CountDownLatchEvent awaitEvent = new CountDownLatchEvent(l.toString(), 
				CountDownLatchEventType.BeforeAwait);
		awaitEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(awaitEvent);
	}
	
	after(CountDownLatch l)  : await(l) {
		CountDownLatchEvent awaitEvent = new CountDownLatchEvent(l.toString(), 
				CountDownLatchEventType.AfterAwait);
		awaitEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(awaitEvent);
	}
	
	before(CountDownLatch l, long timeout, TimeUnit timeoutUnit)  
		: awaitTimeout(l, timeout, timeoutUnit) {
		CountDownLatchEvent awaitEvent = new CountDownLatchEvent(l.toString(), 
				CountDownLatchEventType.BeforeAwait);
		awaitEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		awaitEvent.setTimeout(timeout);
		awaitEvent.setTimeUnit(timeoutUnit);
		
		EventQueue.addEvent(awaitEvent);
	}
	
	after(CountDownLatch l, long timeout, TimeUnit timeoutUnit)  
		returning(boolean successful) : awaitTimeout(l, timeout, timeoutUnit) {
			
		CountDownLatchEvent awaitEvent = new CountDownLatchEvent(l.toString(), 
				CountDownLatchEventType.AfterAwait);
		awaitEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		awaitEvent.setTimeout(timeout);
		awaitEvent.setTimeUnit(timeoutUnit);
		awaitEvent.setSuccessful(successful);
		
		EventQueue.addEvent(awaitEvent);
	}
	
	before(CountDownLatch l) : countDown(l) {
		CountDownLatchEvent downEvent = new CountDownLatchEvent(l.toString(),
				CountDownLatchEventType.CountDown);
		downEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(downEvent);
	}
	 
	 
}
