package us.mattowens.sampleprograms;

public class Consumer  implements Runnable {
	private Buffer buffer;
	
	public Consumer(String name, Buffer buffer) {
		this.buffer = buffer;
	}
	
	@Override
	public void run() {
		
		for(int i = 0; i < 15; i++) {
			consume(buffer.getItem());
		}
	}
	
	private void consume(String item) {
		try {
			Thread.sleep(500);
		}
		catch(InterruptedException e) { }
	}
}
