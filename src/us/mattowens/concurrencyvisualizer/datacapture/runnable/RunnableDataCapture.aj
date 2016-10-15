package us.mattowens.concurrencyvisualizer.datacapture.runnable;

import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public aspect RunnableDataCapture {

	pointcut create() :
		call(Runnable.new()) &&
		!within(us.mattowens.concurrencyvisualizer.datacapture..*);
	
	pointcut run(Runnable r) :
		execution(void Runnable.run()) &&
		target(r) &&
		!within(us.mattowens.concurrencyvisualizer.datacapture..*);
	
	after() returning(Runnable r) : create() {
		RunnableEvent createEvent = new RunnableEvent(r.toString(), RunnableEventType.Create);
		createEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(createEvent);
	}
	
	before(Runnable r) : run(r) {
		RunnableEvent runEvent = new RunnableEvent(r.toString(), RunnableEventType.BeforeRun);
		runEvent.setJoinPointName(thisJoinPoint.getSignature().getName());

		EventQueue.addEvent(runEvent);
	}
	
	after(Runnable r) : run(r) {
		RunnableEvent runEvent = new RunnableEvent(r.toString(), RunnableEventType.AfterRun);
		runEvent.setJoinPointName(thisJoinPoint.getSignature().getName());

		EventQueue.addEvent(runEvent);
	}
}
