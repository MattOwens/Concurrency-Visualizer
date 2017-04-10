package us.mattowens.concurrencyvisualizer.datacapture.timer;

import java.util.TimerTask;

import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public aspect TimerTaskDataCapture {
	
	pointcut create() :
		call(TimerTask.new());
	
	pointcut cancel(TimerTask t) :
		call(void TimerTask.cancel()) &&
		target(t);
	
	pointcut run(TimerTask t) :
		call(void TimerTask.run()) &&
		target(t);
	
	
	after() returning(TimerTask t) : create() {
		Event createEvent = new Event(EventClass.TimerTask, 
				TimerTaskEventType.Create, t.toString());
		createEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(createEvent);
	}
	
	before(TimerTask t) : cancel(t) {
		Event cancelEvent = new Event(EventClass.TimerTask, 
				TimerTaskEventType.Cancel, t.toString());
		cancelEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(cancelEvent);
	}
	
	before(TimerTask t) : run(t) {
		Event runEvent = new Event(EventClass.TimerTask,  
				TimerTaskEventType.Run, t.toString());
		runEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		EventQueue.addEvent(runEvent);
	}

}
