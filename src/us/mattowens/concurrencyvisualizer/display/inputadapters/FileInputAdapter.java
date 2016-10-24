package us.mattowens.concurrencyvisualizer.display.inputadapters;

import java.io.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class FileInputAdapter implements Runnable, InputAdapter {
	
	private BufferedReader fileReader;
	private Thread inputThread;
	private JSONParser jsonParser;
	
	public FileInputAdapter(String filePath) throws IOException {
		fileReader = new BufferedReader(new FileReader(filePath));
		inputThread = new Thread(this); //Might want to fix this
		jsonParser = new JSONParser();
	}

	@Override
	public void run() {
	
		while(true) {
			String inputString;
			try {
				inputString = fileReader.readLine();
				if(inputString == null) {
					if(Thread.interrupted()) {
						try {
							fileReader.close();
							fileReader = null;
							System.out.println("Input Reader Thread Ending");
							return;
						} catch (IOException e1) {
							// TODO If this fails I don't know what to do
							e1.printStackTrace();
							return;
						}
					}
					Thread.sleep(1000);
				}
				else {
					//inputString = inputString.substring(0,  inputString.length()-1);
					try {
						JSONObject parsed = (JSONObject) jsonParser.parse(inputString);
						System.out.println(parsed.get("EventClass"));
					}  catch (org.json.simple.parser.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			} catch (IOException e1) {
				// TODO Do something about this
				e1.printStackTrace();
			}
			catch(InterruptedException e) {
				try {
					fileReader.close();

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				fileReader = null;
				System.out.println("Input Reader Thread Ending");
				return;
			}
			

		}
		
	}

	@Override
	public void cleanup() {
		inputThread.interrupt();
	}
	
	public void startReading() {
		inputThread.start();
	}
	
}
