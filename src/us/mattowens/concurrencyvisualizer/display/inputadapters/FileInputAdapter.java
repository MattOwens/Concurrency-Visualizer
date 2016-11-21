package us.mattowens.concurrencyvisualizer.display.inputadapters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import us.mattowens.concurrencyvisualizer.Logging;
import us.mattowens.concurrencyvisualizer.display.DisplayEvent;
import us.mattowens.concurrencyvisualizer.display.InputEventQueue;

public class FileInputAdapter implements Runnable, InputAdapter {
	
	private BufferedReader fileReader;
	private Thread inputThread;
	private int numEventsRead;
	
	public FileInputAdapter(String filePath) throws IOException {
		fileReader = new BufferedReader(new FileReader(filePath));
		inputThread = new Thread(this); //Might want to fix this
		numEventsRead = 0;
	}

	@Override
	public void run() {
	
		String inputString = "";
		while(inputString != null) {
			try {
				inputString = fileReader.readLine();

				if(inputString != null) {
					numEventsRead++;
					DisplayEvent displayEvent = new DisplayEvent(inputString);
					InputEventQueue.addEvent(displayEvent);
				}
			} catch (IOException e1) {
				Logging.error(e1.toString(), e1);
			} catch (ParseException e) {
				Logging.warning(e.toString(), e);
			}
		}
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
