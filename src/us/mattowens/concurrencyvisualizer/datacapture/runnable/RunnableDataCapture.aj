package us.mattowens.concurrencyvisualizer.datacapture.runnable;

import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public aspect RunnableDataCapture {

	pointcut create() :
		call(Runnable.new()) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	pointcut run(Runnable r) :
		execution(void Runnable.run()) &&
		target(r) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	after() returning(Runnable r) : create() {
		Event createEvent = new Event(EventClass.Runnable, RunnableEventType.Create, r.toString());
		createEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(createEvent);
	}
	
	before(Runnable r) : run(r) {
		Event runEvent = new Event(EventClass.Runnable, RunnableEventType.BeforeRun, r.toString());
		runEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());

		EventQueue.addEvent(runEvent);
	}
	
	after(Runnable r) : run(r) {
		Event runEvent = new Event(EventClass.Runnable, RunnableEventType.AfterRun, r.toString());
		runEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());

		EventQueue.addEvent(runEvent);
	}
}
