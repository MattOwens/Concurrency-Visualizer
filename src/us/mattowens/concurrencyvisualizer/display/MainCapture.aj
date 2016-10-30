package us.mattowens.concurrencyvisualizer.display;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import us.mattowens.concurrencyvisualizer.datacapture.*;
import us.mattowens.concurrencyvisualizer.display.inputadapters.*;

public aspect MainCapture {
	
	private ConcurrencyVisualizerMainWindow mainWindow;

	pointcut main() :
		execution(void main(String[])) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	before() : main() {

		try {
			ServerSocket ss = new ServerSocket(0);
			Socket outSocket = new Socket(ss.getInetAddress(), ss.getLocalPort());
			SocketOutputAdapter socketOutputAdapter = new SocketOutputAdapter(outSocket);
			EventQueue.addOutputAdapter(socketOutputAdapter);
			SocketInputAdapter socketInputAdapter = new SocketInputAdapter(ss.accept());
			InputEventQueue.setInputAdapter(socketInputAdapter);
			socketInputAdapter.startReading();
			ss.close();
		} catch (IOException e) {
			// TODO Show user an error message about this
			e.printStackTrace();
		}
		
		mainWindow = new ConcurrencyVisualizerMainWindow(ConcurrencyVisualizerRunMode.Live);
		mainWindow.setVisible(true);
		

		
		EventQueue.startEventOutput();
	}
}
