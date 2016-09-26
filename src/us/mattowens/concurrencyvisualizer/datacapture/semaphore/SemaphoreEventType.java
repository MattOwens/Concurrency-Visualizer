package us.mattowens.concurrencyvisualizer.datacapture.semaphore;

public enum SemaphoreEventType {
	Create,
	Release,
	Acquire,
	AcquireTimeout,
	TryAcquireFailure,
	PermitReduction,
	DrainPermits
}
