package us.mattowens.concurrencyvisualizer.display.inputadapters;

import java.io.*;
import java.net.Socket;

import org.json.simple.parser.ParseException;

import us.mattowens.concurrencyvisualizer.Logging;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.display.InputEventQueue;
import us.mattowens.concurrencyvisualizer.display.MultipleAccessInputEventQueue;

public class SocketInputAdapter implements Runnable, InputAdapter {
	
	private Socket socket;
	private BufferedReader inputReader;
	private Thread readerThread;
	private int numEventsRead;
	
	public SocketInputAdapter(Socket s) throws IOException {
		socket = s;
		inputReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		readerThread = new Thread(this);
		numEventsRead = 0;
	}
	
	@Override
	public void run() {
		String input = "";
		while(input != null) {
			try {
				input = inputReader.readLine();
				if(input != null) {
					
					numEventsRead++;
					Event displayEvent = new Event(input, 0);
					InputEventQueue.addEvent(displayEvent);
					
					//TODO: This is incredibly slow. Add some kind of buffering
					/*MultipleAccessInputEventQueue.openForWriting();
					MultipleAccessInputEventQueue.addEvent(displayEvent);
					MultipleAccessInputEventQueue.closeWriting();*/
				}
			} catch(IOException e) {
				//Happens when stream is closed
				Logging.exception(e);
				break; //I think this should be okay
			} catch (ParseException e) {
				Logging.exception(e);
			}
		}
		Logging.message(String.format("SocketInputAdapter read %d events before exiting", numEventsRead));
		cleanup();
		
	}
	
	public void startReading() {
		readerThread.start();
		Logging.message("SocketInputAdapter started");
	}

	@Override
	public void cleanup() {
		try {
			if(inputReader != null) {
				inputReader.close();
			}
			if(socket != null) {
				socket.close();
			}
		} catch(IOException e) {
			Logging.exception(e);
		}
	}



}
