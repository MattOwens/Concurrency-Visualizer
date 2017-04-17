package us.mattowens.concurrencyvisualizer.display;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import us.mattowens.concurrencyvisualizer.Logging;
import us.mattowens.concurrencyvisualizer.RunConfiguration;
import us.mattowens.concurrencyvisualizer.StringConstants;
import us.mattowens.concurrencyvisualizer.datacapture.DatabaseOutputAdapter;
import us.mattowens.concurrencyvisualizer.datacapture.Event;
import us.mattowens.concurrencyvisualizer.datacapture.EventClass;
import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;
import us.mattowens.concurrencyvisualizer.datacapture.FileOutputAdapter;
import us.mattowens.concurrencyvisualizer.datacapture.SocketOutputAdapter;
import us.mattowens.concurrencyvisualizer.datacapture.thread.ThreadEventType;
import us.mattowens.concurrencyvisualizer.display.inputadapters.SocketInputAdapter;

public aspect MainCapture {
	
	private ConcurrencyVisualizerMainWindow mainWindow;
	long start;
	long end;

	pointcut main() :
		execution(void main(String[])) &&
		!within(us.mattowens.concurrencyvisualizer..*) &&
		!within(cvrunner..*);
	
	before() : main() {

		try {
			RunConfiguration config = new RunConfiguration(StringConstants.CONFIG_FILE);
			
			if(config.readProperties()) {
				String outputFileName = config.get(StringConstants.OUTFILE_KEY);
				if(!outputFileName.equals("")) {
					FileOutputAdapter fileOutputAdapter = new FileOutputAdapter(outputFileName);
					EventQueue.addOutputAdapter(fileOutputAdapter);
				}
				
				String mainClass = config.get(StringConstants.MAIN_CLASS_NAME);
				if(!mainClass.equals("")) {
					DatabaseOutputAdapter dbOutputAdapter = new DatabaseOutputAdapter(mainClass);
					EventQueue.addOutputAdapter(dbOutputAdapter);
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
			File configFile = new File(StringConstants.CONFIG_FILE);
			configFile.deleteOnExit();

		} catch (IOException e) {
			Logging.exception(e);;
		} catch (SQLException e) {
			Logging.exception(e);
		}
		start = System.nanoTime();
	}
	
	after() : main() {
		end = System.nanoTime();
		System.out.println(end - start);
		EventQueue.indicateChildProgramFinished();
	}
}
