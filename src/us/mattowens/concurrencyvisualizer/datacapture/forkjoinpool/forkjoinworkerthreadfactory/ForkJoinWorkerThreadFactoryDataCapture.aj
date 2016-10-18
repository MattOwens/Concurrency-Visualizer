package us.mattowens.concurrencyvisualizer.datacapture.forkjoinpool.forkjoinworkerthreadfactory;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory;

import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

import java.util.concurrent.ForkJoinWorkerThread;

public aspect ForkJoinWorkerThreadFactoryDataCapture {

	pointcut newThread(ForkJoinWorkerThreadFactory f, ForkJoinPool pool) :
		call(ForkJoinWorkerThread ForkJoinWorkerThreadFactory.newThread(ForkJoinPool)) &&
		target(f) &&
		args(pool);
	
	after(ForkJoinWorkerThreadFactory f, ForkJoinPool pool) 
	returning(ForkJoinWorkerThread t) : newThread(f, pool) {
		ForkJoinWorkerThreadFactoryEvent newThreadEvent = new ForkJoinWorkerThreadFactoryEvent(
				f.toString(), ForkJoinWorkerThreadFactoryEventType.NewThread);
		newThreadEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		newThreadEvent.setForkJoinPool(pool.toString());
		newThreadEvent.setForkJoinWorkerThread(t.getName());
		
		EventQueue.addEvent(newThreadEvent);
	}
}
