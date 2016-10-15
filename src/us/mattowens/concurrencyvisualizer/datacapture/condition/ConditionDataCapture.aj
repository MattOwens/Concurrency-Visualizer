package us.mattowens.concurrencyvisualizer.datacapture.condition;

import java.util.concurrent.locks.Condition;

import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

import java.util.concurrent.TimeUnit;
import java.util.Date;

public aspect ConditionDataCapture {

	pointcut await(Condition c) :
		call(void Condition.await()) &&
		target(c);
	
	pointcut awaitTimeout(Condition c, long time, TimeUnit unit) :
		call(boolean Condition.await(long, TimeUnit)) &&
		target(c) &&
		args(time, unit);
	
	pointcut awaitNanos(Condition c, long nanosTimeout) :
		call(boolean Condition.awaitNanos(long)) &&
		target(c) &&
		args(nanosTimeout);
	
	pointcut awaitUninterruptibly(Condition c) :
		call(void Condition.awaitUninterruptibly()) &&
		target(c);
	
	pointcut awaitUntil(Condition c, Date deadline) :
		call(boolean Condition.awaitUntil(Date)) &&
		target(c) &&
		args(deadline);
	
	pointcut signal(Condition c) :
		call(void Condition.signal()) &&
		target(c);
	
	pointcut signalAll(Condition c) :
		call(void Condition.SignalAll()) &&
		target(c);
	
	
	before(Condition c) : await(c) {
		ConditionEvent awaitEvent = new ConditionEvent(c.toString(), ConditionEventType.BeforeAwait);
		awaitEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(awaitEvent);
	}
	
	after(Condition c) : await(c) {
		ConditionEvent awaitEvent = new ConditionEvent(c.toString(), ConditionEventType.AfterAwait);
		awaitEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(awaitEvent);
	}
	
	before(Condition c, long time, TimeUnit unit) : awaitTimeout(c, time, unit) {
		ConditionEvent awaitEvent = new ConditionEvent(c.toString(), ConditionEventType.BeforeAwait);
		awaitEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		awaitEvent.setTime(time);
		awaitEvent.setTimeUnit(unit);
		
		EventQueue.addEvent(awaitEvent);
	}
	
	after(Condition c, long time, TimeUnit unit) returning(boolean hasAccess) : awaitTimeout(c, time, unit) {
		ConditionEvent awaitEvent = new ConditionEvent(c.toString(), ConditionEventType.AfterAwait);
		awaitEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		awaitEvent.setTime(time);
		awaitEvent.setTimeUnit(unit);
		awaitEvent.setHasAccess(hasAccess);
		
		EventQueue.addEvent(awaitEvent);
	}
	
	before(Condition c, long nanosTimeout) : awaitNanos(c, nanosTimeout) {
		ConditionEvent awaitEvent = new ConditionEvent(c.toString(), ConditionEventType.BeforeAwait);
		awaitEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		awaitEvent.setNanosTimeout(nanosTimeout);
		
		EventQueue.addEvent(awaitEvent);
	}
	
	after(Condition c, long nanosTimeout)  returning(long timeRemaining) : awaitNanos(c, nanosTimeout) {
		ConditionEvent awaitEvent = new ConditionEvent(c.toString(), ConditionEventType.BeforeAwait);
		awaitEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		awaitEvent.setNanosTimeout(nanosTimeout);
		awaitEvent.setTimeRemaining(timeRemaining);
		
		EventQueue.addEvent(awaitEvent);
	}
	
	before(Condition c) : awaitUninterruptibly(c) {
		ConditionEvent awaitEvent = new ConditionEvent(c.toString(), ConditionEventType.BeforeAwaitUninterruptibly);
		awaitEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(awaitEvent);
	}
	
	after(Condition c) : awaitUninterruptibly(c) {
		ConditionEvent awaitEvent = new ConditionEvent(c.toString(), ConditionEventType.AfterAwaitUninterruptibly);
		awaitEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(awaitEvent);
	}
	
	before(Condition c, Date deadline) : awaitUntil(c, deadline) {
		ConditionEvent awaitEvent = new ConditionEvent(c.toString(), ConditionEventType.BeforeAwaitUntil);
		awaitEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		awaitEvent.setDeadline(deadline);
		
		EventQueue.addEvent(awaitEvent);
	}
	
	after(Condition c, Date deadline)  returning(boolean hasAccess) : awaitUntil(c, deadline) {
		ConditionEvent awaitEvent = new ConditionEvent(c.toString(), ConditionEventType.BeforeAwaitUntil);
		awaitEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		awaitEvent.setDeadline(deadline);
		awaitEvent.setHasAccess(hasAccess);
		
		EventQueue.addEvent(awaitEvent);
	}
	
	before(Condition c) : signal(c) {
		ConditionEvent signalEvent = new ConditionEvent(c.toString(), ConditionEventType.Signal);
		signalEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(signalEvent);
	}
	
	before(Condition c) : signalAll(c) {
		ConditionEvent signalEvent = new ConditionEvent(c.toString(), ConditionEventType.SignalAll);
		signalEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(signalEvent);
	}
}
