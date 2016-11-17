package us.mattowens.sampleprograms;
import java.util.concurrent.Semaphore;

public class DemoSemaphoreNoDeadlock {

	public static void main(String[] args) {
		Semaphore sema1 = new Semaphore(1);
		Semaphore sema2 = new Semaphore(1);
		
		Thread a = new Thread(new Runnable() {
			public void run() {
				
				while(true) {
					try {
						sema1.acquire();
						sema2.acquire();
						
						Thread.sleep(10);
						
						sema2.release();
						sema1.release();
					} catch(InterruptedException e) { }

				}
			}
		});
		
		Thread b = new Thread(new Runnable() {
			public void run() {
				while(true) {
					try {
						sema1.acquire();
						sema2.acquire();
						
						Thread.sleep(10);
						
						sema2.release();
						sema1.release();
					} catch(InterruptedException e) { }

				}
			}
		});
		
		a.start();
		b.start();
	}


}
