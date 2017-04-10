package us.mattowens.concurrencyvisualizer.datacapture.forkjoinpool.forkjoinworkerthreadfactory;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory;

import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

import java.util.concurrent.ForkJoinWorkerThread;

public aspect ForkJoinWorkerThreadFactoryDataCapture {

	pointcut newThread(ForkJoinWorkerThreadFactory f, ForkJoinPool pool) :
		call(ForkJoinWorkerThread ForkJoinWorkerThreadFactory.newThread(ForkJoinPool)) &&
		target(f) &&
		args(pool);
	
	after(ForkJoinWorkerThreadFactory f, ForkJoinPool pool) 
	returning(ForkJoinWorkerThread t) : newThread(f, pool) {
		Event newThreadEvent = new Event(EventClass.ForkJoinWorkerThreadFactory,
				ForkJoinWorkerThreadFactoryEventType.NewThread, f.toString());
		newThreadEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		newThreadEvent.addValue(StringConstants.FORK_JOIN_POOL, pool.toString());
		newThreadEvent.addValue(StringConstants.WORKER_THREAD, t.getName());
		
		EventQueue.addEvent(newThreadEvent);
	}
}
