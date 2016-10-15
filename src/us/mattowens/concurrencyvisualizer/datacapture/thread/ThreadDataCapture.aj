package us.mattowens.concurrencyvisualizer.datacapture.thread;

import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public aspect ThreadDataCapture {
	
	pointcut create() :
		call(Thread.new()) &&
		!within(us.mattowens.concurrencyvisualizer.datacapture..*);
	
	pointcut createTarget(Runnable r) :
		call(Thread.new(Runnable)) &&
		args(r) &&
		!within(us.mattowens.concurrencyvisualizer.datacapture..*);
	
	pointcut createTargetName(Runnable r, String name) :
		call(Thread.new(Runnable, String)) &&
		args(r, name) &&
		!within(us.mattowens.concurrencyvisualizer.datacapture..*);
	
	pointcut createName(String name) :
		call(Thread.new(String)) &&
		args(name) &&
		!within(us.mattowens.concurrencyvisualizer.datacapture..*);
	
	pointcut createGroupTarget(ThreadGroup g, Runnable r) :
		call(Thread.new(ThreadGroup, Runnable)) &&
		args(g, r) &&
		!within(us.mattowens.concurrencyvisualizer.datacapture..*);
	
	pointcut createGroupTargetName(ThreadGroup g, Runnable r, String name) :
		(call(Thread.new(ThreadGroup, Runnable, String)) ||
		 call(Thread.new(ThreadGroup, Runnable, String, long))) &&
		args(g, r, name) &&
		!within(us.mattowens.concurrencyvisualizer.datacapture..*);
	
	pointcut createGroupName(ThreadGroup g, String name) :
		call(Thread.new(ThreadGroup, String)) &&
		args(g, name) &&
		!within(us.mattowens.concurrencyvisualizer.datacapture..*);
	
	
	pointcut interrupt(Thread t) :
		call(void Thread.interrupt()) &&
		target(t) &&
		!within(us.mattowens.concurrencyvisualizer.datacapture..*);
	
	pointcut interrupted() :
		call(boolean Thread.interrupted()) &&
		!within(us.mattowens.concurrencyvisualizer.datacapture..*);
	
	pointcut isInterrupted(Thread t) :
		call(boolean Thread.isInterrupted()) &&
		target(t) &&
		!within(us.mattowens.concurrencyvisualizer.datacapture..*);
	
	pointcut join(Thread t) :
		call(void Thread.join()) &&
		target(t) &&
		!within(us.mattowens.concurrencyvisualizer.datacapture..*);
	
	pointcut joinTimeout(Thread t, long millis) :
		call(void Thread.join(long)) &&
		target(t) &&
		args(millis) &&
		!within(us.mattowens.concurrencyvisualizer.datacapture..*);
	
	pointcut joinFiner(Thread t, long millis, int nanos) :
		call(void Thread.join(long, int)) &&
		target(t) &&
		args(millis, nanos) &&
		!within(us.mattowens.concurrencyvisualizer.datacapture..*);
	
	pointcut setDaemon(Thread t, boolean on) :
		call(void Thread.setDaemon(boolean)) &&
		target(t) &&
		args(on) &&
		!within(us.mattowens.concurrencyvisualizer.datacapture..*);
	
	pointcut setName(Thread t, String name) :
		call(void Thread.setName(String)) &&
		target(t) &&
		args(name) &&
		!within(us.mattowens.concurrencyvisualizer.datacapture..*);
	
	pointcut setPriority(Thread t, int newPriority) :
		call(void Thread.setPriority(int)) &&
		target(t) &&
		args(newPriority) &&
		!within(us.mattowens.concurrencyvisualizer.datacapture..*);
	
	pointcut sleep(long millis) :
		call(void Thread.sleep(long)) &&
		args(millis) &&
		!within(us.mattowens.concurrencyvisualizer.datacapture..*);
	
	pointcut sleepFiner(long millis, int nanos) :
		call(void Thread.sleep(long, int)) &&
		args(millis, nanos) &&
		!within(us.mattowens.concurrencyvisualizer.datacapture..*);
	
	pointcut start(Thread t) :
		call(void Thread.start()) &&
		target(t) &&
		!within(us.mattowens.concurrencyvisualizer.datacapture..*);
	
	pointcut yield() :
		call(void Thread.yield()) &&
		!within(us.mattowens.concurrencyvisualizer.datacapture..*);
	
	
	after() returning(Thread t) : create() {
		ThreadEvent createEvent = new ThreadEvent(t.getName(), 
				ThreadEventType.Create);
		createEvent.setJoinPointName(thisJoinPoint.getSignature().getName());

		EventQueue.addEvent(createEvent);
	}
	
	after(Runnable r) returning(Thread t) : createTarget(r) {
		ThreadEvent createEvent = new ThreadEvent(t.getName(), 
				ThreadEventType.Create);
		createEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		createEvent.setRunnable(r.toString());
		
		EventQueue.addEvent(createEvent);
	}
	
	after(Runnable r, String name) returning(Thread t) : createTargetName(r, name) {
		ThreadEvent createEvent = new ThreadEvent(t.getName(), 
				ThreadEventType.Create);
		createEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		createEvent.setRunnable(r.toString());
		createEvent.setNewName(name);
		
		EventQueue.addEvent(createEvent);
	}
	
	after(String name) returning(Thread t) : createName(name) {
		ThreadEvent createEvent = new ThreadEvent(t.getName(), 
				ThreadEventType.Create);
		createEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		createEvent.setNewName(name);
		
		EventQueue.addEvent(createEvent);
	}
	
	after(ThreadGroup g, Runnable r) returning(Thread t) : createGroupTarget(g, r) {
		ThreadEvent createEvent = new ThreadEvent(t.getName(), 
				ThreadEventType.Create);
		createEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		createEvent.setRunnable(r.toString());
		createEvent.setThreadGroup(g.toString());
		
		EventQueue.addEvent(createEvent);
	}
	
	after(ThreadGroup g, Runnable r, String name) returning(Thread t) :
		createGroupTargetName(g, r, name) {
		
		ThreadEvent createEvent = new ThreadEvent(t.getName(), 
				ThreadEventType.Create);
		createEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		createEvent.setRunnable(r.toString());
		createEvent.setThreadGroup(g.toString());
		createEvent.setNewName(name);
		
		EventQueue.addEvent(createEvent);
	}
	
	after(ThreadGroup g, String name) returning(Thread t) : createGroupName(g, name) {
		ThreadEvent createEvent = new ThreadEvent(t.getName(), 
				ThreadEventType.Create);
		createEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		createEvent.setThreadGroup(g.toString());
		createEvent.setNewName(name);
		
		EventQueue.addEvent(createEvent);
	}
	
	after(Thread t) : interrupt(t) {
		ThreadEvent interruptEvent = new ThreadEvent(t.getName(),
				ThreadEventType.Interrupt);
		interruptEvent.setJoinPointName(thisJoinPoint.getSignature().getName());

		EventQueue.addEvent(interruptEvent);
	}
	
	after() returning(boolean interrupted) : interrupted() {
		ThreadEvent interruptEvent = 
				new ThreadEvent(Thread.currentThread().getName(),
				ThreadEventType.Interrupted);
		interruptEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		interruptEvent.setIsInterrupted(interrupted);
		
		EventQueue.addEvent(interruptEvent);
	}
	
	after(Thread t) returning(boolean interrupted) : isInterrupted(t) {
		ThreadEvent interruptEvent = 
				new ThreadEvent(t.getName(), ThreadEventType.Interrupted);
		interruptEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		interruptEvent.setIsInterrupted(interrupted);
		
		EventQueue.addEvent(interruptEvent);
	}

	before(Thread t) : join(t) {
		ThreadEvent joinEvent = new ThreadEvent(t.getName(),
				ThreadEventType.BeforeJoin);
		joinEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(joinEvent);
	}
	
	after(Thread t) : join(t) {
		ThreadEvent joinEvent = new ThreadEvent(t.getName(),
				ThreadEventType.AfterJoin);
		joinEvent.setJoinPointName(thisJoinPoint.getSignature().getName());

		
		EventQueue.addEvent(joinEvent);
	}
	
	before(Thread t, long millis) : joinTimeout(t, millis) {
		ThreadEvent joinEvent = new ThreadEvent(t.getName(),
				ThreadEventType.BeforeJoin);
		joinEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		joinEvent.setMillis(millis);
		
		EventQueue.addEvent(joinEvent);
	}
	
	after(Thread t, long millis) : joinTimeout(t, millis) {
		ThreadEvent joinEvent = new ThreadEvent(t.getName(),
				ThreadEventType.AfterJoin);
		joinEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		joinEvent.setMillis(millis);
		
		EventQueue.addEvent(joinEvent);
	}
	
	before(Thread t, long millis, int nanos) : joinFiner(t, millis, nanos) {
		ThreadEvent joinEvent = new ThreadEvent(t.getName(),
				ThreadEventType.BeforeJoin);
		joinEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		joinEvent.setMillis(millis);
		joinEvent.setNanos(nanos);
		
		EventQueue.addEvent(joinEvent);
	}
	
	after(Thread t, long millis, int nanos) : joinFiner(t, millis, nanos) {
		ThreadEvent joinEvent = new ThreadEvent(t.getName(),
				ThreadEventType.AfterJoin);
		joinEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		joinEvent.setMillis(millis);
		joinEvent.setNanos(nanos);
		
		EventQueue.addEvent(joinEvent);
	}
	
	after(Thread t, boolean on) : setDaemon(t, on) {
		ThreadEvent daemonEvent = new ThreadEvent(t.getName(),
				ThreadEventType.DaemonChange);
		daemonEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		daemonEvent.setIsDaemon(on);
		
		EventQueue.addEvent(daemonEvent);
	}
	
	//Record before so we get both names in the event
	before(Thread t, String name) : setName(t, name) {
		ThreadEvent nameEvent = new ThreadEvent(t.getName(),
				ThreadEventType.NameChange);
		nameEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		nameEvent.setNewName(name);
		
		EventQueue.addEvent(nameEvent);
	}
	
	after(Thread t, int priority) : setPriority(t, priority) {
		ThreadEvent priorityEvent = new ThreadEvent(t.getName(),
				ThreadEventType.PriorityChange);
		priorityEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		priorityEvent.setNewPriority(priority);
		
		EventQueue.addEvent(priorityEvent);
	}
	
	before(long millis) : sleep(millis) {
		ThreadEvent sleepEvent = new ThreadEvent(Thread.currentThread().getName(),
				ThreadEventType.BeforeSleep);
		sleepEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		sleepEvent.setMillis(millis);
		
		EventQueue.addEvent(sleepEvent);
	}
	
	after(long millis) : sleep(millis) {
		ThreadEvent sleepEvent = new ThreadEvent(Thread.currentThread().getName(),
				ThreadEventType.AfterSleep);
		sleepEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		sleepEvent.setMillis(millis);
		
		EventQueue.addEvent(sleepEvent);
	}
	
	before(long millis, int nanos) : sleepFiner(millis, nanos) {
		ThreadEvent sleepEvent = new ThreadEvent(Thread.currentThread().getName(),
				ThreadEventType.BeforeSleep);
		sleepEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		sleepEvent.setMillis(millis);
		sleepEvent.setNanos(nanos);
		
		EventQueue.addEvent(sleepEvent);
	}
	
	after(long millis, int nanos) : sleepFiner(millis, nanos) {
		ThreadEvent sleepEvent = new ThreadEvent(Thread.currentThread().getName(),
				ThreadEventType.AfterSleep);
		sleepEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		sleepEvent.setMillis(millis);
		sleepEvent.setNanos(nanos);
		
		EventQueue.addEvent(sleepEvent);
	}
	
	before(Thread t) : start(t) {
		ThreadEvent startEvent = new ThreadEvent(t.getName(), 
				ThreadEventType.Start);
		startEvent.setJoinPointName(thisJoinPoint.getSignature().getName());

		EventQueue.addEvent(startEvent);
	}
	
	after() : yield() {
		ThreadEvent yieldEvent = new ThreadEvent(Thread.currentThread().getName(), 
				ThreadEventType.Yield);
		yieldEvent.setJoinPointName(thisJoinPoint.getSignature().getName());

		EventQueue.addEvent(yieldEvent);
	}
}
