package us.mattowens.concurrencyvisualizer.datacapture.threadgroup;

import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public aspect ThreadGroupDataCapture {

	pointcut create(String name) :
		call(ThreadGroup.new(String)) &&
		args(name);
	
	pointcut createWithParent(ThreadGroup parent, String name) :
		call(ThreadGroup.new(ThreadGroup, String)) &&
		args(parent, name);
	
	pointcut destroy(ThreadGroup g) :
		call(void ThreadGroup.destroy()) &&
		target(g);
	
	pointcut interrupt(ThreadGroup g) :
		call(void ThreadGroup.interrupt()) &&
		target(g);
	
	pointcut setDaemon(ThreadGroup g, boolean daemon) :
		call(void ThreadGroup.setDaemon(boolean)) &&
		target(g) &&
		args(daemon);
	
	pointcut setMaxPriority(ThreadGroup g, int maxPriority) :
		call(void ThreadGroup.setMaxPriority(int)) &&
		target(g) &&
		args(maxPriority);
	
	before(String name) : create(name) {
		ThreadGroupEvent createEvent = new ThreadGroupEvent(name, 
				ThreadGroupEventType.Create);
		createEvent.setJoinPointName(thisJoinPoint.getSignature().toString());
		
		EventQueue.addEvent(createEvent);
	}
	
	before(ThreadGroup parent, String name) : createWithParent(parent, name) {
		ThreadGroupEvent createEvent = new ThreadGroupEvent(name, 
				ThreadGroupEventType.Create);
		createEvent.setJoinPointName(thisJoinPoint.getSignature().toString());
		createEvent.setParentName(parent.getName());
		
		EventQueue.addEvent(createEvent);
	}
	
	before(ThreadGroup g) : destroy(g) {
		ThreadGroupEvent destroyEvent = new ThreadGroupEvent(g.getName(), 
				ThreadGroupEventType.Destroy);
		destroyEvent.setJoinPointName(thisJoinPoint.getSignature().toString());
		
		EventQueue.addEvent(destroyEvent);
	}
	
	before(ThreadGroup g) : interrupt(g) {
		ThreadGroupEvent interruptEvent = new ThreadGroupEvent(g.getName(), 
				ThreadGroupEventType.Interrupt);
		interruptEvent.setJoinPointName(thisJoinPoint.getSignature().toString());
		
		EventQueue.addEvent(interruptEvent);
	}
	
	before(ThreadGroup g, boolean daemon) : setDaemon(g, daemon) {
		ThreadGroupEvent daemonEvent = new ThreadGroupEvent(g.getName(), 
				ThreadGroupEventType.DaemonChange);
		daemonEvent.setJoinPointName(thisJoinPoint.getSignature().toString());
		daemonEvent.setDameon(daemon);
		
		EventQueue.addEvent(daemonEvent);
	}
	
	before(ThreadGroup g, int maxPriority) : setMaxPriority(g, maxPriority) {
		ThreadGroupEvent priorityEvent = new ThreadGroupEvent(g.getName(), 
				ThreadGroupEventType.PriorityChange);
		priorityEvent.setJoinPointName(thisJoinPoint.getSignature().toString());
		priorityEvent.setMaxPriority(maxPriority);
		
		EventQueue.addEvent(priorityEvent);
	}
}
