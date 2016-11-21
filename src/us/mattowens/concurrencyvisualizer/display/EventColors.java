package us.mattowens.concurrencyvisualizer.display;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import us.mattowens.concurrencyvisualizer.Logging;

/**
 * Reads a file containing colors for each event type.
 * Each line of the file must be of the form:
 * EventType,red,green,blue
 */
public class EventColors {
	
	private static EventColors singletonEventColors;
	
	static {
		try {
			singletonEventColors = new EventColors("EventColors.txt");
		} catch (IOException e) {
			Logging.error(e.toString(), e);
		}
	}
	
	
	public static Color getColor(String eventType) {
		if(singletonEventColors.eventColors.containsKey(eventType)) {
			return singletonEventColors.eventColors.get(eventType);
		}
		return Color.BLACK;
	}
	
	public static Set<String> getColorableEventTypes() {
		return singletonEventColors.eventColors.keySet();
	}
	
	private HashMap<String, Color> eventColors;

	private EventColors(String colorFile) throws IOException {
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
	

}
