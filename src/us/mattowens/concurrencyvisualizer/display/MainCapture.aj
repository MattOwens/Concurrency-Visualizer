package us.mattowens.concurrencyvisualizer.display;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import us.mattowens.concurrencyvisualizer.datacapture.*;
import us.mattowens.concurrencyvisualizer.display.inputadapters.FileInputAdapter;

public aspect MainCapture {
	
	private ConcurrencyVisualizerMainWindow mainWindow;

	pointcut main() :
		execution(void main(String[]));
	
	before() : main() {

		try {
			FileOutputAdapter fileOutputAdapter = new FileOutputAdapter("UpdatedTestOutput.txt");
			EventQueue.addOutputAdapter(fileOutputAdapter);
			
			FileInputAdapter fileInputAdapter = new FileInputAdapter("UpdatedTestOutput.txt");
			InputEventQueue.setInputAdapter(fileInputAdapter);
			fileInputAdapter.startReading();
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
