package us.mattowens.sampleprograms;

public class Consumer  implements Runnable {
	private Buffer buffer;
	private String name;
	
	public Consumer(String name, Buffer buffer) {
		this.buffer = buffer;
		this.name = name;
	}
	
	@Override
	public void run() {
		
		for(int i = 0; i < 15; i++) {
			consume(buffer.getItem());
		}
	}
	
	private void consume(String item) {
		//System.out.println(name + " consumed " + item);
		try {
			Thread.sleep(500);
		}
		catch(InterruptedException e) { }
	}
}
