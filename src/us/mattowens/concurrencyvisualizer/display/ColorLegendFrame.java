package us.mattowens.concurrencyvisualizer.display;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ColorLegendFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ColorLegendFrame() {
		setTitle("Legend");
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		addColorLabels();
	}
	
	private void addColorLabels() {
		for(String eventClass : EventColors.getColorableEventTypes()) {
			JLabel label = new JLabel(eventClass);
			label.setForeground(EventColors.getColor(eventClass));
			add(label);
		}
		pack();
	}

}
