package us.mattowens.concurrencyvisualizer.display;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import us.mattowens.concurrencyvisualizer.RunConfiguration;
import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;
import us.mattowens.concurrencyvisualizer.datacapture.FileOutputAdapter;
import us.mattowens.concurrencyvisualizer.datacapture.SocketOutputAdapter;
import us.mattowens.concurrencyvisualizer.display.inputadapters.SocketInputAdapter;

public aspect MainCapture {
	
	private ConcurrencyVisualizerMainWindow mainWindow;

	pointcut main() :
		execution(void main(String[])) &&
		!within(us.mattowens.concurrencyvisualizer..*);
	
	before() : main() {

		try {
			RunConfiguration config = new RunConfiguration(StringConstants.CONFIG_FILE);
			
			if(config.readProperties()) {
				String outputFileName = config.get(StringConstants.OUTFILE_KEY);
				if(!outputFileName.equals("")) {
					FileOutputAdapter outputAdapter = new FileOutputAdapter(outputFileName);
					EventQueue.addOutputAdapter(outputAdapter);
				}
				
				String viewLive = config.get(StringConstants.LIVE_VIEW);
				if(!viewLive.equals("") && Boolean.parseBoolean(viewLive)) {
					ServerSocket ss = new ServerSocket(0);
					Socket outSocket = new Socket(ss.getInetAddress(), ss.getLocalPort());
					SocketOutputAdapter socketOutputAdapter = new SocketOutputAdapter(outSocket);
					EventQueue.addOutputAdapter(socketOutputAdapter);
					SocketInputAdapter socketInputAdapter = new SocketInputAdapter(ss.accept());
					InputEventQueue.setInputAdapter(socketInputAdapter);
					socketInputAdapter.startReading();
					ss.close();
					
					
					mainWindow = new ConcurrencyVisualizerMainWindow(ConcurrencyVisualizerRunMode.Live);
					mainWindow.setVisible(true);
				
				}
			}
			EventQueue.startEventOutput();

		} catch (IOException e) {
			// TODO Show user an error message about this
			e.printStackTrace();
		}

	}
}
