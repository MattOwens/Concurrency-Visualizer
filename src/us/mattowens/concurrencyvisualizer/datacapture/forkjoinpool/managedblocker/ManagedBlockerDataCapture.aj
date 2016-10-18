package us.mattowens.concurrencyvisualizer.datacapture.forkjoinpool.managedblocker;

import java.util.concurrent.ForkJoinPool.ManagedBlocker;

import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public aspect ManagedBlockerDataCapture {

	pointcut block(ManagedBlocker b) :
		call(boolean ManagedBlocker.block()) &&
		target(b);
	
	pointcut isReleasable(ManagedBlocker b) :
		call(boolean ManagedBlocker.isReleasable()) &&
		target(b);
	
	before(ManagedBlocker b) : block(b) {
		ManagedBlockerEvent blockEvent = new ManagedBlockerEvent(b.toString(), 
				ManagedBlockerEventType.BeforeBlock);
		blockEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(blockEvent);
	}
	
	after(ManagedBlocker b) returning(boolean blockingUnnecessary) : block(b) {
		ManagedBlockerEvent blockEvent = new ManagedBlockerEvent(b.toString(), 
				ManagedBlockerEventType.AfterBlock);
		blockEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		blockEvent.setBlockingUnnecessary(blockingUnnecessary);
		
		EventQueue.addEvent(blockEvent);
	}
	
	after(ManagedBlocker b) returning(boolean blockingUnnecessary) : isReleasable(b) {
		ManagedBlockerEvent blockEvent = new ManagedBlockerEvent(b.toString(), 
				ManagedBlockerEventType.IsReleasable);
		blockEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		blockEvent.setBlockingUnnecessary(blockingUnnecessary);
		
		EventQueue.addEvent(blockEvent);
	}
}
