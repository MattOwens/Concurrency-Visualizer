package us.mattowens.concurrencyvisualizer.datacapture;

public interface OutputAdapter {
	
	/***
	 * Passes the adapter an Event object to output
	 * @param eventToOutput the Event the adapter must output
	 */
	void sendEvent(Event eventToOutput);
	
	/***
	 * Instructs the adapter to perform any necessary cleanup operations to safely close
	 */
	void cleanup();
}
