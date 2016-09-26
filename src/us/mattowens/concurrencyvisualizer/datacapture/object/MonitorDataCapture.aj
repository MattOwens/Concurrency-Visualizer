package us.mattowens.concurrencyvisualizer.datacapture.object;

import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public aspect MonitorDataCapture {
	
	pointcut synchronizedMethod(Object o):
		call(synchronized * *(..)) &&
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
		MonitorEvent beforeEvent = new MonitorEvent(MonitorEventType.BeforeWait);
		beforeEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(beforeEvent);
	}
	
	after(Object o) : wait(o) {
		MonitorEvent afterEvent = new MonitorEvent(MonitorEventType.AfterWait);
		afterEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(afterEvent);
	}
	
	before(Object o, long timeout) : waitTimeout(o, timeout) {
		MonitorEvent beforeEvent = new MonitorEvent(MonitorEventType.BeforeWait);
		beforeEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		beforeEvent.setTimeout(timeout);
		
		EventQueue.addEvent(beforeEvent);	
	}
	
	after(Object o, long timeout) : waitTimeout(o, timeout) {
		MonitorEvent afterEvent = new MonitorEvent(MonitorEventType.AfterWait);
		afterEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		afterEvent.setTimeout(timeout);
		
		EventQueue.addEvent(afterEvent);
	}
	
	before(Object o, long timeout, int nanos) : waitFinerTimeout(o, timeout, nanos) {
		MonitorEvent beforeEvent = new MonitorEvent(MonitorEventType.BeforeWait);
		beforeEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		beforeEvent.setTimeout(timeout);
		beforeEvent.setNanos(nanos);
		
		EventQueue.addEvent(beforeEvent);	
	}
	
	after(Object o, long timeout, int nanos) : waitFinerTimeout(o, timeout, nanos) {
		MonitorEvent afterEvent = new MonitorEvent(MonitorEventType.AfterWait);
		afterEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		afterEvent.setTimeout(timeout);
		afterEvent.setNanos(nanos);
		
		EventQueue.addEvent(afterEvent);
	}
	
	after(Object o) : notify(o) {
		MonitorEvent notifyEvent = new MonitorEvent(MonitorEventType.Notify);
		notifyEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(notifyEvent);
	}
	
	after(Object o) : notifyAll(o) {
		MonitorEvent notifyEvent = new MonitorEvent(MonitorEventType.NotifyAll);
		notifyEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(notifyEvent);
	}
	
	before(Object o) : synchronizedMethod(o) {
		MonitorEvent syncEvent = new MonitorEvent(MonitorEventType.BeforeSynchronized);
		syncEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(syncEvent);
	}
	
	after(Object o) : synchronizedMethod(o) {
		MonitorEvent syncEvent = new MonitorEvent(MonitorEventType.AfterSynchronized);
		syncEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(syncEvent);
		
	}
}
