package us.mattowens.concurrencyvisualizer.datacapture.semaphore;

public enum SemaphoreEventType {
	Create,
	Release,
	BeforeAcquire,
	AfterAcquire,
	BeforeAcquireTimeout,
	AfterAcquireTimeout,
	BeforeTryAcquire,
	AfterTryAcquire,
	PermitReduction,
	DrainPermits
}
