package us.mattowens.sampleprograms;

public class Producer implements Runnable {

	private Buffer buffer;
	private String name;
	
	public Producer(String name, Buffer buffer) {
		this.buffer = buffer;
		this.name = name;
	}
	
	@Override
	public void run() {
		
		for(int i = 0; i < 15; i++) {
			String item = produce();
			buffer.putItem(item);
			//System.out.println("Produced " + item);
		}
	}
	
	private String produce() {
		try {
			Thread.sleep(500);
		}
		catch(InterruptedException e) { }
		return name;
	}

	
	
}
