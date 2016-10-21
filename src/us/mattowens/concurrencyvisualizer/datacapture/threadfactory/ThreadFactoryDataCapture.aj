package us.mattowens.concurrencyvisualizer.datacapture.threadfactory;

import java.util.concurrent.ThreadFactory;

import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public aspect ThreadFactoryDataCapture {
	
	pointcut newThread(ThreadFactory f, Runnable r) :
		call(Thread ThreadFactory.newThread(Runnable)) &&
		target(f) &&
		args(r);
	
	after(ThreadFactory f, Runnable r) returning(Thread t) : newThread(f, r) {
		ThreadFactoryEvent newThreadEvent = new ThreadFactoryEvent(f.toString(), 
				ThreadFactoryEventType.NewThread);
		newThreadEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		newThreadEvent.setRunnable(r.toString());
		newThreadEvent.setThread(t.getName());
		
		EventQueue.addEvent(newThreadEvent);
	}
}
