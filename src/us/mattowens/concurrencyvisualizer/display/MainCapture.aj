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
		execution(void main(String[]));
	
	before() : main() {

		try {
			/*FileOutputAdapter fileOutputAdapter = new FileOutputAdapter("UpdatedTestOutput.txt");
			EventQueue.addOutputAdapter(fileOutputAdapter);
			
			FileInputAdapter fileInputAdapter = new FileInputAdapter("UpdatedTestOutput.txt");
			InputEventQueue.setInputAdapter(fileInputAdapter);
			fileInputAdapter.startReading();
			*/
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
		
		mainWindow = new ConcurrencyVisualizerMainWindow();
		mainWindow.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e)
		    {
		        EventQueue.stopEventOutput();
		        InputEventQueue.stopEventInput();
		        System.exit(0);
		    }
		});
		mainWindow.setVisible(true);
		

		
		EventQueue.startEventOutput();
	}
}
