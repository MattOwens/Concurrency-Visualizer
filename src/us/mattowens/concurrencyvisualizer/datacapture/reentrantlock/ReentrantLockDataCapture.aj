package us.mattowens.concurrencyvisualizer.datacapture.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.TimeUnit;

public aspect ReentrantLockDataCapture {

	pointcut create() :
		call(ReentrantLock.new());
	
	pointcut createFair(boolean isFair) :
		call(ReentrantLock.new(boolean)) &&
		args(isFair);
	
	pointcut isLocked(ReentrantLock l) :
		call(boolean ReentrantLock.isLocked()) &&
		target(l);
	
	pointcut lockPointcut(ReentrantLock l) :
		call(void ReentrantLock.lock()) &&
		target(l);
	
	pointcut lockInterruptibly(ReentrantLock l) :
		call(void ReentrantLock.lockInterruptibly()) &&
		target(l);
	
	pointcut newCondition(ReentrantLock l) :
		call(Condition ReentrantLock.newCondition()) &&
		target(l);
	
	pointcut tryLock(ReentrantLock l) :
		call(boolean ReentrantLock.tryLock()) &&
		target(l);
	
	pointcut tryLockTimeout(ReentrantLock l, long timeout, TimeUnit unit) :
		call(boolean ReentrantLock.tryLock(long, TimeUnit)) &&
		target(l) &&
		args(timeout, unit);
	
	pointcut unlockPointcut(ReentrantLock l) :
		call(void ReentrantLock.unlock()) &&
		target(l);
	
	
	after() returning(ReentrantLock l) : create() {
		Event createEvent = new Event(EventClass.ReentrantLock, ReentrantLockEventType.Create, l.toString());
		createEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(createEvent);
	}
	
	after(boolean isFair) returning(ReentrantLock l) : createFair(isFair) {
		Event createEvent = new Event(EventClass.ReentrantLock, ReentrantLockEventType.Create, l.toString());
		createEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		createEvent.addValue(StringConstants.FAIR, isFair);
		
		EventQueue.addEvent(createEvent);
	}
	
	after(ReentrantLock l) returning(boolean isLocked) : isLocked(l) {
		Event event = new Event(EventClass.ReentrantLock, ReentrantLockEventType.IsLocked, l.toString());
		event.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		event.addValue(StringConstants.LOCKED, isLocked);
		
		EventQueue.addEvent(event);
	}
	
	before(ReentrantLock l) : lockPointcut(l) {
		Event lockEvent = new Event(EventClass.ReentrantLock, ReentrantLockEventType.BeforeLock, l.toString());
		lockEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(lockEvent);
	}
	
	after(ReentrantLock l) : lockPointcut(l) {
		Event lockEvent = new Event(EventClass.ReentrantLock, ReentrantLockEventType.AfterLock, l.toString());
		lockEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(lockEvent);
	}
	
	before(ReentrantLock l) : lockInterruptibly(l) {
		Event lockEvent = new Event(EventClass.ReentrantLock, ReentrantLockEventType.BeforeLockInterruptibly, l.toString());
		lockEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(lockEvent);
	}
	
	after(ReentrantLock l) : lockPointcut(l) {
		Event lockEvent = new Event(EventClass.ReentrantLock, ReentrantLockEventType.AfterLockInterruptibly, l.toString());
		lockEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(lockEvent);
	}
	
	after(ReentrantLock l) returning(Condition c) : newCondition(l) {
		Event conditionEvent = new Event(EventClass.ReentrantLock, ReentrantLockEventType.NewCondition, l.toString());
		conditionEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		conditionEvent.addValue(StringConstants.CONDITION, c.toString());
		
		EventQueue.addEvent(conditionEvent);
	}
	
	before(ReentrantLock l) : tryLock(l) {
		Event lockEvent = new Event(EventClass.ReentrantLock, ReentrantLockEventType.BeforeTryLock, l.toString());
		lockEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(lockEvent);
	}
	
	after(ReentrantLock l) returning(boolean hasLock) : tryLock(l) {
		Event lockEvent = new Event(EventClass.ReentrantLock, ReentrantLockEventType.AfterTryLock, l.toString());
		lockEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		lockEvent.addValue(StringConstants.SUCCESS, hasLock);
		
		EventQueue.addEvent(lockEvent);
	}
	
	before(ReentrantLock l, long timeout, TimeUnit unit) : tryLockTimeout(l, timeout, unit) {
		Event lockEvent = new Event(EventClass.ReentrantLock, ReentrantLockEventType.AfterTryLock, l.toString());
		lockEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		lockEvent.addValue(StringConstants.TIMEOUT, timeout);
		lockEvent.addValue(StringConstants.TIME_UNIT, unit);
		
		EventQueue.addEvent(lockEvent);
	}
	
	after(ReentrantLock l, long timeout, TimeUnit unit) returning(boolean hasLock) : tryLockTimeout(l, timeout, unit) {
		Event lockEvent = new Event(EventClass.ReentrantLock, ReentrantLockEventType.AfterTryLock, l.toString());
		lockEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		lockEvent.addValue(StringConstants.SUCCESS, hasLock);

		
		EventQueue.addEvent(lockEvent);
	}
	
	before(ReentrantLock l) : unlockPointcut(l) {
		Event unlockEvent = new Event(EventClass.ReentrantLock, ReentrantLockEventType.Unlock, l.toString());
		unlockEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(unlockEvent);
	}
}
