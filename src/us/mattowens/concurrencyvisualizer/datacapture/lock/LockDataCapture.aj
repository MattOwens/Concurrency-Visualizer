package us.mattowens.concurrencyvisualizer.datacapture.lock;

import java.util.concurrent.locks.Lock;

import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.TimeUnit;

public aspect LockDataCapture {

	pointcut lockPointcut(Lock l) :
		call(void Lock.lock()) &&
		target(l);
	
	pointcut lockInterruptibly(Lock l) :
		call(void Lock.lockInterruptibly()) &&
		target(l);
	
	pointcut newCondition(Lock l) :
		call(Condition Lock.newCondition()) &&
		target(l);
	
	pointcut tryLock(Lock l) :
		call(boolean Lock.tryLock()) &&
		target(l);
	
	pointcut tryLockTimeout(Lock l, long time, TimeUnit unit) : 
		call(boolean Lock.tryLock(long, TimeUnit)) &&
		target(l) &&
		args(time, unit);
	
	pointcut unlockPointcut(Lock l) :
		call(void Lock.unlock()) &&
		target(l);
	
	before(Lock l) : lockPointcut(l) {
		LockEvent lockEvent = new LockEvent(l.toString(), LockEventType.BeforeLock);
		lockEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(lockEvent);
	}
	
	after(Lock l) : lockPointcut(l) {
		LockEvent lockEvent = new LockEvent(l.toString(), LockEventType.AfterLock);
		lockEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(lockEvent);
	}
	
	before(Lock l) : lockInterruptibly(l) {
		LockEvent lockEvent = new LockEvent(l.toString(), LockEventType.BeforeLockInterruptibly);
		lockEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(lockEvent);
	}
	
	after(Lock l) : lockInterruptibly(l) {
		LockEvent lockEvent = new LockEvent(l.toString(), LockEventType.AfterLockInterruptibly);
		lockEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(lockEvent);
	}
	
	after(Lock l) returning(Condition c) : newCondition(l) {
		LockEvent newConditionEvent = new LockEvent(l.toString(), LockEventType.NewCondition);
		newConditionEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		newConditionEvent.setConditionDescription(c.toString());
		
		EventQueue.addEvent(newConditionEvent);
	}
	
	before(Lock l) : tryLock(l) {
		LockEvent lockEvent = new LockEvent(l.toString(), LockEventType.BeforeTryLock);
		lockEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(lockEvent);
	}
	
	after(Lock l) returning(boolean hasAccess) : tryLock(l) {
		LockEvent lockEvent = new LockEvent(l.toString(), LockEventType.AfterTryLock);
		lockEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		lockEvent.setHasAccess(hasAccess);
		
		EventQueue.addEvent(lockEvent);
	}
	
	before(Lock l, long time, TimeUnit unit) : tryLockTimeout(l, time, unit) {
		LockEvent lockEvent = new LockEvent(l.toString(), LockEventType.BeforeTryLock);
		lockEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		lockEvent.setTimeout(time);
		lockEvent.setTimeUnit(unit);
		
		EventQueue.addEvent(lockEvent);
	}
	
	after(Lock l, long time, TimeUnit unit) returning(boolean hasAccess) : tryLockTimeout(l, time, unit) {
		LockEvent lockEvent = new LockEvent(l.toString(), LockEventType.AfterTryLock);
		lockEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		lockEvent.setTimeout(time);
		lockEvent.setTimeUnit(unit);
		lockEvent.setHasAccess(hasAccess);
		
		EventQueue.addEvent(lockEvent);
	}
	
	after(Lock l) : unlockPointcut(l) {
		LockEvent unlockEvent = new LockEvent(l.toString(), LockEventType.AfterLock);
		unlockEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(unlockEvent);
	}
}
