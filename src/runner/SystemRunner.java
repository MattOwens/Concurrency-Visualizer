package runner;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.aspectj.tools.ajc.Main;

import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;
import us.mattowens.concurrencyvisualizer.datacapture.FileOutputAdapter;
import us.mattowens.concurrencyvisualizer.datacapture.SocketOutputAdapter;
import us.mattowens.concurrencyvisualizer.display.ConcurrencyVisualizerMainWindow;
import us.mattowens.concurrencyvisualizer.display.ConcurrencyVisualizerRunMode;
import us.mattowens.concurrencyvisualizer.display.InputEventQueue;
import us.mattowens.concurrencyvisualizer.display.inputadapters.SocketInputAdapter;

public class SystemRunner {

	
	public static void main(String[] args) throws IOException {
		String[] ajcArgs = {
				"-sourceroots", "F:\\matthew\\Documents\\Sync\\2016 Fall\\Synchronization Methods\\Concurrency Visualizer\\src\\us",
				"-d", "aspectj_output",
				"-1.8", "-Xlint:ignore"
		};
		
		System.out.println("Running compiler");
		Main main = new Main();
		main.runMain(ajcArgs, false); //Run without System.exit()
		
		System.out.println("Compiler finished. Setting up output");
		/*FileOutputAdapter outputAdapter = new FileOutputAdapter("SystemRunnerTest.txt");
		EventQueue.addOutputAdapter(outputAdapter);
		ServerSocket ss = new ServerSocket(0);
		Socket outSocket = new Socket(ss.getInetAddress(), ss.getLocalPort());
		SocketOutputAdapter socketOutputAdapter = new SocketOutputAdapter(outSocket);
		EventQueue.addOutputAdapter(socketOutputAdapter);
		SocketInputAdapter socketInputAdapter = new SocketInputAdapter(ss.accept());
		InputEventQueue.setInputAdapter(socketInputAdapter);
		socketInputAdapter.startReading();
		ss.close();
		
		ConcurrencyVisualizerMainWindow mainWindow = new ConcurrencyVisualizerMainWindow(ConcurrencyVisualizerRunMode.Live);
		mainWindow.setVisible(true);
		

		
		EventQueue.startEventOutput();*/
		
		//System.out.println("Output setup finished. Starting program");
		//Process process = Runtime.getRuntime().exec("java -cp C:\\aspectj1.8\\lib\\aspectjrt.jar;aspectj_output.jar; DiningPhilosophers");
		
		
	}
}
