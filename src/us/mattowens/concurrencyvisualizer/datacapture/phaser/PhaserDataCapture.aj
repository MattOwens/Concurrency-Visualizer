package us.mattowens.concurrencyvisualizer.datacapture.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public aspect PhaserDataCapture {
	
	pointcut create() :
		call(Phaser.new());
	
	pointcut createParties(int parties) :
		call(Phaser.new(int)) &&
		args(parties);
	
	pointcut createParent(Phaser parent) :
		call(Phaser.new(Phaser)) &&
		args(parent);
	
	pointcut createParentParties(Phaser parent, int parties) :
		call(Phaser.new(Phaser, int)) &&
		args(parent, parties);

	pointcut arrive(Phaser p) :
		call(int Phaser.arrive()) &&
		target(p);
	
	pointcut arriveAndAwaitAdvance(Phaser p) :
		call(int Phaser.arriveAndAwaitAdvance()) &&
		target(p);
	
	pointcut arriveAndDeregister(Phaser p) :
		call(int Phaser.arriveAndDeregister()) &&
		target(p);
	
	pointcut awaitAdvance(Phaser p, int phase) :
		call(int Phaser.awaitAdvance*(int)) &&
		target(p) &&
		args(phase);
	
	pointcut awaitAdvanceTimeout(Phaser p, int phase, long timeout, TimeUnit unit) :
		call(int Phaser.awaitAdvanceInterruptibly(int, long, TimeUnit)) &&
		target(p) &&
		args(phase, timeout, unit);
	
	pointcut bulkRegister(Phaser p, int parties) :
		call(int Phaser.bulkRegister(int)) &&
		target(p) &&
		args(parties);
	
	pointcut forceTermination(Phaser p) :
		call(void Phaser.forceTermination()) &&
		target(p);
	
	pointcut register(Phaser p) :
		call(int Phaser.register()) &&
		target(p);
	
	
	after() returning(Phaser p) : create() {
		Event newEvent = new Event(EventClass.Phaser, PhaserEventType.Create, p.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		EventQueue.addEvent(newEvent);
	}
	
	after(int parties) returning(Phaser p) : createParties(parties) {
		Event newEvent = new Event(EventClass.Phaser, PhaserEventType.Create, p.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		newEvent.addValue(StringConstants.PARTIES, parties);
		EventQueue.addEvent(newEvent);
	}
	
	after(Phaser parent) returning(Phaser p) : createParent(parent) {
		Event newEvent = new Event(EventClass.Phaser, PhaserEventType.Create, p.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		newEvent.addValue(StringConstants.PARENT, parent.toString());
		EventQueue.addEvent(newEvent);
	}
	
	after(Phaser parent, int parties) returning(Phaser p) : createParentParties(parent, parties) {
		Event newEvent = new Event(EventClass.Phaser, PhaserEventType.Create, p.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		newEvent.addValue(StringConstants.PARENT, parent.toString());
		newEvent.addValue(StringConstants.PARTIES, parties);
		EventQueue.addEvent(newEvent);
	}
	
	after(Phaser p) returning(int phase) : arrive(p) {
		Event newEvent = new Event(EventClass.Phaser, PhaserEventType.Arrive, p.toString());
		newEvent.setJoinPointName(thisJoinPointStaticPart.getSignature().getName());
		
	}
}
