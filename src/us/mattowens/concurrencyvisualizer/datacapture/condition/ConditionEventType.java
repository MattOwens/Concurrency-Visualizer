package us.mattowens.concurrencyvisualizer.datacapture.condition;

public enum ConditionEventType {
	BeforeAwait,
	AfterAwait,
	BeforeAwaitUninterruptibly,
	AfterAwaitUninterruptibly,
	BeforeAwaitUntil,
	AfterAwaitUntil,
	Signal,
	SignalAll
}
