package us.mattowens.concurrencyvisualizer.display;

import java.util.Comparator;

public class DisplayEventComparator implements Comparator<DisplayEvent> {

	@Override
	public int compare(DisplayEvent arg0, DisplayEvent arg1) {
		return (int) (arg0.getTimestamp() - arg1.getTimestamp());
	}

}
