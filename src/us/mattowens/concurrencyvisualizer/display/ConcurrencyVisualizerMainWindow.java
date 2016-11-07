package us.mattowens.concurrencyvisualizer.display;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import us.mattowens.concurrencyvisualizer.datacapture.EventQueue;

public class ConcurrencyVisualizerMainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EventDisplayPanel displayPanel;
	private ConcurrencyVisualizerRunMode runMode;

	
	public ConcurrencyVisualizerMainWindow(ConcurrencyVisualizerRunMode runMode) {
		this.runMode = runMode;
		displayPanel = new EventDisplayPanel(runMode);
		Container contentPane = getContentPane();

		setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		JToolBar toolBar = new JToolBar("Execution Control");
		
		if(runMode == ConcurrencyVisualizerRunMode.StepThrough) {
			JButton nextEventButton = new JButton();
			nextEventButton.setActionCommand("Show next event");
			nextEventButton.setText("Next");
			toolBar.add(nextEventButton);
			nextEventButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean eventAdded = displayPanel.addNextEvent();
					
					if(!eventAdded) {
						displayPanel.showEventInputDone();
						nextEventButton.setEnabled(false);
					}
				}
			});
		} else if(runMode == ConcurrencyVisualizerRunMode.OnDelay) {
			JLabel sliderLabel = new JLabel("Delay in ms:");
			toolBar.add(sliderLabel);
			JSlider delayTimeSlider = new JSlider(JSlider.HORIZONTAL, 0, 2000, 100);
			delayTimeSlider.setMajorTickSpacing(500);
			delayTimeSlider.setMinorTickSpacing(100);
			delayTimeSlider.setPaintTicks(true);
			delayTimeSlider.setPaintLabels(true);
			delayTimeSlider.setSnapToTicks(true);
			delayTimeSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					displayPanel.setDisplayDelay(delayTimeSlider.getValue());
				}
				
			});
			toolBar.add(delayTimeSlider);
		}
		toolBar.setVisible(true);
		contentPane.add(toolBar);
		
		JScrollPane scrollPane = new JScrollPane(displayPanel);
		displayPanel.setVisible(true);
		contentPane.add(scrollPane);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e)
		    {
		        EventQueue.stopEventOutput();
		        InputEventQueue.stopEventInput();
		        System.exit(0);
		    }
		});
	}
	
	@Override
	public String toString() {
		return "ConcurrencyVisualizerMainWindow";
	}
}
