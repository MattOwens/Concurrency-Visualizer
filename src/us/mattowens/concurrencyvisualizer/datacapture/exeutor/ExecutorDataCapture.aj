package us.mattowens.concurrencyvisualizer.datacapture.exeutor;

import java.util.concurrent.Executor;

import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public aspect ExecutorDataCapture {

	pointcut execute(Executor e, Runnable r) :
		call(void Executor.execute(Runnable)) &&
		target(e) &&
		args(r);
	
	before(Executor e, Runnable r) : execute(e, r) {
		Event executeEvent = new Event(EventClass.Executor, 
				ExecutorEventType.BeforeExecute, e.toString());
		executeEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		executeEvent.addValue(StringConstants.RUNNABLE, r.toString());
		
		EventQueue.addEvent(executeEvent);
	}
	
	after(Executor e, Runnable r) : execute(e, r) {
		Event executeEvent = new Event(EventClass.Executor, 
				ExecutorEventType.AfterExecute, e.toString());
		executeEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		executeEvent.addValue(StringConstants.RUNNABLE,r.toString());
		
		EventQueue.addEvent(executeEvent);
	}
}
