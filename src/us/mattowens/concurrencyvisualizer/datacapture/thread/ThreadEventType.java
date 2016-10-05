package us.mattowens.concurrencyvisualizer.datacapture.thread;

public enum ThreadEventType {
	Create,
	Interrupt,
	Interrupted,
	IsInterrupted,
	BeforeJoin,
	AfterJoin,
	PriorityChange,
	DaemonChange,
	NameChange,
	BeforeSleep,
	AfterSleep,
	Start,
	Yield
}
