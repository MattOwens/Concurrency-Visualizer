package us.mattowens.concurrencyvisualizer.display.inputadapters;

import java.io.*;
import java.net.Socket;

import org.json.simple.parser.ParseException;

import us.mattowens.concurrencyvisualizer.Logging;
import us.mattowens.concurrencyvisualizer.display.DisplayEvent;
import us.mattowens.concurrencyvisualizer.display.InputEventQueue;

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
					DisplayEvent displayEvent = new DisplayEvent(input);
					InputEventQueue.addEvent(displayEvent);
				}
			} catch(IOException e) {
				//Happens when stream is closed
				Logging.warning(e.toString(), e);
				break; //I think this should be okay
			} catch (ParseException e) {
				Logging.warning(e.toString(), e);
			}
		}
		Logging.message(String.format("SocketInputAdapter read %d events before exiting", numEventsRead));
		cleanup();
		
	}
	
	public void startReading() {
		Logging.message("SocketInputAdapter started");
		readerThread.start();
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
			Logging.error(e.toString(), e);
		}
	}



}
