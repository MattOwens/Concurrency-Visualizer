package us.mattowens.concurrencyvisualizer.datacapture.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

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
		ReentrantLockEvent createEvent = new ReentrantLockEvent(l.toString(), ReentrantLockEventType.Create);
		createEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(createEvent);
	}
	
	after(boolean isFair) returning(ReentrantLock l) : createFair(isFair) {
		ReentrantLockEvent createEvent = new ReentrantLockEvent(l.toString(), ReentrantLockEventType.Create);
		createEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		createEvent.setFair(isFair);
		
		EventQueue.addEvent(createEvent);
	}
	
	after(ReentrantLock l) returning(boolean isLocked) : isLocked(l) {
		ReentrantLockEvent event = new ReentrantLockEvent(l.toString(), ReentrantLockEventType.IsLocked);
		event.setJoinPointName(thisJoinPoint.getSignature().getName());
		event.setLocked(isLocked);
		
		EventQueue.addEvent(event);
	}
	
	before(ReentrantLock l) : lockPointcut(l) {
		ReentrantLockEvent lockEvent = new ReentrantLockEvent(l.toString(), ReentrantLockEventType.BeforeLock);
		lockEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(lockEvent);
	}
	
	after(ReentrantLock l) : lockPointcut(l) {
		ReentrantLockEvent lockEvent = new ReentrantLockEvent(l.toString(), ReentrantLockEventType.AfterLock);
		lockEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(lockEvent);
	}
	
	before(ReentrantLock l) : lockInterruptibly(l) {
		ReentrantLockEvent lockEvent = new ReentrantLockEvent(l.toString(), ReentrantLockEventType.BeforeLockInterruptibly);
		lockEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(lockEvent);
	}
	
	after(ReentrantLock l) : lockPointcut(l) {
		ReentrantLockEvent lockEvent = new ReentrantLockEvent(l.toString(), ReentrantLockEventType.AfterLockInterruptibly);
		lockEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(lockEvent);
	}
	
	after(ReentrantLock l) returning(Condition c) : newCondition(l) {
		ReentrantLockEvent conditionEvent = new ReentrantLockEvent(l.toString(), ReentrantLockEventType.NewCondition);
		conditionEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		conditionEvent.setNewCondition(c.toString());
		
		EventQueue.addEvent(conditionEvent);
	}
	
	before(ReentrantLock l) : tryLock(l) {
		ReentrantLockEvent lockEvent = new ReentrantLockEvent(l.toString(), ReentrantLockEventType.BeforeTryLock);
		lockEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(lockEvent);
	}
	
	after(ReentrantLock l) returning(boolean hasLock) : tryLock(l) {
		ReentrantLockEvent lockEvent = new ReentrantLockEvent(l.toString(), ReentrantLockEventType.AfterTryLock);
		lockEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		lockEvent.setHasLock(hasLock);
		
		EventQueue.addEvent(lockEvent);
	}
	
	before(ReentrantLock l, long timeout, TimeUnit unit) : tryLockTimeout(l, timeout, unit) {
		ReentrantLockEvent lockEvent = new ReentrantLockEvent(l.toString(), ReentrantLockEventType.BeforeTryLock);
		lockEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		lockEvent.setTimeout(timeout);
		lockEvent.setUnit(unit);
		
		EventQueue.addEvent(lockEvent);
	}
	
	after(ReentrantLock l, long timeout, TimeUnit unit) returning(boolean hasLock) : tryLockTimeout(l, timeout, unit) {
		ReentrantLockEvent lockEvent = new ReentrantLockEvent(l.toString(), ReentrantLockEventType.AfterTryLock);
		lockEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		lockEvent.setHasLock(hasLock);
		lockEvent.setTimeout(timeout);
		lockEvent.setUnit(unit);
		
		EventQueue.addEvent(lockEvent);
	}
	
	before(ReentrantLock l) : unlockPointcut(l) {
		ReentrantLockEvent unlockEvent = new ReentrantLockEvent(l.toString(), ReentrantLockEventType.Unlock);
		unlockEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(unlockEvent);
	}
}
