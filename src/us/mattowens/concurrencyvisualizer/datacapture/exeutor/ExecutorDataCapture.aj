package us.mattowens.concurrencyvisualizer.datacapture.exeutor;

import java.util.concurrent.Executor;

import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public aspect ExecutorDataCapture {

	pointcut execute(Executor e, Runnable r) :
		call(void Executor.execute(Runnable)) &&
		target(e) &&
		args(r);
	
	before(Executor e, Runnable r) : execute(e, r) {
		ExecutorEvent executeEvent = new ExecutorEvent(e.toString(), 
				ExecutorEventType.BeforeExecute);
		executeEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		executeEvent.setRunnable(r.toString());
		
		EventQueue.addEvent(executeEvent);
	}
	
	after(Executor e, Runnable r) : execute(e, r) {
		ExecutorEvent executeEvent = new ExecutorEvent(e.toString(), 
				ExecutorEventType.AfterExecute);
		executeEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		executeEvent.setRunnable(r.toString());
		
		EventQueue.addEvent(executeEvent);
	}
}
