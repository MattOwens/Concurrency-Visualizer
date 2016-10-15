package us.mattowens.concurrencyvisualizer.datacapture.timer;

import java.util.TimerTask;

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
		TimerTaskEvent createEvent = new TimerTaskEvent(t.toString(), 
				TimerTaskEventType.Create);
		
		EventQueue.addEvent(createEvent);
	}
	
	before(TimerTask t) : cancel(t) {
		TimerTaskEvent cancelEvent = new TimerTaskEvent(t.toString(), 
				TimerTaskEventType.Cancel);
		
		EventQueue.addEvent(cancelEvent);
	}
	
	before(TimerTask t) : run(t) {
		TimerTaskEvent runEvent = new TimerTaskEvent(t.toString(), 
				TimerTaskEventType.Run);
		
		EventQueue.addEvent(runEvent);
	}

}
