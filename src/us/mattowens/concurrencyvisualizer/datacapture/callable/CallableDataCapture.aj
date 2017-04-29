package us.mattowens.concurrencyvisualizer.datacapture.callable;

import java.util.concurrent.Callable;

import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public aspect CallableDataCapture {

	pointcut create() :
		call(Callable.new());
	
	pointcut callPointcut() :
		call(* Callable.call());
	
	after() : create() {
		System.out.println("There's a callable being created");
	}
	before() : callPointcut() {
		System.out.println("Matched call to Callable.call()");
		Callable<?> c = (Callable<?>) thisJoinPoint.getTarget();
		CallableEvent callableEvent = new CallableEvent(c.toString(),
				CallableEventType.BeforeCall);
		callableEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(callableEvent);
	}
	
	/*after()  returning(* result) : callPointcut() {
		Callable<?> c = (Callable<?>) thisJoinPoint.getTarget();
		CallableEvent callableEvent = new CallableEvent(c.toString(),
				CallableEventType.AfterCall);
		callableEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		callableEvent.setResult(result);
		
		EventQueue.addEvent(callableEvent);
	}*/
}
