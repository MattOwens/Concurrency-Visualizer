package us.mattowens.concurrencyvisualizer.display;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Reads a file containing colors for each event type.
 * Each line of the file must be of the form:
 * EventType,red,green,blue
 */
public class EventColors {
	
	private HashMap<String, Color> eventColors;
	
	public EventColors(String colorFile) throws IOException {
		eventColors = new HashMap<String, Color>();
		BufferedReader colorReader = new BufferedReader(new FileReader(colorFile));
		String line = colorReader.readLine();
		
		while(line != null) {
			String[] color = line.split(",");
			String eventType = color[0];
			int red = Integer.parseInt(color[1]);
			int green = Integer.parseInt(color[2]);
			int blue = Integer.parseInt(color[3]);
			
			Color newColor = new Color(red, green, blue);
			eventColors.put(eventType, newColor);
			line = colorReader.readLine();
		}
		
		colorReader.close();
	}
	
	public Color getColor(String eventType) {
		if(eventColors.containsKey(eventType)) {
			return eventColors.get(eventType);
		}
		return Color.BLACK;
	}
}
