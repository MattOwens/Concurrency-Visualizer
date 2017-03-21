package us.mattowens.concurrencyvisualizer.datacapture.reentrantreadwritelock;

public enum ReentrantReadWriteLockEventType {
	Create,
	GetReadLock,
	GetWriteLock,
	BeforeLockRead,
	AfterLockRead,
	BeforeLockWrite,
	AfterLockWrite,
	BeforeLockInterruptiblyRead,
	AfterLockInterruptiblyRead,
	BeforeLockInterruptiblyWrite,
	AfterLockInterruptiblyWrite,
	BeforeTryLockRead,
	AfterTryLockRead,
	BeforeTryLockWrite,
	AfterTryLockWrite,
	UnlockRead,
	UnlockWrite
}
