package us.mattowens.concurrencyvisualizer.display;

import javax.swing.BoxLayout;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

public class ColorLegendFrame extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ColorLegendFrame() {
		super("Legend", true, true, false, false);
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
