package us.mattowens.concurrencyvisualizer.datacapture.abstractownablesynchronizer;

import java.util.concurrent.locks.AbstractOwnableSynchronizer;

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
		AbstractOwnableSynchronizerEvent event = new AbstractOwnableSynchronizerEvent(s.toString(),
				AbstractOwnableSynchronizerEventType.Create);
		event.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(event);
	}
	
	after(AbstractOwnableSynchronizer s) returning(Thread t) : getOwnerThread(s) {
		AbstractOwnableSynchronizerEvent event = new AbstractOwnableSynchronizerEvent(s.toString(),
				AbstractOwnableSynchronizerEventType.GetOwnerThread);
		event.setJoinPointName(thisJoinPoint.getSignature().getName());
		event.setThreadName(t.getName());
		
		EventQueue.addEvent(event);
	}
	
	before(AbstractOwnableSynchronizer s, Thread t) : setOwnerThread(s, t) {
		AbstractOwnableSynchronizerEvent event = new AbstractOwnableSynchronizerEvent(s.toString(),
				AbstractOwnableSynchronizerEventType.SetOwnerThread);
		event.setJoinPointName(thisJoinPoint.getSignature().getName());
		event.setThreadName(t.getName());
		
		EventQueue.addEvent(event);
	}
}
