package us.mattowens.concurrencyvisualizer.datacapture;

import java.io.*;
import java.util.Map;
import org.json.simple.JSONValue;

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
			throw new IllegalStateException("FileOutputAdapter Error : output writer already closed");
		}
		
		Map<String, Object> eventMap = eventToOutput.collapseToMap();
		
		try {
			outputWriter.write(JSONValue.toJSONString(eventMap) + "\n");
		} catch(IOException e) {
			// TODO Decide what to do here
			e.printStackTrace();
		}

	}
	
	@Override
	public void cleanup() {
		
		if(outputWriter != null) {
			try {
				outputWriter.flush();
				outputWriter.close();
			} catch (IOException e) {
				// TODO Decide what to do here
				e.printStackTrace();
			} finally {
				outputWriter = null;
			}
		}
		
	}
	
	private static BufferedWriter createBufferedWriter(File outputFile) throws IOException {
		return new BufferedWriter(new FileWriter(outputFile.getAbsoluteFile()));
	}

}
