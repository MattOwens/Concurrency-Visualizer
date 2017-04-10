package us.mattowens.concurrencyvisualizer.datacapture.forkjoinpool.managedblocker;

import java.util.concurrent.ForkJoinPool.ManagedBlocker;

import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public aspect ManagedBlockerDataCapture {

	pointcut block(ManagedBlocker b) :
		call(boolean ManagedBlocker.block()) &&
		target(b);
	
	pointcut isReleasable(ManagedBlocker b) :
		call(boolean ManagedBlocker.isReleasable()) &&
		target(b);
	
	before(ManagedBlocker b) : block(b) {
		Event blockEvent = new Event(EventClass.ManagedBlocker, 
				ManagedBlockerEventType.BeforeBlock, b.toString());
		blockEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(blockEvent);
	}
	
	after(ManagedBlocker b) returning(boolean blockingUnnecessary) : block(b) {
		Event blockEvent = new Event(EventClass.ManagedBlocker,
				ManagedBlockerEventType.AfterBlock, b.toString());
		blockEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		blockEvent.addValue(StringConstants.BLOCKING_UNNECESSARY, blockingUnnecessary);
		
		EventQueue.addEvent(blockEvent);
	}
	
	after(ManagedBlocker b) returning(boolean blockingUnnecessary) : isReleasable(b) {
		Event blockEvent = new Event(EventClass.ManagedBlocker, 
				ManagedBlockerEventType.IsReleasable, b.toString());
		blockEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		blockEvent.addValue(StringConstants.BLOCKING_UNNECESSARY, blockingUnnecessary);
		
		EventQueue.addEvent(blockEvent);
	}
}
