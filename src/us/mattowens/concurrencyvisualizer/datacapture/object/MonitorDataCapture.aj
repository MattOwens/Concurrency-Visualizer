package us.mattowens.concurrencyvisualizer.datacapture.object;

import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public aspect MonitorDataCapture {
	
	pointcut synchronizedMethod(Object o):
		call(synchronized * *(..)) &&
		!within(us.mattowens.concurrencyvisualizer..*) &&
		target(o);
	
	pointcut notify(Object o):
		call(void Object.notify()) &&
		target(o);
	
	pointcut notifyAll(Object o):
		call(void Object.notifyAll()) &&
		target(o);
	
	pointcut wait(Object o):
		call(void Object.wait()) &&
		target(o);
	
	pointcut waitTimeout(Object o, long timeout):
		call(void Object.wait(long)) &&
		target(o) &&
		args(timeout);
	
	pointcut waitFinerTimeout(Object o, long timeout, int nanos):
		call(void Object.wiat(long, int)) &&
		target(o) &&
		args(timeout, nanos);
	

	before(Object o) : wait(o) {
		Event beforeEvent = new Event(EventClass.Monitor, MonitorEventType.BeforeWait, o.toString());
		beforeEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(beforeEvent);
	}
	
	after(Object o) : wait(o) {
		Event afterEvent = new Event(EventClass.Monitor, MonitorEventType.AfterWait, o.toString());
		afterEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(afterEvent);
	}
	
	before(Object o, long timeout) : waitTimeout(o, timeout) {
		Event beforeEvent = new Event(EventClass.Monitor, MonitorEventType.BeforeWait, o.toString());
		beforeEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		beforeEvent.addValue(StringConstants.TIMEOUT, timeout);
		
		EventQueue.addEvent(beforeEvent);	
	}
	
	after(Object o, long timeout) : waitTimeout(o, timeout) {
		Event afterEvent = new Event(EventClass.Monitor, MonitorEventType.AfterWait, o.toString());
		afterEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(afterEvent);
	}
	
	before(Object o, long timeout, int nanos) : waitFinerTimeout(o, timeout, nanos) {
		Event beforeEvent = new Event(EventClass.Monitor, MonitorEventType.BeforeWait, o.toString());
		beforeEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		beforeEvent.addValue(StringConstants.TIMEOUT, timeout);
		beforeEvent.addValue(StringConstants.NANOS, nanos);
		
		EventQueue.addEvent(beforeEvent);	
	}
	
	after(Object o, long timeout, int nanos) : waitFinerTimeout(o, timeout, nanos) {
		Event afterEvent = new Event(EventClass.Monitor, MonitorEventType.AfterWait, o.toString());
		afterEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(afterEvent);
	}
	
	after(Object o) : notify(o) {
		Event notifyEvent = new Event(EventClass.Monitor, MonitorEventType.Notify, o.toString());
		notifyEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(notifyEvent);
	}
	
	after(Object o) : notifyAll(o) {
		Event notifyEvent = new Event(EventClass.Monitor, MonitorEventType.NotifyAll, o.toString());
		notifyEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(notifyEvent);
	}
	
	before(Object o) : synchronizedMethod(o) {
		Event syncEvent = new Event(EventClass.Monitor, MonitorEventType.BeforeSynchronized, o.toString());
		syncEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(syncEvent);
	}
	
	after(Object o) : synchronizedMethod(o) {
		Event syncEvent = new Event(EventClass.Monitor, MonitorEventType.AfterSynchronized, o.toString());
		syncEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(syncEvent);
	}
}
