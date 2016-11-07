package us.mattowens.concurrencyvisualizer.datacapture;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

import org.json.simple.JSONValue;

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
		//byte[] outputMessage = JSONValue.toJSONString(eventMap).getBytes();
		
		//try {
			out.println(JSONValue.toJSONString(eventMap));
			numEventsWritten++;
		//} catch(IOException e) {
		//	e.printStackTrace();
		//}
		
		

	}

	@Override
	public void cleanup() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Socket adapter wrote " + numEventsWritten + " events");

	}

}
