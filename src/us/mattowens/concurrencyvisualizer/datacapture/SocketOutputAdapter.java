package us.mattowens.concurrencyvisualizer.datacapture;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

import org.json.simple.JSONValue;

import us.mattowens.concurrencyvisualizer.Logging;

public class SocketOutputAdapter implements OutputAdapter {
	
	private Socket socket;
	private PrintWriter out;
	private int numEventsWritten;
	
	public SocketOutputAdapter(Socket s) throws IOException {
		socket = s;
		out = new PrintWriter(s.getOutputStream(), true);
		numEventsWritten = 0;
	}

	@Override
	public void sendEvent(Event eventToOutput) {
		Map<String, Object> eventMap = eventToOutput.collapseToMap();
		
		out.println(JSONValue.toJSONString(eventMap));
		numEventsWritten++;
	}

	@Override
	public void cleanup() {
		try {
			socket.close();
		} catch (IOException e) {
			Logging.error(e.toString(), e);
		}
		Logging.message("Socket adapter wrote {0} events", numEventsWritten);

	}

}
