package us.mattowens.concurrencyvisualizer.datacapture.thread;

import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.ControlSignalEventType;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public aspect ThreadDataCapture {
	
	pointcut create() :
		call(Thread.new()) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	pointcut createTarget(Runnable r) :
		call(Thread.new(Runnable)) &&
		args(r) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	pointcut createTargetName(Runnable r, String name) :
		call(Thread.new(Runnable, String)) &&
		args(r, name) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	pointcut createName(String name) :
		call(Thread.new(String)) &&
		args(name) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	pointcut createGroupTarget(ThreadGroup g, Runnable r) :
		call(Thread.new(ThreadGroup, Runnable)) &&
		args(g, r) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	pointcut createGroupTargetName(ThreadGroup g, Runnable r, String name) :
		(call(Thread.new(ThreadGroup, Runnable, String)) ||
		 call(Thread.new(ThreadGroup, Runnable, String, long))) &&
		args(g, r, name) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	pointcut createGroupName(ThreadGroup g, String name) :
		call(Thread.new(ThreadGroup, String)) &&
		args(g, name) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	
	pointcut interrupt(Thread t) :
		call(void Thread.interrupt()) &&
		target(t) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	pointcut interrupted() :
		call(boolean Thread.interrupted()) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	pointcut isInterrupted(Thread t) :
		call(boolean Thread.isInterrupted()) &&
		target(t) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	pointcut join(Thread t) :
		call(void Thread.join()) &&
		target(t) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	pointcut joinTimeout(Thread t, long millis) :
		call(void Thread.join(long)) &&
		target(t) &&
		args(millis) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	pointcut joinFiner(Thread t, long millis, int nanos) :
		call(void Thread.join(long, int)) &&
		target(t) &&
		args(millis, nanos) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	pointcut setDaemon(Thread t, boolean on) :
		call(void Thread.setDaemon(boolean)) &&
		target(t) &&
		args(on) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	pointcut setName(Thread t, String name) :
		call(void Thread.setName(String)) &&
		target(t) &&
		args(name) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	pointcut setPriority(Thread t, int newPriority) :
		call(void Thread.setPriority(int)) &&
		target(t) &&
		args(newPriority) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	pointcut sleep(long millis) :
		call(void Thread.sleep(long)) &&
		args(millis) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	pointcut sleepFiner(long millis, int nanos) :
		call(void Thread.sleep(long, int)) &&
		args(millis, nanos) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	pointcut start(Thread t) :
		call(void Thread.start()) &&
		target(t) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	pointcut yield() :
		call(void Thread.yield()) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	
	after() returning(Thread t) : create() {
		Event createEvent = new Event(EventClass.Thread, ThreadEventType.Create, t.getName());
		createEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());

		EventQueue.addEvent(createEvent);
	}
	
	after(Runnable r) returning(Thread t) : createTarget(r) {
		Event createEvent = new Event(EventClass.Thread, ThreadEventType.Create, t.getName());
		createEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		createEvent.addValue(StringConstants.RUNNABLE, r.toString());
		
		EventQueue.addEvent(createEvent);
	}
	
	after(Runnable r, String name) returning(Thread t) : createTargetName(r, name) {
		Event createEvent = new Event(EventClass.Thread, ThreadEventType.Create, t.getName());
		createEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		createEvent.addValue(StringConstants.RUNNABLE, r.toString());
		createEvent.addValue(StringConstants.NEW_NAME, name);
		
		EventQueue.addEvent(createEvent);
	}
	
	after(String name) returning(Thread t) : createName(name) {
		Event createEvent = new Event(EventClass.Thread, ThreadEventType.Create, t.getName());
		createEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		createEvent.addValue(StringConstants.NEW_NAME, name);
		
		EventQueue.addEvent(createEvent);
	}
	
	after(ThreadGroup g, Runnable r) returning(Thread t) : createGroupTarget(g, r) {
		Event createEvent = new Event(EventClass.Thread, ThreadEventType.Create, t.getName());
		createEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		createEvent.addValue(StringConstants.RUNNABLE, r.toString());
		createEvent.addValue(StringConstants.THREAD_GROUP, g.toString());
		
		EventQueue.addEvent(createEvent);
	}
	
	after(ThreadGroup g, Runnable r, String name) returning(Thread t) :
		createGroupTargetName(g, r, name) {
		
		Event createEvent = new Event(EventClass.Thread, ThreadEventType.Create, t.getName());
		createEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		createEvent.addValue(StringConstants.RUNNABLE, r.toString());
		createEvent.addValue(StringConstants.THREAD_GROUP, g.toString());
		createEvent.addValue(StringConstants.NEW_NAME, name);
		
		EventQueue.addEvent(createEvent);
	}
	
	after(ThreadGroup g, String name) returning(Thread t) : createGroupName(g, name) {
		Event createEvent = new Event(EventClass.Thread, ThreadEventType.Create, t.getName());
		createEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		createEvent.addValue(StringConstants.THREAD_GROUP, g.toString());
		createEvent.addValue(StringConstants.NEW_NAME, name);
		
		EventQueue.addEvent(createEvent);
	}
	
	after(Thread t) : interrupt(t) {
		Event interruptEvent = new Event(EventClass.Thread, ThreadEventType.Interrupt, t.getName());
		interruptEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());

		EventQueue.addEvent(interruptEvent);
	}
	
	after() returning(boolean interrupted) : interrupted() {
		Event interruptEvent = 
				new Event(EventClass.Thread, ThreadEventType.Interrupted, Thread.currentThread().getName());
		interruptEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		interruptEvent.addValue(StringConstants.INTERRUPTED, interrupted);
		
		EventQueue.addEvent(interruptEvent);
	}
	
	after(Thread t) returning(boolean interrupted) : isInterrupted(t) {
		Event interruptEvent = 
				new Event(EventClass.Thread, ThreadEventType.IsInterrupted, t.getName());
		interruptEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		interruptEvent.addValue(StringConstants.INTERRUPTED, interrupted);
		
		EventQueue.addEvent(interruptEvent);
	}

	before(Thread t) : join(t) {
		Event joinEvent = new Event(EventClass.Thread, ThreadEventType.BeforeJoin, t.getName());
		joinEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(joinEvent);
	}
	
	after(Thread t) : join(t) {
		Event joinEvent = new Event(EventClass.Thread, ThreadEventType.AfterJoin, t.getName());
		joinEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());

		
		EventQueue.addEvent(joinEvent);
	}
	
	before(Thread t, long millis) : joinTimeout(t, millis) {
		Event joinEvent = new Event(EventClass.Thread, ThreadEventType.BeforeJoin, t.getName());
		joinEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		joinEvent.addValue(StringConstants.MILLIS, millis);
		
		EventQueue.addEvent(joinEvent);
	}
	
	after(Thread t, long millis) : joinTimeout(t, millis) {
		Event joinEvent = new Event(EventClass.Thread, ThreadEventType.AfterJoin, t.getName());
		joinEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(joinEvent);
	}
	
	before(Thread t, long millis, int nanos) : joinFiner(t, millis, nanos) {
		Event joinEvent = new Event(EventClass.Thread, ThreadEventType.BeforeJoin, t.getName());
		joinEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		joinEvent.addValue(StringConstants.MILLIS, millis);
		joinEvent.addValue(StringConstants.NANOS, nanos);
		
		EventQueue.addEvent(joinEvent);
	}
	
	after(Thread t, long millis, int nanos) : joinFiner(t, millis, nanos) {
		Event joinEvent = new Event(EventClass.Thread, ThreadEventType.AfterJoin, t.getName());
		joinEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(joinEvent);
	}
	
	after(Thread t, boolean on) : setDaemon(t, on) {
		Event daemonEvent = new Event(EventClass.Thread, ThreadEventType.DaemonChange, t.getName());
		daemonEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		daemonEvent.addValue(StringConstants.DAEMON, on);
		
		EventQueue.addEvent(daemonEvent);
	}
	
	before(Thread t, String name) : setName(t, name) {
		//Send control signal so display gets the new name
		Event controlSignal = new Event(EventClass.ControlSignal, ControlSignalEventType.NewThread, "");
		controlSignal.setJoinPointName("");
		controlSignal.addValue(StringConstants.THREAD_NAME, name);
		EventQueue.addEvent(controlSignal);
		
		//Send event that gets displayed
		Event nameEvent = new Event(EventClass.Thread, ThreadEventType.NameChange, t.getName());
		nameEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		nameEvent.addValue(StringConstants.NEW_NAME, name);
		
		EventQueue.addEvent(nameEvent);
	}
	
	after(Thread t, int priority) : setPriority(t, priority) {
		Event priorityEvent = new Event(EventClass.Thread, ThreadEventType.PriorityChange, t.getName());
		priorityEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		priorityEvent.addValue(StringConstants.PRIORITY, priority);
		
		EventQueue.addEvent(priorityEvent);
	}
	
	before(long millis) : sleep(millis) {
		Event sleepEvent = new Event(EventClass.Thread, ThreadEventType.BeforeSleep, Thread.currentThread().getName());
		sleepEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		sleepEvent.addValue(StringConstants.MILLIS, millis);
		
		EventQueue.addEvent(sleepEvent);
	}
	
	after(long millis) : sleep(millis) {
		Event sleepEvent = new Event(EventClass.Thread, ThreadEventType.AfterSleep, Thread.currentThread().getName());
		sleepEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(sleepEvent);
	}
	
	before(long millis, int nanos) : sleepFiner(millis, nanos) {
		Event sleepEvent = new Event(EventClass.Thread, ThreadEventType.BeforeSleep, Thread.currentThread().getName());
		sleepEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		sleepEvent.addValue(StringConstants.MILLIS, millis);
		sleepEvent.addValue(StringConstants.NANOS, nanos);
		
		EventQueue.addEvent(sleepEvent);
	}
	
	after(long millis, int nanos) : sleepFiner(millis, nanos) {
		Event sleepEvent = new Event(EventClass.Thread, ThreadEventType.AfterSleep, Thread.currentThread().getName());
		sleepEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(sleepEvent);
	}
	
	before(Thread t) : start(t) {
		Event startEvent = new Event(EventClass.Thread, ThreadEventType.Start, t.getName());
		startEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());

		EventQueue.addEvent(startEvent);
	}
	
	after() : yield() {
		Event yieldEvent = new Event(EventClass.Thread, ThreadEventType.Yield, Thread.currentThread().getName());
		yieldEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());

		EventQueue.addEvent(yieldEvent);
	}
}
