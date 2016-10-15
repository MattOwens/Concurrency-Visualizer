package us.mattowens.concurrencyvisualizer.datacapture.lock;

public enum LockEventType {
	BeforeLock,
	AfterLock,
	BeforeLockInterruptibly,
	AfterLockInterruptibly,
	NewCondition,
	BeforeTryLock,
	AfterTryLock,
	Unlock
}
