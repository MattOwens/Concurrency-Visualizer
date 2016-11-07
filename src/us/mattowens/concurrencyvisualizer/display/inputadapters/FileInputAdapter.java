package us.mattowens.concurrencyvisualizer.display.inputadapters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import us.mattowens.concurrencyvisualizer.display.DisplayEvent;
import us.mattowens.concurrencyvisualizer.display.InputEventQueue;

public class FileInputAdapter implements Runnable, InputAdapter {
	
	private BufferedReader fileReader;
	private Thread inputThread;
	
	public FileInputAdapter(String filePath) throws IOException {
		fileReader = new BufferedReader(new FileReader(filePath));
		inputThread = new Thread(this); //Might want to fix this
	}

	@Override
	public void run() {
	
		String inputString = "";
		while(inputString != null) {
			try {
				inputString = fileReader.readLine();

				if(inputString != null) {
					DisplayEvent displayEvent = new DisplayEvent(inputString);
					InputEventQueue.addEvent(displayEvent);
				}
			} catch (IOException e1) {
				// TODO Do something about this
				e1.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("File Adapter Exiting");
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
			e.printStackTrace();
		}
	}
	
	public void startReading() {
		inputThread.start();
	}
	
	public void waitForDataLoaded() throws InterruptedException {
		inputThread.join();
	}
	
}
