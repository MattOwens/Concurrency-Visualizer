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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.aspectj.tools.ajc.Main;

public class ConcurrencyVisualizerLiveMode extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField sourceRootTextField;

	public static void main(String[] args) throws IOException {
		ConcurrencyVisualizerLiveMode liveMode = new ConcurrencyVisualizerLiveMode();
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
		
		JButton runButton = new JButton("Run");
		constraints.weightx = 0.25;
		constraints.gridx = 2;
		constraints.gridy = 1;
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
		
		
		pack();
		
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
		sourceRoot = sourceRoot.replace("\\", "\\\\") +";src\\us\\mattowens\\concurrencyvisualizer";
		System.out.println(sourceRoot);
		
		String[] ajcArgs = {
				//"-aspectpath", "src\\us\\mattowens\\concurrencyvisualizer\\",
				"-sourceroots", sourceRoot,
				"-outjar", "aspectj_output.jar",
				"-1.8", "-Xlint:ignore"
		};
		
		System.out.println("Running compiler");
		Main main = new Main();
		main.runMain(ajcArgs, false); //Run without System.exit()
		System.out.println("Output setup finished. Starting program");
		Process process = Runtime.getRuntime().exec("java -cp c:\\libraries\\*;c:\\aspectj1.8\\lib\\aspectjrt.jar;"
				+ "c:\\aspectj1.8\\lib\\aspectjtools.jar;"
				+ "aspectj_output.jar; us.mattowens.sampleprograms.DiningPhilosophers");
		
		InputStream stdin = process.getErrorStream();
		InputStreamReader isr = new InputStreamReader(stdin);
		BufferedReader br = new BufferedReader(isr);

		String line = null;
		System.out.println("<OUTPUT>");

		while ( (line = br.readLine()) != null)
		     System.out.println(line);

		System.out.println("</OUTPUT>");
		try {
			int exitVal = process.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
