package us.mattowens.concurrencyvisualizer.datacapture.lock;

import java.util.concurrent.locks.Lock;

import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.TimeUnit;

public aspect LockDataCapture {


	pointcut lockPointcut(Lock l) :
		call(void java.util.concurrent.locks.Lock.lock()) &&
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
		Event lockEvent = new Event(EventClass.Lock, LockEventType.BeforeLock, l.toString());
		lockEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(lockEvent);
	}
	
	after(Lock l) : lockPointcut(l) {
		Event lockEvent = new Event(EventClass.Lock, LockEventType.AfterLock, l.toString());
		lockEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(lockEvent);
	}
	
	before(Lock l) : lockInterruptibly(l) {
		Event lockEvent = new Event(EventClass.Lock, LockEventType.BeforeLockInterruptibly, l.toString());
		lockEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(lockEvent);
	}
	
	after(Lock l) : lockInterruptibly(l) {
		Event lockEvent = new Event(EventClass.Lock, LockEventType.AfterLockInterruptibly, l.toString());
		lockEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(lockEvent);
	}
	
	after(Lock l) returning(Condition c) : newCondition(l) {
		Event newConditionEvent = new Event(EventClass.Lock, LockEventType.NewCondition, l.toString());
		newConditionEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		newConditionEvent.addValue(StringConstants.CONDITION, c.toString());
		
		EventQueue.addEvent(newConditionEvent);
	}
	
	before(Lock l) : tryLock(l) {
		Event lockEvent = new Event(EventClass.Lock, LockEventType.BeforeTryLock, l.toString());
		lockEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(lockEvent);
	}
	
	after(Lock l) returning(boolean hasAccess) : tryLock(l) {
		Event lockEvent = new Event(EventClass.Lock, LockEventType.AfterTryLock, l.toString());
		lockEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		lockEvent.addValue(StringConstants.SUCCESS, hasAccess);
		
		EventQueue.addEvent(lockEvent);
	}
	
	before(Lock l, long time, TimeUnit unit) : tryLockTimeout(l, time, unit) {
		Event lockEvent = new Event(EventClass.Lock, LockEventType.BeforeTryLock, l.toString());
		lockEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		lockEvent.addValue(StringConstants.TIMEOUT, time);
		lockEvent.addValue(StringConstants.TIME_UNIT, unit);
		
		EventQueue.addEvent(lockEvent);
	}
	
	after(Lock l, long time, TimeUnit unit) returning(boolean hasAccess) : tryLockTimeout(l, time, unit) {
		Event lockEvent = new Event(EventClass.Lock, LockEventType.AfterTryLock, l.toString());
		lockEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		lockEvent.addValue(StringConstants.SUCCESS, hasAccess);
		
		EventQueue.addEvent(lockEvent);
	}
	
	after(Lock l) : unlockPointcut(l) {
		Event unlockEvent = new Event(EventClass.Lock, LockEventType.AfterLock, l.toString());
		unlockEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(unlockEvent);
	}
}
