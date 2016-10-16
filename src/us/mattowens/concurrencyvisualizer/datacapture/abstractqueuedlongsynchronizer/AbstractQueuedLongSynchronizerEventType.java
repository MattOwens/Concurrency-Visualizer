package us.mattowens.concurrencyvisualizer.datacapture.abstractqueuedlongsynchronizer;

public enum AbstractQueuedLongSynchronizerEventType {
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
