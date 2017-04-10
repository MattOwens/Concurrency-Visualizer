package cvrunner;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.aspectj.tools.ajc.Main;

import us.mattowens.concurrencyvisualizer.RunConfiguration;
import us.mattowens.concurrencyvisualizer.StringConstants;

public class ConcurrencyVisualizerLiveMode extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private JTextField sourceRootTextField;
	private JTextField mainClassTextField;
	private JTextField outputFilePathTextField;
	private JCheckBox outputFileCheckBox;
	private JCheckBox outputDatabaseCheckBox;
	private JCheckBox liveViewCheckBox;

	public static void main(String[] args) throws IOException {
		System.out.println(new File(".").getCanonicalPath());
		ConcurrencyVisualizerLiveMode liveMode = new ConcurrencyVisualizerLiveMode();
		liveMode.setDefaultCloseOperation(EXIT_ON_CLOSE);
		liveMode.setVisible(true);
	}
	
	public ConcurrencyVisualizerLiveMode() {
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		
		JLabel sourceRootLabel = new JLabel("Source Tree Root:");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.25;
		constraints.gridx = 0;
		constraints.gridy = 0;
		contentPane.add(sourceRootLabel, constraints);
		
		sourceRootTextField = new JTextField();
		constraints.weightx = 0.5;
		constraints.gridx = 1;
		constraints.gridy = 0;
		contentPane.add(sourceRootTextField,  constraints);
		
		JButton browseButton = new JButton("Browse");
		constraints.weightx = 0.25;
		constraints.gridx = 2;
		constraints.gridy = 0;
		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				browseButtonClicked();
			}
		});
		contentPane.add(browseButton, constraints);
		
		JLabel mainClassLabel = new JLabel("Main Class (With full package name):");
		constraints.weightx = 0.25;
		constraints.gridx = 0;
		constraints.gridy = 1;
		contentPane.add(mainClassLabel, constraints);
		
		mainClassTextField = new JTextField();
		constraints.weightx = 0.75;
		constraints.gridx = 1;
		constraints.gridy = 1;
		contentPane.add(mainClassTextField, constraints);
		
		outputFileCheckBox = new JCheckBox("Save to file:");
		constraints.weightx = 0.25;
		constraints.gridx = 0;
		constraints.gridy = 2;
		contentPane.add(outputFileCheckBox, constraints);
		
		outputFilePathTextField = new JTextField();
		constraints.weightx = 0.75;
		constraints.gridx = 1;
		constraints.gridy = 2;
		contentPane.add(outputFilePathTextField, constraints);
		
		outputDatabaseCheckBox = new JCheckBox("Save to database");
		constraints.weightx = 0.25;
		constraints.gridx = 0;
		constraints.gridy = 3;
		contentPane.add(outputDatabaseCheckBox, constraints);
		
		liveViewCheckBox = new JCheckBox("View execution live");
		constraints.weightx = 0.25;
		constraints.gridx = 0;
		constraints.gridy = 4;
		contentPane.add(liveViewCheckBox, constraints);
		
		JButton runButton = new JButton("Run");
		constraints.weightx = 0.25;
		constraints.gridx = 2;
		constraints.gridy = 4;
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					run();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(runButton,  constraints);
		
		
		setSize(600,200);
		
	}
	
	private void browseButtonClicked() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int fcReturnValue = fileChooser.showOpenDialog(sourceRootTextField);
		
		if(fcReturnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			sourceRootTextField.setText(selectedFile.getAbsolutePath());
		}
	}
	
	private void run() throws IOException {
		String sourceRoot = sourceRootTextField.getText();
		String mainClass = mainClassTextField.getText();
		String outputFile = outputFilePathTextField.getText();
		sourceRoot = sourceRoot.replace("\\", "\\\\");
		
		String[] ajcArgs = {
				"-aspectpath", "lib\\concurrency_visualizer.jar",
				"-sourceroots", sourceRoot,
				"-outjar", "aspectj_output.jar",
				"-1.8", "-Xlint:ignore"
		};
		
		System.out.println("Running compiler");
		Main main = new Main();
		main.runMain(ajcArgs, false); //Run without System.exit()
		System.out.println("Output setup finished. Starting program");
		
		RunConfiguration config = new RunConfiguration(StringConstants.CONFIG_FILE);
		
		if(outputFileCheckBox.isSelected() && !outputFile.equals("")) {
			config.set(StringConstants.OUTFILE_KEY, outputFile);
		}
		
		if(outputDatabaseCheckBox.isSelected()) {
			config.set(StringConstants.MAIN_CLASS_NAME, mainClass);
		}
		
		if(liveViewCheckBox.isSelected()) {
			config.set(StringConstants.LIVE_VIEW, "true");
		}
		config.save();
		
		Process process = Runtime.getRuntime().exec("java -cp lib\\*;lib\\aspectj1.8\\lib\\*;"
				+ "aspectj_output.jar; " + mainClass);
		
		InputStream stdin = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(stdin);
		BufferedReader br = new BufferedReader(isr);

		String line = null;

		while ( (line = br.readLine()) != null)
		     System.out.println(line);

		try {
			int exitVal = process.waitFor();
			System.out.println("Process finished");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
