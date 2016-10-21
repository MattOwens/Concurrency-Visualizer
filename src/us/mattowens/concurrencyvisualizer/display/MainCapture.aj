package us.mattowens.concurrencyvisualizer.display;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import us.mattowens.concurrencyvisualizer.datacapture.*;

public aspect MainCapture {
	
	private ConcurrencyVisualizerMainWindow mainWindow;

	pointcut main() :
		execution(void main(String[]));
	
	before() : main() {
		mainWindow = new ConcurrencyVisualizerMainWindow();
		//mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e)
		    {
		        EventQueue.stopEventOutput();
		        System.exit(0);
		    }
		});
		mainWindow.setVisible(true);
		
		try {
			FileOutputAdapter fileOutputAdapter = new FileOutputAdapter("UpdatedTestOutput.txt");
			EventQueue.addOutputAdapter(fileOutputAdapter);
		} catch (IOException e) {
			// TODO Show user an error message about this
			e.printStackTrace();
		}
		
		EventQueue.startEventOutput();
	}
}
