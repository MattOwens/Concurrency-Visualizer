package us.mattowens.concurrencyvisualizer.datacapture.semaphore;

import java.util.ArrayList;

public class JavaSemaphore {
	
	private int availablePermits;
	private ArrayList<Thread> waitingQueue;
	
	public JavaSemaphore(int initialPermits) {
		availablePermits = initialPermits;
		waitingQueue = new ArrayList<Thread>();
	}
	
	public void addThreadToWaitingQueue(Thread t) {
		if(waitingQueue.contains(t)) {
			throw new IllegalArgumentException();
		}
		
		waitingQueue.add(t);
	}
	
	public void removeThreadFromWaitingQueue(Thread t) {
		if(!waitingQueue.contains(t)) {
			throw new IllegalArgumentException();
		}
		
		waitingQueue.remove(t);
	}
	
	public void permitsAcquired(int numAcquired) {
		availablePermits -= numAcquired;
	}
	
	public void permitsReleased(int numReleased) {
		availablePermits += numReleased;
	}
	
	public int getAvailablePermits() {
		return availablePermits;
	}

}
