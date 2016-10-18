package us.mattowens.sampleprograms;

import java.util.ArrayList;

public class ProducerConsumerDriver {
	
	static ArrayList<Producer> producers;
	static ArrayList<Consumer> consumers;
	
	public static void main(String[] args) {
		producers = new ArrayList<Producer>();
		consumers = new ArrayList<Consumer>();
		
		Buffer buffer = new Buffer();
		
		for(int i = 1; i <= 10; i++) {
			String pName = "Producer-" + i;
			String cName = "Consumer-" + i;
			Producer p = new Producer(pName, buffer);
			Consumer c = new Consumer(cName, buffer);
			producers.add(p);
			consumers.add(c);
			
			Thread t1 = new Thread(p, pName);
			Thread t2 = new Thread(c, cName);
			t1.start();
			t2.start();
		}
		
	}

}
