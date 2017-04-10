package us.mattowens.concurrencyvisualizer.datacapture.abstractownablesynchronizer;

import java.util.concurrent.locks.AbstractOwnableSynchronizer;

import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public aspect AbstractOwnableSynchronizerDataCapture {

	pointcut create() :
		call(AbstractOwnableSynchronizer.new(..));
	
	pointcut getOwnerThread(AbstractOwnableSynchronizer s) :
		call(Thread AbstractOwnableSynchronizer.getExclusiveOwnerThread()) &&
		target(s);
	
	pointcut setOwnerThread(AbstractOwnableSynchronizer s, Thread t) :
		call(void AbstractOwnableSynchronizer.setExclusiveOwnerThread(Thread)) &&
		target(s) &&
		args(t);
		
	
	after() returning(AbstractOwnableSynchronizer s) : create() {
		Event event = new Event(EventClass.AbstractOwnableSynchronizer,
				AbstractOwnableSynchronizerEventType.Create, s.toString());
		event.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(event);
	}
	
	after(AbstractOwnableSynchronizer s) returning(Thread t) : getOwnerThread(s) {
		Event event = new Event(EventClass.AbstractOwnableSynchronizer,
				AbstractOwnableSynchronizerEventType.GetOwnerThread, s.toString());
		event.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		event.addValue(StringConstants.OWNER_THREAD,t.getName());
		
		EventQueue.addEvent(event);
	}
	
	before(AbstractOwnableSynchronizer s, Thread t) : setOwnerThread(s, t) {
		Event event = new Event(EventClass.AbstractOwnableSynchronizer, 
				AbstractOwnableSynchronizerEventType.SetOwnerThread, s.toString());
		event.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		event.addValue(StringConstants.OWNER_THREAD, t.getName());
		
		EventQueue.addEvent(event);
	}
}
