package us.mattowens.concurrencyvisualizer.datacapture.reentrantreadwritelock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public aspect ReentrantReadWriteLockDataCapture {

	/*
	 * Parent class
	 */
	pointcut create() :
		call(ReentrantReadWriteLock.new());
	
	pointcut createFair(boolean fair) :
		call(ReentrantReadWriteLock.new(boolean)) &&
		args(fair);
	
	pointcut getReadLock(ReentrantReadWriteLock l) :
		call(ReentrantReadWriteLock.ReadLock ReentrantReadWriteLock.readLock()) &&
		target(l);

	pointcut getWriteLock(ReentrantReadWriteLock l) :
		call(ReentrantReadWriteLock.WriteLock ReentrantReadWriteLock.writeLock()) &&
		target(l);
	
	
	after() returning(ReentrantReadWriteLock l) : create() {
		ReentrantReadWriteLockEvent newEvent = new ReentrantReadWriteLockEvent(l.toString());
		newEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		newEvent.setEventType(ReentrantReadWriteLockEventType.Create);
		EventQueue.addEvent(newEvent);
	}
	
	after(boolean fair) returning(ReentrantReadWriteLock l) : createFair(fair) {
		ReentrantReadWriteLockEvent newEvent = new ReentrantReadWriteLockEvent(l.toString());
		newEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		newEvent.setEventType(ReentrantReadWriteLockEventType.Create);
		newEvent.setFair(fair);
		EventQueue.addEvent(newEvent);
	}
	
	after(ReentrantReadWriteLock l) returning(ReentrantReadWriteLock.ReadLock readLock) :
		getReadLock(l) {
		
		ReentrantReadWriteLockEvent newEvent = new ReentrantReadWriteLockEvent(l.toString());
		newEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		newEvent.setEventType(ReentrantReadWriteLockEventType.GetReadLock);
		newEvent.setReadLock(readLock.toString());
	}
	
	after(ReentrantReadWriteLock l) returning(ReentrantReadWriteLock.WriteLock writeLock) :
		getWriteLock(l) {
		
		ReentrantReadWriteLockEvent newEvent = new ReentrantReadWriteLockEvent(l.toString());
		newEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		newEvent.setEventType(ReentrantReadWriteLockEventType.GetWriteLock);
		newEvent.setWriteLock(writeLock.toString());
	}
	
	
	//ReadLock operations
	pointcut lockRead(ReentrantReadWriteLock.ReadLock l) :
		call(void ReentrantReadWriteLock.ReadLock.lock()) &&
		target(l);
	
	pointcut lockInterruptiblyRead(ReentrantReadWriteLock.ReadLock l) :
		call(void ReentrantReadWriteLock.ReadLock.lockInterruptibly()) &&
		target(l);
	
	pointcut tryLockRead(ReentrantReadWriteLock.ReadLock l) :
		call(boolean ReentrantReadWriteLock.ReadLock.tryLock()) &&
		target(l);
	
	pointcut tryLockTimeRead(ReentrantReadWriteLock.ReadLock l, long timeout, TimeUnit unit) :
		call(boolean ReentrantReadWriteLock.ReadLock.tryLock(long, TimeUnit)) &&
		target(l) &&
		args(timeout, unit);
	
	pointcut unlockRead(ReentrantReadWriteLock.ReadLock l) :
		call(void ReentrantReadWriteLock.ReadLock.unlock()) &&
		target(l);
	
	
	before(ReentrantReadWriteLock.ReadLock l) : lockRead(l) {
		ReentrantReadWriteLockEvent newEvent = new ReentrantReadWriteLockEvent(l.toString());
		newEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		newEvent.setEventType(ReentrantReadWriteLockEventType.BeforeLockRead);
		EventQueue.addEvent(newEvent);
	}
	
	after(ReentrantReadWriteLock.ReadLock l) : lockRead(l) {
		ReentrantReadWriteLockEvent newEvent = new ReentrantReadWriteLockEvent(l.toString());
		newEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		newEvent.setEventType(ReentrantReadWriteLockEventType.AfterLockRead);
		EventQueue.addEvent(newEvent);
	}
	
	before(ReentrantReadWriteLock.ReadLock l) : lockInterruptiblyRead(l) {
		ReentrantReadWriteLockEvent newEvent = new ReentrantReadWriteLockEvent(l.toString());
		newEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		newEvent.setEventType(ReentrantReadWriteLockEventType.BeforeLockInterruptiblyRead);
		EventQueue.addEvent(newEvent);
	}
	
	after(ReentrantReadWriteLock.ReadLock l) : lockInterruptiblyRead(l) {
		ReentrantReadWriteLockEvent newEvent = new ReentrantReadWriteLockEvent(l.toString());
		newEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		newEvent.setEventType(ReentrantReadWriteLockEventType.AfterLockInterruptiblyRead);
		EventQueue.addEvent(newEvent);
	}
	
	before(ReentrantReadWriteLock.ReadLock l) : tryLockRead(l) {
		ReentrantReadWriteLockEvent newEvent = new ReentrantReadWriteLockEvent(l.toString());
		newEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		newEvent.setEventType(ReentrantReadWriteLockEventType.BeforeTryLockRead);
		EventQueue.addEvent(newEvent);
	}
	
	after(ReentrantReadWriteLock.ReadLock l) returning(boolean hasLock) : tryLockRead(l) {
		ReentrantReadWriteLockEvent newEvent = new ReentrantReadWriteLockEvent(l.toString());
		newEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		newEvent.setEventType(ReentrantReadWriteLockEventType.AfterTryLockRead);
		newEvent.setHasLock(hasLock);
		EventQueue.addEvent(newEvent);
	}
	
	before(ReentrantReadWriteLock.ReadLock l, long timeout, TimeUnit unit) : tryLockTimeRead(l, timeout, unit) {
		ReentrantReadWriteLockEvent newEvent = new ReentrantReadWriteLockEvent(l.toString());
		newEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		newEvent.setEventType(ReentrantReadWriteLockEventType.BeforeTryLockRead);
		newEvent.setTimeout(timeout);
		newEvent.setTimeoutUnit(unit);
		EventQueue.addEvent(newEvent);
	}
	
	after(ReentrantReadWriteLock.ReadLock l, long timeout, TimeUnit unit) 
		returning(boolean hasLock) : tryLockTimeRead(l, timeout, unit) {
		ReentrantReadWriteLockEvent newEvent = new ReentrantReadWriteLockEvent(l.toString());
		newEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		newEvent.setEventType(ReentrantReadWriteLockEventType.AfterTryLockRead);
		newEvent.setTimeout(timeout);
		newEvent.setHasLock(hasLock);
		EventQueue.addEvent(newEvent);
	}
		
	after(ReentrantReadWriteLock.ReadLock l) : unlockRead(l) {
		ReentrantReadWriteLockEvent newEvent = new ReentrantReadWriteLockEvent(l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		newEvent.setEventType(ReentrantReadWriteLockEventType.UnlockRead);
		EventQueue.addEvent(newEvent);
	}
	
	
	
	//WriteLock operations
	pointcut lockWrite(ReentrantReadWriteLock.WriteLock l) :
		call(void ReentrantReadWriteLock.WriteLock.lock()) &&
		target(l);
	
	pointcut lockInterruptiblyWrite(ReentrantReadWriteLock.WriteLock l) :
		call(void ReentrantReadWriteLock.WriteLock.lockInterruptibly()) &&
		target(l);
	
	pointcut tryLockWrite(ReentrantReadWriteLock.WriteLock l) :
		call(boolean ReentrantReadWriteLock.WriteLock.tryLock()) &&
		target(l);
	
	pointcut tryLockTimeWrite(ReentrantReadWriteLock.WriteLock l, long timeout, TimeUnit unit) :
		call(boolean ReentrantReadWriteLock.WriteLock.tryLock(long, TimeUnit)) &&
		target(l) &&
		args(timeout, unit);
	
	pointcut unlockWrite(ReentrantReadWriteLock.WriteLock l) :
		call(void ReentrantReadWriteLock.WriteLock.unlock()) &&
		target(l);
	
	before(ReentrantReadWriteLock.WriteLock l) : lockWrite(l) {
		ReentrantReadWriteLockEvent newEvent = new ReentrantReadWriteLockEvent(l.toString());
		newEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		newEvent.setEventType(ReentrantReadWriteLockEventType.BeforeLockWrite);
		EventQueue.addEvent(newEvent);
	}
	
	after(ReentrantReadWriteLock.WriteLock l) : lockWrite(l) {
		ReentrantReadWriteLockEvent newEvent = new ReentrantReadWriteLockEvent(l.toString());
		newEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		newEvent.setEventType(ReentrantReadWriteLockEventType.AfterLockWrite);
		EventQueue.addEvent(newEvent);
	}
	
	before(ReentrantReadWriteLock.WriteLock l) : lockInterruptiblyWrite(l) {
		ReentrantReadWriteLockEvent newEvent = new ReentrantReadWriteLockEvent(l.toString());
		newEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		newEvent.setEventType(ReentrantReadWriteLockEventType.BeforeLockInterruptiblyWrite);
		EventQueue.addEvent(newEvent);
	}
	
	after(ReentrantReadWriteLock.WriteLock l) : lockInterruptiblyWrite(l) {
		ReentrantReadWriteLockEvent newEvent = new ReentrantReadWriteLockEvent(l.toString());
		newEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		newEvent.setEventType(ReentrantReadWriteLockEventType.AfterLockInterruptiblyWrite);
		EventQueue.addEvent(newEvent);
	}
	
	before(ReentrantReadWriteLock.WriteLock l) : tryLockWrite(l) {
		ReentrantReadWriteLockEvent newEvent = new ReentrantReadWriteLockEvent(l.toString());
		newEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		newEvent.setEventType(ReentrantReadWriteLockEventType.BeforeTryLockWrite);
		EventQueue.addEvent(newEvent);
	}
	
	after(ReentrantReadWriteLock.WriteLock l) returning(boolean hasLock) : tryLockWrite(l) {
		ReentrantReadWriteLockEvent newEvent = new ReentrantReadWriteLockEvent(l.toString());
		newEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		newEvent.setEventType(ReentrantReadWriteLockEventType.AfterTryLockWrite);
		newEvent.setHasLock(hasLock);
		EventQueue.addEvent(newEvent);
	}
	
	before(ReentrantReadWriteLock.WriteLock l, long timeout, TimeUnit unit) : tryLockTimeWrite(l, timeout, unit) {
		ReentrantReadWriteLockEvent newEvent = new ReentrantReadWriteLockEvent(l.toString());
		newEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		newEvent.setEventType(ReentrantReadWriteLockEventType.BeforeTryLockWrite);
		newEvent.setTimeout(timeout);
		newEvent.setTimeoutUnit(unit);
		EventQueue.addEvent(newEvent);
	}
	
	after(ReentrantReadWriteLock.WriteLock l, long timeout, TimeUnit unit) 
		returning(boolean hasLock) : tryLockTimeWrite(l, timeout, unit) {
		ReentrantReadWriteLockEvent newEvent = new ReentrantReadWriteLockEvent(l.toString());
		newEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		newEvent.setEventType(ReentrantReadWriteLockEventType.AfterTryLockWrite);
		newEvent.setTimeout(timeout);
		newEvent.setHasLock(hasLock);
		EventQueue.addEvent(newEvent);
	}
		
	after(ReentrantReadWriteLock.WriteLock l) : unlockWrite(l) {
		ReentrantReadWriteLockEvent newEvent = new ReentrantReadWriteLockEvent(l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		newEvent.setEventType(ReentrantReadWriteLockEventType.UnlockRead);
		EventQueue.addEvent(newEvent);
	}
}
