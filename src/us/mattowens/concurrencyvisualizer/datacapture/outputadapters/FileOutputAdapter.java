package us.mattowens.concurrencyvisualizer.datacapture.outputadapters;

import java.io.*;
import java.util.Map;
import org.json.simple.JSONValue;

import us.mattowens.concurrencyvisualizer.Logging;
import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class FileOutputAdapter implements OutputAdapter {
	
	private BufferedWriter outputWriter;
	
	public FileOutputAdapter(String filePath) throws IOException {
		File outputFile = new File(filePath);
		outputWriter = createBufferedWriter(outputFile);
		
	}
	

	public FileOutputAdapter(File outputFile) throws IOException {
		outputWriter = createBufferedWriter(outputFile);
	}

	@Override
	public void sendEvent(Event eventToOutput) {
		
		if(outputWriter == null) {
			String errorMessage = "FileOutputAdapter received event after output writer closed";
			Logging.error(errorMessage);
			throw new IllegalStateException(errorMessage);
		}
		
		Map<String, Object> eventMap = eventToOutput.collapseToMap();
		
		try {
			outputWriter.write(JSONValue.toJSONString(eventMap) + "\n");
		} catch(IOException e) {
			Logging.exception(e);
		}

	}
	
	@Override
	public void cleanup() {
		if(outputWriter != null) {
			try {
				outputWriter.flush();
				outputWriter.close();
			} catch (IOException e) {
				Logging.exception(e);
			} finally {
				outputWriter = null;
			}
		}
		
	}
	
	private static BufferedWriter createBufferedWriter(File outputFile) throws IOException {
		return new BufferedWriter(new FileWriter(outputFile.getAbsoluteFile()));
	}

}
