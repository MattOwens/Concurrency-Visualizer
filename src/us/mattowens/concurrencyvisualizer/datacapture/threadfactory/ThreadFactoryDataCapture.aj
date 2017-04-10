package us.mattowens.concurrencyvisualizer.datacapture.threadfactory;

import java.util.concurrent.ThreadFactory;

import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public aspect ThreadFactoryDataCapture {
	
	pointcut newThread(ThreadFactory f, Runnable r) :
		call(Thread ThreadFactory.newThread(Runnable)) &&
		target(f) &&
		args(r);
	
	after(ThreadFactory f, Runnable r) returning(Thread t) : newThread(f, r) {
		Event newThreadEvent = new Event(EventClass.ThreadFactory, 
				ThreadFactoryEventType.NewThread, f.toString());
		newThreadEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		newThreadEvent.addValue(StringConstants.RUNNABLE, r.toString());
		newThreadEvent.addValue(StringConstants.NEW_THREAD, t.getName());
		
		EventQueue.addEvent(newThreadEvent);
	}
}
