package us.mattowens.concurrencyvisualizer.datacapture.rejectedexecutionhandler;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public aspect RejectedExecutionHandlerDataCapture {
	
	pointcut rejectedExecution(RejectedExecutionHandler h, Runnable r, 
			ThreadPoolExecutor e) :
			call(void RejectedExecutionHandler.rejectedExecution(Runnable, 
					ThreadPoolExecutor)) &&
			target(h) &&
			args(r, e);
	
	
	before(RejectedExecutionHandler h, Runnable r, ThreadPoolExecutor e) :
		rejectedExecution(h, r, e) {
		
		RejectedExecutionHandlerEvent handleEvent = new RejectedExecutionHandlerEvent(
				h.toString(), RejectedExecutionHandlerEventType.RejectedExecution);
		handleEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		handleEvent.setRunnable(r.toString());
		handleEvent.setThreadPoolExecutor(e.toString());
		
		EventQueue.addEvent(handleEvent);
	}
	
	
				

}
