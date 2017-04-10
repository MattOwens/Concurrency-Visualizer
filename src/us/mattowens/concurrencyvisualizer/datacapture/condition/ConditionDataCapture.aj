package us.mattowens.concurrencyvisualizer.datacapture.condition;

import java.util.concurrent.locks.Condition;

import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
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
		Event awaitEvent = new Event(EventClass.Condition, ConditionEventType.BeforeAwait, c.toString());
		awaitEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(awaitEvent);
	}
	
	after(Condition c) : await(c) {
		Event awaitEvent = new Event(EventClass.Condition, ConditionEventType.AfterAwait, c.toString());
		awaitEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(awaitEvent);
	}
	
	before(Condition c, long time, TimeUnit unit) : awaitTimeout(c, time, unit) {
		Event awaitEvent = new Event(EventClass.Condition, ConditionEventType.BeforeAwait, c.toString());
		awaitEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		awaitEvent.addValue(StringConstants.TIMEOUT, time);
		awaitEvent.addValue(StringConstants.TIME_UNIT, unit);
		
		EventQueue.addEvent(awaitEvent);
	}
	
	after(Condition c, long time, TimeUnit unit) returning(boolean hasAccess) : awaitTimeout(c, time, unit) {
		Event awaitEvent = new Event(EventClass.Condition, ConditionEventType.AfterAwait, c.toString());
		awaitEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		awaitEvent.addValue(StringConstants.SUCCESS, hasAccess);
		
		EventQueue.addEvent(awaitEvent);
	}
	
	before(Condition c, long nanosTimeout) : awaitNanos(c, nanosTimeout) {
		Event awaitEvent = new Event(EventClass.Condition, ConditionEventType.BeforeAwait, c.toString());
		awaitEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		awaitEvent.addValue(StringConstants.NANOS, nanosTimeout);
		
		EventQueue.addEvent(awaitEvent);
	}
	
	after(Condition c, long nanosTimeout)  returning(long timeRemaining) : awaitNanos(c, nanosTimeout) {
		Event awaitEvent = new Event(EventClass.Condition, ConditionEventType.BeforeAwait, c.toString());
		awaitEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		awaitEvent.addValue(StringConstants.NANOS, nanosTimeout);
		awaitEvent.addValue(StringConstants.TIME_REMAINING, timeRemaining);
		
		EventQueue.addEvent(awaitEvent);
	}
	
	before(Condition c) : awaitUninterruptibly(c) {
		Event awaitEvent = new Event(EventClass.Condition, ConditionEventType.BeforeAwaitUninterruptibly, c.toString());
		awaitEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(awaitEvent);
	}
	
	after(Condition c) : awaitUninterruptibly(c) {
		Event awaitEvent = new Event(EventClass.Condition, ConditionEventType.AfterAwaitUninterruptibly, c.toString());
		awaitEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(awaitEvent);
	}
	
	before(Condition c, Date deadline) : awaitUntil(c, deadline) {
		Event awaitEvent = new Event(EventClass.Condition, ConditionEventType.BeforeAwaitUntil, c.toString());
		awaitEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		awaitEvent.addValue(StringConstants.DEADLINE, deadline);
		
		EventQueue.addEvent(awaitEvent);
	}
	
	after(Condition c, Date deadline)  returning(boolean hasAccess) : awaitUntil(c, deadline) {
		Event awaitEvent = new Event(EventClass.Condition, ConditionEventType.BeforeAwaitUntil, c.toString());
		awaitEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		awaitEvent.addValue(StringConstants.SUCCESS, hasAccess);
		
		EventQueue.addEvent(awaitEvent);
	}
	
	before(Condition c) : signal(c) {
		Event signalEvent = new Event(EventClass.Condition, ConditionEventType.Signal, c.toString());
		signalEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(signalEvent);
	}
	
	before(Condition c) : signalAll(c) {
		Event signalEvent = new Event(EventClass.Condition, ConditionEventType.SignalAll, c.toString());
		signalEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(signalEvent);
	}
}
