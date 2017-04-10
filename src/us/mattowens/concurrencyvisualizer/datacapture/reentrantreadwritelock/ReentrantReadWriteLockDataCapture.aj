package us.mattowens.concurrencyvisualizer.datacapture.reentrantreadwritelock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
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
		Event newEvent = new Event(EventClass.ReentrantReadWriteLock,
				ReentrantReadWriteLockEventType.Create, l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		EventQueue.addEvent(newEvent);
	}
	
	after(boolean fair) returning(ReentrantReadWriteLock l) : createFair(fair) {
		Event newEvent = new Event(EventClass.ReentrantReadWriteLock,
				ReentrantReadWriteLockEventType.Create, l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		newEvent.addValue(StringConstants.FAIR, fair);
		EventQueue.addEvent(newEvent);
	}
	
	after(ReentrantReadWriteLock l) returning(ReentrantReadWriteLock.ReadLock readLock) :
		getReadLock(l) {
		
		Event newEvent = new Event(EventClass.ReentrantReadWriteLock,
				ReentrantReadWriteLockEventType.GetReadLock, l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		newEvent.addValue(StringConstants.READ_LOCK, readLock.toString());
	}
	
	after(ReentrantReadWriteLock l) returning(ReentrantReadWriteLock.WriteLock writeLock) :
		getWriteLock(l) {
		
		Event newEvent = new Event(EventClass.ReentrantReadWriteLock,
				ReentrantReadWriteLockEventType.GetWriteLock, l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		newEvent.addValue(StringConstants.WRITE_LOCK, writeLock.toString());
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
		Event newEvent = new Event(EventClass.ReentrantReadWriteLock,
				ReentrantReadWriteLockEventType.BeforeLockRead, l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		EventQueue.addEvent(newEvent);
	}
	
	after(ReentrantReadWriteLock.ReadLock l) : lockRead(l) {
		Event newEvent = new Event(EventClass.ReentrantReadWriteLock,
				ReentrantReadWriteLockEventType.AfterLockRead, l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		EventQueue.addEvent(newEvent);
	}
	
	before(ReentrantReadWriteLock.ReadLock l) : lockInterruptiblyRead(l) {
		Event newEvent = new Event(EventClass.ReentrantReadWriteLock,
				ReentrantReadWriteLockEventType.BeforeLockInterruptiblyRead, l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		EventQueue.addEvent(newEvent);
	}
	
	after(ReentrantReadWriteLock.ReadLock l) : lockInterruptiblyRead(l) {
		Event newEvent = new Event(EventClass.ReentrantReadWriteLock, 
				ReentrantReadWriteLockEventType.AfterLockInterruptiblyRead, l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		EventQueue.addEvent(newEvent);
	}
	
	before(ReentrantReadWriteLock.ReadLock l) : tryLockRead(l) {
		Event newEvent = new Event(EventClass.ReentrantReadWriteLock,
				ReentrantReadWriteLockEventType.BeforeTryLockRead, l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		EventQueue.addEvent(newEvent);
	}
	
	after(ReentrantReadWriteLock.ReadLock l) returning(boolean hasLock) : tryLockRead(l) {
		Event newEvent = new Event(EventClass.ReentrantReadWriteLock,
				ReentrantReadWriteLockEventType.AfterTryLockRead, l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		newEvent.addValue(StringConstants.SUCCESS, hasLock);
		EventQueue.addEvent(newEvent);
	}
	
	before(ReentrantReadWriteLock.ReadLock l, long timeout, TimeUnit unit) : tryLockTimeRead(l, timeout, unit) {
		Event newEvent = new Event(EventClass.ReentrantReadWriteLock,
				ReentrantReadWriteLockEventType.BeforeTryLockRead, l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		newEvent.addValue(StringConstants.TIMEOUT, timeout);
		newEvent.addValue(StringConstants.TIME_UNIT, unit);
		EventQueue.addEvent(newEvent);
	}
	
	after(ReentrantReadWriteLock.ReadLock l, long timeout, TimeUnit unit) 
		returning(boolean hasLock) : tryLockTimeRead(l, timeout, unit) {
		Event newEvent = new Event(EventClass.ReentrantReadWriteLock,
				ReentrantReadWriteLockEventType.AfterTryLockRead, l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		newEvent.addValue(StringConstants.SUCCESS, hasLock);
		EventQueue.addEvent(newEvent);
	}
		
	after(ReentrantReadWriteLock.ReadLock l) : unlockRead(l) {
		Event newEvent = new Event(EventClass.ReentrantReadWriteLock,
				ReentrantReadWriteLockEventType.UnlockRead, l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
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
		Event newEvent = new Event(EventClass.ReentrantReadWriteLock,
				ReentrantReadWriteLockEventType.BeforeLockWrite, l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		EventQueue.addEvent(newEvent);
	}
	
	after(ReentrantReadWriteLock.WriteLock l) : lockWrite(l) {
		Event newEvent = new Event(EventClass.ReentrantReadWriteLock,
				ReentrantReadWriteLockEventType.AfterLockWrite, l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		EventQueue.addEvent(newEvent);
	}
	
	before(ReentrantReadWriteLock.WriteLock l) : lockInterruptiblyWrite(l) {
		Event newEvent = new Event(EventClass.ReentrantReadWriteLock, 
				ReentrantReadWriteLockEventType.BeforeLockInterruptiblyWrite, l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		EventQueue.addEvent(newEvent);
	}
	
	after(ReentrantReadWriteLock.WriteLock l) : lockInterruptiblyWrite(l) {
		Event newEvent = new Event(EventClass.ReentrantReadWriteLock,
				ReentrantReadWriteLockEventType.AfterLockInterruptiblyWrite, l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		EventQueue.addEvent(newEvent);
	}
	
	before(ReentrantReadWriteLock.WriteLock l) : tryLockWrite(l) {
		Event newEvent = new Event(EventClass.ReentrantReadWriteLock,
				ReentrantReadWriteLockEventType.BeforeTryLockWrite, l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		EventQueue.addEvent(newEvent);
	}
	
	after(ReentrantReadWriteLock.WriteLock l) returning(boolean hasLock) : tryLockWrite(l) {
		Event newEvent = new Event(EventClass.ReentrantReadWriteLock,
				ReentrantReadWriteLockEventType.AfterTryLockWrite, l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		newEvent.addValue(StringConstants.SUCCESS, hasLock);
		EventQueue.addEvent(newEvent);
	}
	
	before(ReentrantReadWriteLock.WriteLock l, long timeout, TimeUnit unit) : tryLockTimeWrite(l, timeout, unit) {
		Event newEvent = new Event(EventClass.ReentrantReadWriteLock,
				ReentrantReadWriteLockEventType.BeforeTryLockWrite, l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		newEvent.addValue(StringConstants.TIMEOUT, timeout);
		newEvent.addValue(StringConstants.TIME_UNIT, unit);
		EventQueue.addEvent(newEvent);
	}
	
	after(ReentrantReadWriteLock.WriteLock l, long timeout, TimeUnit unit) 
		returning(boolean hasLock) : tryLockTimeWrite(l, timeout, unit) {
		Event newEvent = new Event(EventClass.ReentrantReadWriteLock,
				ReentrantReadWriteLockEventType.AfterTryLockWrite, l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		newEvent.addValue(StringConstants.SUCCESS, hasLock);
		EventQueue.addEvent(newEvent);
	}
		
	after(ReentrantReadWriteLock.WriteLock l) : unlockWrite(l) {
		Event newEvent = new Event(EventClass.ReentrantReadWriteLock,
				ReentrantReadWriteLockEventType.UnlockWrite, l.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		EventQueue.addEvent(newEvent);
	}
}
