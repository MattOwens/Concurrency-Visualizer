package us.mattowens.concurrencyvisualizer.datacapture.abstractqueuedsynchronizer;

public enum AbstractQueuedSynchronizerEventType {
	Create,
	BeforeAcquire,
	AfterAcquire,
	BeforeAcquireShared,
	AfterAcquireShared,
	CompareAndSetState,
	SetState,
	Release,
	ReleaseShared,
	BeforeTryAcquire,
	AfterTryAcquire,
	BeforeTryAcquireShared,
	AfterTryAcquireShared,
	TryRelease,
	TryReleaseShared
}
