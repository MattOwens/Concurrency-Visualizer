package us.mattowens.sampleprograms;

public class Buffer {
	public static final int MAX_ITEMS = 10;
	
	private String[] items;
	private int currentItem;
	
	public Buffer() {
		items = new String[MAX_ITEMS];
		currentItem = 0;
	}
	
	public synchronized String getItem() {
		while(isEmpty()) {
			try {
				//System.out.println(Thread.currentThread().getName() +
				//		" waiting to get item.");
				wait();
				//System.out.println(Thread.currentThread().getName() +
				//		" woken up.");
			}
			catch(InterruptedException e) { }
		}
		//System.out.println(Thread.currentThread().getName() + " getting item.");
		notifyAll();
		return items[currentItem--];
	}
	
	public synchronized void putItem(String item) {
		
		while(isFull()) {
			try {
			//	System.out.println(Thread.currentThread().getName() +
			//			" waiting to put item.");
				wait();
			//	System.out.println(Thread.currentThread().getName() +
			//			" woken up.");
			} catch (InterruptedException e) { }
		}
		//System.out.println(Thread.currentThread().getName() + " putting item.");
		items[++currentItem] = item;
		notifyAll();
	}
	
	public synchronized boolean isEmpty() {
		return currentItem == 0;
	}
	
	public synchronized boolean isFull() {
		return currentItem == MAX_ITEMS - 1;
	}

}
