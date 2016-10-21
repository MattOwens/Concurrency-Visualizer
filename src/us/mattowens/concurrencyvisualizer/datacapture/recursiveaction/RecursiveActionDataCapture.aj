package us.mattowens.concurrencyvisualizer.datacapture.recursiveaction;

import java.util.concurrent.RecursiveAction;

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
		RecursiveActionEvent createEvent = new RecursiveActionEvent(a.toString(), 
				RecursiveActionEventType.Create);
		createEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(createEvent);
	}
	
	before(RecursiveAction a) : compute(a) {
		RecursiveActionEvent computeEvent = new RecursiveActionEvent(a.toString(), 
				RecursiveActionEventType.BeforeCompute);
		computeEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(computeEvent);
	}
	
	after(RecursiveAction a) : compute(a) {
		RecursiveActionEvent computeEvent = new RecursiveActionEvent(a.toString(), 
				RecursiveActionEventType.AfterCompute);
		computeEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(computeEvent);
	}
	
	before(RecursiveAction a) : exec(a) {
		RecursiveActionEvent computeEvent = new RecursiveActionEvent(a.toString(), 
				RecursiveActionEventType.BeforeExec);
		computeEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(computeEvent);
	}
	
	after(RecursiveAction a) returning(boolean completedNormally) : compute(a) {
		RecursiveActionEvent computeEvent = new RecursiveActionEvent(a.toString(), 
				RecursiveActionEventType.AfterExec);
		computeEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		computeEvent.setCompletedNormally(completedNormally);
		
		EventQueue.addEvent(computeEvent);
	}
}
