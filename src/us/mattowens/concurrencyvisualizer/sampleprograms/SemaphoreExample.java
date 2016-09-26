package us.mattowens.concurrencyvisualizer.sampleprograms;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class SemaphoreExample {
	static Semaphore s;
	
	public static void main(String[] args) {
		s = new Semaphore(5);
		
		for(int i = 1; i <= 10; i++) {
			
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					Random r = new Random();
					
					for(int i = 0; i < 10; i++) {
						
						if(i % 2 == 0) {
							try {
								s.acquire();
								//Thread.sleep(r.nextInt(500));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							s.release();
						}
						if(i % 3 == 0) {
							int numPermits = r.nextInt(5);
							try {
								s.acquire(numPermits);
								//Thread.sleep(r.nextInt(500));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							s.release(numPermits);
						}
						if(i % 4 == 0) {
							boolean hasLock = s.tryAcquire();
							if(hasLock) {
								try {
									Thread.sleep(r.nextInt(500));
									s.release();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						System.out.println(Thread.currentThread().getName() + " finishing iteration " + i + " of the loop");
					}
					return;
				}
				
			});
			t.setName("ExampleThread." + i);
			t.start();
		}
	}

}
