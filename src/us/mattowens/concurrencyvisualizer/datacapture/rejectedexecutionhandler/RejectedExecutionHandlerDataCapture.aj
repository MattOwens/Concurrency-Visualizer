package us.mattowens.concurrencyvisualizer.datacapture.rejectedexecutionhandler;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
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
		
		Event handleEvent = new Event(EventClass.RejectedExecutionHandler,
				RejectedExecutionHandlerEventType.RejectedExecution, h.toString());
		handleEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		handleEvent.addValue(StringConstants.RUNNABLE, r.toString());
		handleEvent.addValue(StringConstants.EXECUTOR, e.toString());
		
		EventQueue.addEvent(handleEvent);
	}
	
	
				

}
