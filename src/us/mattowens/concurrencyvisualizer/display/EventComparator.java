package us.mattowens.concurrencyvisualizer.display;

import java.util.Comparator;

import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class EventComparator implements Comparator<Event> {

	@Override
	public int compare(Event arg0, Event arg1) {
		return (int) (arg0.getTimestamp() - arg1.getTimestamp());
	}

}
