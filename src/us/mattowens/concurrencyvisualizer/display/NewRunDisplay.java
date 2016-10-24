package us.mattowens.concurrencyvisualizer.display;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.*;

import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;
import us.mattowens.concurrencyvisualizer.datacapture.FileOutputAdapter;

public class NewRunDisplay extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ConcurrencyVisualizerMainWindow mainWindow;
	
	public static void main(String[] args) {
		NewRunDisplay newRunDisplay = new NewRunDisplay();
		newRunDisplay.setVisible(true);
		
	}
	
	public NewRunDisplay() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
		JCheckBox fileOutputCheckBox = new JCheckBox();
		fileOutputCheckBox.setText("Enable file output");
		contentPane.add(fileOutputCheckBox);
		
		JTextField filePathTextField = new JTextField(50);
		contentPane.add(filePathTextField);
		
		JButton browseButton = new JButton("Browse");
		contentPane.add(browseButton);
		
		JButton runButton = new JButton("Run");
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					runProgram();
				} catch (Exception e1) {
					// TODO Show user error message
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(runButton);
		
		JButton cancelButton = new JButton("Cancel");
		contentPane.add(cancelButton);
		
		pack();
		
	}
	
	private void runProgram() throws Exception {
		
		mainWindow = new ConcurrencyVisualizerMainWindow();
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
		
		Process compilation = Runtime.getRuntime().exec("C:\\aspectj1.8\\bin\\ajc.bat -inpath" +
				"F:\\matthew\\Documents\\Sync\\2016 Fall\\Synchronization Methods\\Concurrency Visualizer\\src\\us\\mattowens\\sampleprograms\\" +
				"-outpath F:\\matthew\\Documents\\Sync\\2016 Fall\\Synchronization Methods\\Concurrency Visualizer\\src\\us\\mattowens\\datacapture\\");
		
		printLines("stdout:", compilation.getInputStream());
		printLines("stderr:", compilation.getErrorStream());
		compilation.waitFor();
		Process run = Runtime.getRuntime().exec("java ProducerConsumerDriver");
	}
	
	  private static void printLines(String name, InputStream ins) throws Exception {
		    String line = null;
		    BufferedReader in = new BufferedReader(
		        new InputStreamReader(ins));
		    while ((line = in.readLine()) != null) {
		        System.out.println(name + " " + line);
		    }
		  }
	
	

}
