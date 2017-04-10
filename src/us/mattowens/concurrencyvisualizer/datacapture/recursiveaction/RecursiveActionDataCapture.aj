package us.mattowens.concurrencyvisualizer.datacapture.recursiveaction;

import java.util.concurrent.RecursiveAction;

import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public aspect RecursiveActionDataCapture {

	pointcut create() :
		call(RecursiveAction.new());
	
	pointcut compute(RecursiveAction a) :
		call(void RecursiveAction.compute()) &&
		target(a);
	
	pointcut exec(RecursiveAction a) :
		call(boolean RecursiveAction.exec()) &&
		target(a);
	
	after() returning(RecursiveAction a) : create() {
		Event createEvent = new Event(EventClass.RecursiveAction, 
				RecursiveActionEventType.Create, a.toString());
		createEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(createEvent);
	}
	
	before(RecursiveAction a) : compute(a) {
		Event computeEvent = new Event(EventClass.RecursiveAction, 
				RecursiveActionEventType.BeforeCompute, a.toString());
		computeEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(computeEvent);
	}
	
	after(RecursiveAction a) : compute(a) {
		Event computeEvent = new Event(EventClass.RecursiveAction, 
				RecursiveActionEventType.AfterCompute, a.toString());
		computeEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(computeEvent);
	}
	
	before(RecursiveAction a) : exec(a) {
		Event computeEvent = new Event(EventClass.RecursiveAction, 
				RecursiveActionEventType.BeforeExec, a.toString());
		computeEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(computeEvent);
	}
	
	after(RecursiveAction a) returning(boolean completedNormally) : compute(a) {
		Event computeEvent = new Event(EventClass.RecursiveAction, 
				RecursiveActionEventType.AfterExec, a.toString());
		computeEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		computeEvent.addValue(StringConstants.COMPLETED_NORMALLY, completedNormally);
		
		EventQueue.addEvent(computeEvent);
	}
}
