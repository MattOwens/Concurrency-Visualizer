package us.mattowens.concurrencyvisualizer.display;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import us.mattowens.concurrencyvisualizer.Logging;
import us.mattowens.concurrencyvisualizer.display.inputadapters.FileInputAdapter;

public class NewRunDisplay extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final ConcurrencyVisualizerRunMode[] availableRunModes = 
		{ConcurrencyVisualizerRunMode.ReadAll, 
	     ConcurrencyVisualizerRunMode.OnDelay,
		 ConcurrencyVisualizerRunMode.StepThrough
		};
	
	private JTextField filePathTextField;
	private JComboBox<ConcurrencyVisualizerRunMode> runModeComboBox;
	
	public static void main(String[] args) {
		NewRunDisplay newRunDisplay = new NewRunDisplay();
		newRunDisplay.setDefaultCloseOperation(EXIT_ON_CLOSE);
		newRunDisplay.setVisible(true);
		
	}
	
	public NewRunDisplay() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		JLabel filePathLabel = new JLabel("Data File:");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.25;
		constraints.gridx = 0;
		constraints.gridy = 0;
		contentPane.add(filePathLabel, constraints);
		
		filePathTextField = new JTextField();
		constraints.weightx = 0.5;
		constraints.gridx = 1;
		constraints.gridy = 0;
		contentPane.add(filePathTextField, constraints);
		
		JButton browseButton = new JButton("Browse");
		constraints.weightx = 0.25;
		constraints.gridx = 2;
		constraints.gridy = 0;
		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int fcReturnValue = fileChooser.showOpenDialog(browseButton);
				
				if(fcReturnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					filePathTextField.setText(selectedFile.getAbsolutePath());
				}
			}
		});
		contentPane.add(browseButton, constraints);
		
		JLabel runModeLabel = new JLabel("Run Mode:");
		constraints.weightx = 0.25;
		constraints.gridx = 0;
		constraints.gridy = 1;
		contentPane.add(runModeLabel, constraints);
		
		runModeComboBox = new JComboBox<ConcurrencyVisualizerRunMode>(availableRunModes);
		constraints.weightx = 0.75;
		constraints.gridx = 1;
		constraints.gridy = 1;
		contentPane.add(runModeComboBox, constraints);
		
		JButton runButton = new JButton("Run");
		constraints.weightx = 0.34;
		constraints.gridx = 1;
		constraints.gridy = 2;
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					runProgram();
				} catch (Exception e1) {
					Logging.error(e1.toString(), e1);
					JOptionPane.showMessageDialog(runButton.getRootPane(), e1, "Exception", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		contentPane.add(runButton, constraints);
		
		JButton cancelButton = new JButton("Cancel");
		constraints.weightx = 0.34;
		constraints.gridx = 2;
		constraints.gridy = 2;
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		contentPane.add(cancelButton, constraints);
		pack();
	}
	
	private void runProgram() throws IOException, InterruptedException {
		FileInputAdapter fileInputAdapter = new FileInputAdapter(filePathTextField.getText());
		InputEventQueue.setInputAdapter(fileInputAdapter);
		fileInputAdapter.startReading();
		
		ConcurrencyVisualizerRunMode runMode = availableRunModes[runModeComboBox.getSelectedIndex()];
		
		if(runMode == ConcurrencyVisualizerRunMode.ReadAll || runMode == ConcurrencyVisualizerRunMode.OnDelay) {
			fileInputAdapter.waitForDataLoaded();
		}
		
		ConcurrencyVisualizerMainWindow mainWindow = 
				new ConcurrencyVisualizerMainWindow(
						availableRunModes[runModeComboBox.getSelectedIndex()]);
		mainWindow.setVisible(true);
		this.setVisible(false);
	}
}
