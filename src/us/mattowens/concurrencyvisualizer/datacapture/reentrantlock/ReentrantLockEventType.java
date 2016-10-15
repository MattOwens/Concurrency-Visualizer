package us.mattowens.concurrencyvisualizer.datacapture.reentrantlock;

public enum ReentrantLockEventType {
	Create,
	IsLocked,
	BeforeLock,
	AfterLock,
	BeforeLockInterruptibly,
	AfterLockInterruptibly,
	NewCondition,
	BeforeTryLock,
	AfterTryLock,
	Unlock
}
