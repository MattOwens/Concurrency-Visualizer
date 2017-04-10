package us.mattowens.concurrencyvisualizer.datacapture.timer;

import java.util.Timer;
import java.util.TimerTask;

import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

import java.util.Date;

public aspect TimerDataCapture {

	pointcut create() :
		call(Timer.new());
	
	pointcut createDaemon(boolean isDaemon) :
		call(Timer.new(boolean)) &&
		args(isDaemon);
	
	pointcut createName(String name) :
		call(Timer.new(String)) &&
		args(name);
	
	pointcut createNameDaemon(String name, boolean isDaemon) :
		call(Timer.new(String, boolean)) &&
		args(name, isDaemon);
	
	pointcut cancel(Timer t) :
		call(void Timer.cancel()) &&
		target(t);
	
	pointcut purge(Timer t) :
		call(int Timer.purge()) &&
		target(t);
	
	pointcut scheduleSetTime(Timer t, TimerTask task, Date time) :
		call(void Timer.schedule(TimerTask, Date)) &&
		target(t) &&
		args(task, time);
	
	pointcut scheduleDelay(Timer t, TimerTask task, long delay) :
		call(void Timer.schedule(TimerTask, long)) &&
		target(t) &&
		args(task, delay);
	
	pointcut schedulePeriodicSetTime(Timer t, TimerTask task, Date start, long period) :
		call(void Timer.schedule(TimerTask, Date, long)) &&
		target(t) &&
		args(task, start, period);
	
	pointcut schedulePeriodicDelay(Timer t, TimerTask task, long delay, long period) :
		call(void Timer.schedule(TimerTask, long, long)) &&
		target(t) &&
		args(task, delay, period);
	
	pointcut scheduleAtFixedRateSetTime(Timer t, TimerTask task, Date start, long period) :
		call(void Timer.scheduleAtFixedRate(TimerTask, Date, long)) &&
		target(t) &&
		args(task, start, period);
	
	pointcut scheduleAtFixedRateDelay(Timer t, TimerTask task, long delay, long period) :
		call(void Timer.scheduleAtFixedRate(TimerTask, long, long)) &&
		target(t) &&
		args(task, delay, period);
	
	after() returning(Timer t) : create() {
		Event createEvent = new Event(EventClass.Timer, TimerEventType.Create, t.toString());
		createEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(createEvent);
	}
	
	after(boolean isDaemon) returning(Timer t) : createDaemon(isDaemon) {
		Event createEvent = new Event(EventClass.Timer, TimerEventType.Create, t.toString());
		createEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		createEvent.addValue(StringConstants.DAEMON, isDaemon);
		
		EventQueue.addEvent(createEvent);
	}
	
	after(String name) returning(Timer t) : createName(name) {
		Event createEvent = new Event(EventClass.Timer, TimerEventType.Create, name);
		createEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(createEvent);
	}
	
	after(String name, boolean isDaemon) returning(Timer t) :
		createNameDaemon(name, isDaemon) {
		Event createEvent = new Event(EventClass.Timer, TimerEventType.Create, name);
		createEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		createEvent.addValue(StringConstants.DAEMON, isDaemon);
		
		EventQueue.addEvent(createEvent);
	}
		
	after(Timer t) : cancel(t) {
		Event cancelEvent = new Event(EventClass.Timer, TimerEventType.Cancel, t.toString());
		cancelEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
		EventQueue.addEvent(cancelEvent);
	}
	
	after(Timer t) returning(int numPurged) : purge(t) {
		Event purgeEvent = new Event(EventClass.Timer, TimerEventType.Purge, t.toString());
		purgeEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		purgeEvent.addValue(StringConstants.NUM_PURGED, numPurged);
		
		
		EventQueue.addEvent(purgeEvent);
	}
	
	before(Timer t, TimerTask task, Date time) : scheduleSetTime(t, task, time) {
		Event scheduleEvent = new Event(EventClass.Timer, TimerEventType.Schedule, t.toString());
		scheduleEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		scheduleEvent.addValue(StringConstants.TASK, task.toString());
		scheduleEvent.addValue(StringConstants.START_TIME, time);
		
		EventQueue.addEvent(scheduleEvent);
	}
	
	before(Timer t, TimerTask task, long delay) : scheduleDelay(t, task, delay) {
		Event scheduleEvent = new Event(EventClass.Timer, TimerEventType.Schedule, t.toString());
		scheduleEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		scheduleEvent.addValue(StringConstants.TASK, task.toString());
		scheduleEvent.addValue(StringConstants.DELAY, delay);
		
		EventQueue.addEvent(scheduleEvent);
	}
	
	before(Timer t, TimerTask task, Date time, long period) :
		schedulePeriodicSetTime(t, task, time, period) {
		Event scheduleEvent = new Event(EventClass.Timer, TimerEventType.Schedule, t.toString());
		scheduleEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		scheduleEvent.addValue(StringConstants.TASK, task.toString());
		scheduleEvent.addValue(StringConstants.START_TIME, time);
		scheduleEvent.addValue(StringConstants.PERIOD, period);
		
		EventQueue.addEvent(scheduleEvent);
	}
	
	before(Timer t, TimerTask task, long delay, long period) :
		schedulePeriodicDelay(t, task, delay, period) {
		Event scheduleEvent = new Event(EventClass.Timer, TimerEventType.Schedule, t.toString());
		scheduleEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		scheduleEvent.addValue(StringConstants.TASK, task.toString());
		scheduleEvent.addValue(StringConstants.DELAY, delay);
		scheduleEvent.addValue(StringConstants.PERIOD, period);
		
		EventQueue.addEvent(scheduleEvent);
	}
	
	before(Timer t, TimerTask task, Date time, long period) :
		scheduleAtFixedRateSetTime(t, task, time, period) {
		Event scheduleEvent = new Event(EventClass.Timer, TimerEventType.Schedule, t.toString());
		scheduleEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		scheduleEvent.addValue(StringConstants.TASK, task.toString());
		scheduleEvent.addValue(StringConstants.START_TIME, time);
		scheduleEvent.addValue(StringConstants.PERIOD, period);
		
		EventQueue.addEvent(scheduleEvent);
	}
	
	before(Timer t, TimerTask task, long delay, long period) :
		scheduleAtFixedRateDelay(t, task, delay, period) {
		Event scheduleEvent = new Event(EventClass.Timer, TimerEventType.Schedule, t.toString());
		scheduleEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		scheduleEvent.addValue(StringConstants.TASK, task.toString());
		scheduleEvent.addValue(StringConstants.DELAY, delay);
		scheduleEvent.addValue(StringConstants.PERIOD, period);
		
		EventQueue.addEvent(scheduleEvent);
	}
}
