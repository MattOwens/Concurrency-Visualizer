package us.mattowens.concurrencyvisualizer.timer;

import java.util.Timer;
import java.util.TimerTask;

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
		TimerEvent createEvent = new TimerEvent(t.toString(), TimerEventType.Create);
		
		EventQueue.addEvent(createEvent);
	}
	
	after(boolean isDaemon) returning(Timer t) : createDaemon(isDaemon) {
		TimerEvent createEvent = new TimerEvent(t.toString(), TimerEventType.Create);
		createEvent.setDaemon(isDaemon);
		createEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(createEvent);
	}
	
	after(String name) returning(Timer t) : createName(name) {
		TimerEvent createEvent = new TimerEvent(name, TimerEventType.Create);
		createEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(createEvent);
	}
	
	after(String name, boolean isDaemon) returning(Timer t) :
		createNameDaemon(name, isDaemon) {
		TimerEvent createEvent = new TimerEvent(name, TimerEventType.Create);
		createEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		createEvent.setDaemon(isDaemon);
		
		EventQueue.addEvent(createEvent);
	}
		
	after(Timer t) : cancel(t) {
		TimerEvent cancelEvent = new TimerEvent(t.toString(), TimerEventType.Cancel);
		cancelEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		
		EventQueue.addEvent(cancelEvent);
	}
	
	after(Timer t) returning(int numPurged) : purge(t) {
		TimerEvent purgeEvent = new TimerEvent(t.toString(), TimerEventType.Purge);
		purgeEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		purgeEvent.setNumPurged(numPurged);
		
		
		EventQueue.addEvent(purgeEvent);
	}
	
	before(Timer t, TimerTask task, Date time) : scheduleSetTime(t, task, time) {
		TimerEvent scheduleEvent = new TimerEvent(t.toString(), TimerEventType.Schedule);
		scheduleEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		scheduleEvent.setTaskDescription(task.toString());
		scheduleEvent.setStartTime(time);
		
		EventQueue.addEvent(scheduleEvent);
	}
	
	before(Timer t, TimerTask task, long delay) : scheduleDelay(t, task, delay) {
		TimerEvent scheduleEvent = new TimerEvent(t.toString(), TimerEventType.Schedule);
		scheduleEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		scheduleEvent.setTaskDescription(task.toString());
		scheduleEvent.setDelay(delay);
		
		EventQueue.addEvent(scheduleEvent);
	}
	
	before(Timer t, TimerTask task, Date time, long period) :
		schedulePeriodicSetTime(t, task, time, period) {
		TimerEvent scheduleEvent = new TimerEvent(t.toString(), TimerEventType.Schedule);
		scheduleEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		scheduleEvent.setTaskDescription(task.toString());
		scheduleEvent.setStartTime(time);
		scheduleEvent.setPeriod(period);
		
		EventQueue.addEvent(scheduleEvent);
	}
	
	before(Timer t, TimerTask task, long delay, long period) :
		schedulePeriodicDelay(t, task, delay, period) {
		TimerEvent scheduleEvent = new TimerEvent(t.toString(), TimerEventType.Schedule);
		scheduleEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		scheduleEvent.setTaskDescription(task.toString());
		scheduleEvent.setDelay(delay);
		scheduleEvent.setPeriod(period);
		
		EventQueue.addEvent(scheduleEvent);
	}
	
	before(Timer t, TimerTask task, Date time, long period) :
		scheduleAtFixedRateSetTime(t, task, time, period) {
		TimerEvent scheduleEvent = new TimerEvent(t.toString(), TimerEventType.Schedule);
		scheduleEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		scheduleEvent.setTaskDescription(task.toString());
		scheduleEvent.setStartTime(time);
		scheduleEvent.setPeriod(period);
		
		EventQueue.addEvent(scheduleEvent);
	}
	
	before(Timer t, TimerTask task, long delay, long period) :
		scheduleAtFixedRateDelay(t, task, delay, period) {
		TimerEvent scheduleEvent = new TimerEvent(t.toString(), TimerEventType.Schedule);
		scheduleEvent.setJoinPointName(thisJoinPoint.getSignature().getName());
		scheduleEvent.setTaskDescription(task.toString());
		scheduleEvent.setDelay(delay);
		scheduleEvent.setPeriod(period);
		
		EventQueue.addEvent(scheduleEvent);
	}
}
