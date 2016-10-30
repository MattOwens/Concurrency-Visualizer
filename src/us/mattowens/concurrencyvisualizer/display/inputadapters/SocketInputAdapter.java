package us.mattowens.concurrencyvisualizer.display.inputadapters;

import java.io.*;
import java.net.Socket;

import org.json.simple.parser.ParseException;

import us.mattowens.concurrencyvisualizer.display.DisplayEvent;
import us.mattowens.concurrencyvisualizer.display.InputEventQueue;

public class SocketInputAdapter implements Runnable, InputAdapter {
	
	private Socket socket;
	private BufferedReader inputReader;
	private Thread readerThread;
	
	public SocketInputAdapter(Socket s) throws IOException {
		socket = s;
		inputReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		readerThread = new Thread(this);
	}
	
	@Override
	public void run() {
		String input = "";
		while(input != null) {
			try {
				input = inputReader.readLine();
				if(input != null) {
					DisplayEvent displayEvent = new DisplayEvent(input);
					InputEventQueue.addEvent(displayEvent);
				}
			} catch(IOException e) {
				//Happens when stream is closed
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Input Socket Reader thread exiting");
		cleanup();
		
	}
	
	public void startReading() {
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
			//TODO Decide what to do on close failures
		}
	}



}
