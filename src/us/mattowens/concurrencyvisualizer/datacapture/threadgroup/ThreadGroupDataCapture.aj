package us.mattowens.concurrencyvisualizer.datacapture.threadgroup;

import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
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
		Event createEvent = new Event(EventClass.ThreadGroup, 
				ThreadGroupEventType.Create, name);
		createEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().toString());
		
		EventQueue.addEvent(createEvent);
	}
	
	before(ThreadGroup parent, String name) : createWithParent(parent, name) {
		Event createEvent = new Event(EventClass.ThreadGroup,  
				ThreadGroupEventType.Create, name);
		createEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().toString());
		createEvent.addValue(StringConstants.PARENT, parent.getName());
		
		EventQueue.addEvent(createEvent);
	}
	
	before(ThreadGroup g) : destroy(g) {
		Event destroyEvent = new Event(EventClass.ThreadGroup,  
				ThreadGroupEventType.Destroy, g.getName());
		destroyEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().toString());
		
		EventQueue.addEvent(destroyEvent);
	}
	
	before(ThreadGroup g) : interrupt(g) {
		Event interruptEvent = new Event(EventClass.ThreadGroup, 
				ThreadGroupEventType.Interrupt, g.getName());
		interruptEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().toString());
		
		EventQueue.addEvent(interruptEvent);
	}
	
	before(ThreadGroup g, boolean daemon) : setDaemon(g, daemon) {
		Event daemonEvent = new Event(EventClass.ThreadGroup, 
				ThreadGroupEventType.DaemonChange, g.getName());
		daemonEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().toString());
		daemonEvent.addValue(StringConstants.DAEMON, daemon);
		
		EventQueue.addEvent(daemonEvent);
	}
	
	before(ThreadGroup g, int maxPriority) : setMaxPriority(g, maxPriority) {
		Event priorityEvent = new Event(EventClass.ThreadGroup,  
				ThreadGroupEventType.PriorityChange, g.getName());
		priorityEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().toString());
		priorityEvent.addValue(StringConstants.MAX_PRIORITY, maxPriority);
		
		EventQueue.addEvent(priorityEvent);
	}
}
