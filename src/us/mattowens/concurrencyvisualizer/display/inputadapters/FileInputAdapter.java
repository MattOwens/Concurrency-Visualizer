package us.mattowens.concurrencyvisualizer.display.inputadapters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import us.mattowens.concurrencyvisualizer.Logging;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.display.DisplayEvent;
import us.mattowens.concurrencyvisualizer.display.InputEventQueue;
import us.mattowens.concurrencyvisualizer.display.MultipleAccessInputEventQueue;

public class FileInputAdapter implements Runnable, InputAdapter {
	
	private BufferedReader fileReader;
	private Thread inputThread;
	private int numEventsRead;
	private ArrayList<Event> eventsRead;
	
	public FileInputAdapter(String filePath) throws IOException {
		fileReader = new BufferedReader(new FileReader(filePath));
		inputThread = new Thread(this); //Might want to fix this
		numEventsRead = 0;
		eventsRead = new ArrayList<Event>();
	}

	@Override
	public void run() {
	
		String inputString = "";
		while(inputString != null) {
			try {
				inputString = fileReader.readLine();

				if(inputString != null) {
					numEventsRead++;
					Event displayEvent = new Event(inputString, 0);
					InputEventQueue.addEvent(displayEvent);
					//eventsRead.add(displayEvent);
				}
			} catch (IOException e1) {
				Logging.error(e1.toString(), e1);
			} catch (ParseException e) {
				Logging.warning(e.toString(), e);
			}
		}
		
		/*//All events read, make them available to input queue
		MultipleAccessInputEventQueue.openForWriting();
		MultipleAccessInputEventQueue.addManyEvents(eventsRead);
		MultipleAccessInputEventQueue.closeWriting();*/
		Logging.message("FileInputAdapter read {0} events before exiting", numEventsRead);
		cleanup();
		
		
	}

	@Override
	public void cleanup() {
		try {
			if(fileReader != null) {
				fileReader.close();
				fileReader = null;
			}
		} catch(IOException e) {
			Logging.error(e.toString(), e);
		}
	}
	
	public void startReading() {
		Logging.message("FileInputAdapter started");
		inputThread.start();
	}
	
	public void waitForDataLoaded() throws InterruptedException {
		inputThread.join();
	}
	
}
