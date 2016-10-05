package us.mattowens.concurrencyvisualizer.datacapture;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.json.simple.JSONValue;

public class EventQueue {
	
	private static ConcurrentLinkedQueue<JSONSerializable> eventQueue;
	private static File outputFile;
	private static BufferedWriter writer;
	
	static {
		eventQueue = new ConcurrentLinkedQueue<JSONSerializable>();
		outputFile = new File("test_output.txt");
		try {
			writer = new BufferedWriter(new FileWriter(outputFile.getAbsoluteFile()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Thread outputThread = new Thread(new Runnable() {
			
			public void run() {
				int timesWithoutEvents = 0;
				while(true) {
										
					if(!eventQueue.isEmpty()) {
						timesWithoutEvents = 0;

						while(!eventQueue.isEmpty()) {
							JSONSerializable event = eventQueue.remove();
							try {
								//writer.write(event.toString() + "\n");
								Map<String, Object> eventMap = event.collapseToMap();
								writer.write(JSONValue.toJSONString(eventMap) + "\n");
							}
							catch(IOException e) { }
						}
						
						try {
							writer.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else {
						timesWithoutEvents++;
						if(timesWithoutEvents == 10) {
							System.out.println("No more events - returning");
							try {
								writer.flush();
								writer.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return;
						}
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		});
		
		outputThread.start();
		
		
	}
	
	public static void addEvent(Event newEvent) {
		eventQueue.add(newEvent);
	}
	


}
