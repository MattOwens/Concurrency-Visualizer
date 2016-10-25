package us.mattowens.concurrencyvisualizer.display;

import java.awt.Container;

import javax.swing.*;

public class ConcurrencyVisualizerMainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EventDisplayPanel displayPanel;

	
	public ConcurrencyVisualizerMainWindow() {
		Container contentPane = getContentPane();
		displayPanel = new EventDisplayPanel();
		JScrollPane scrollPane = new JScrollPane(displayPanel);
		contentPane.add(scrollPane);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		//pack();
	}
	
	@Override
	public String toString() {
		return "ConcurrencyVisualizerMainWindow";
	}
}
