package us.mattowens.concurrencyvisualizer.datacapture.callable;

public aspect CallableDataCapture {

	 /*pointcut callPointcut(Callable c) :
		call(Object Callable.call()) &&
		target(c);
	
	before(Callable c) : callPointcut(c) {
		CallableEvent callableEvent = new CallableEvent(c.toString(),
				CallableEventType.BeforeCall);
		callableEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(callableEvent);
	}
	
	after(Callable c)  returning(Object result) : callPointcut(c) {
		CallableEvent callableEvent = new CallableEvent(c.toString(),
				CallableEventType.AfterCall);
		callableEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		callableEvent.setResult(result);
		
		EventQueue.addEvent(callableEvent);
	}*/
}
