package us.mattowens.sampleprograms;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {

	public static void main(String[] args) {
		Lock fork1 = new ReentrantLock();
		Lock fork2 = new ReentrantLock();
		Lock fork3 = new ReentrantLock();
		Lock fork4 = new ReentrantLock();
		Lock fork5 = new ReentrantLock();
		
		Philosopher philosopherA = new Philosopher(fork1, fork5);
		Philosopher philosopherB = new Philosopher(fork2, fork1);
		Philosopher philosopherC = new Philosopher(fork3, fork2);
		Philosopher philosopherD = new Philosopher(fork4, fork3);
		Philosopher philosopherE = new Philosopher(fork5, fork4);
		
		Thread threadA = new Thread(philosopherA);
		Thread threadB = new Thread(philosopherB);
		Thread threadC = new Thread(philosopherC);
		Thread threadD = new Thread(philosopherD);
		Thread threadE = new Thread(philosopherE);
		
		threadA.start();
		threadB.start();
		threadC.start();
		threadD.start();
		threadE.start();
	}
	
	static class Philosopher implements Runnable {
		private Lock leftFork;
		private Lock rightFork;
		
		public Philosopher(Lock leftFork, Lock rightFork) {
			this.leftFork = leftFork;
			this.rightFork = rightFork;
		}
		
		public void run() {
			boolean hasLeftFork = false;
			boolean hasRightFork = false;
			
			int eatenTimes = 0;
			
			while(eatenTimes < 10) {
				try {
					hasLeftFork = leftFork.tryLock();
					hasRightFork = rightFork.tryLock();
					
					if(hasLeftFork && hasRightFork) {
						eat();
						eatenTimes++;
					}
				} finally {
					if(hasLeftFork) {
						leftFork.unlock();
					}
					if(hasRightFork) {
						rightFork.unlock();
					}
				}
			}
		}
		
		private void eat() {
			try {
				Thread.sleep(10);
			} catch(InterruptedException e) { }
		}
	}
}
