package us.mattowens.concurrencyvisualizer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logging {
	
	public static final Logger LOGGER = Logger.getLogger(Logging.class.getName());
	
	static {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyyHHmm");
			FileHandler fh = new FileHandler("ConcurrencyVisualizer-" + dateFormat.format(new Date()) + ".log");
			fh.setLevel(Level.ALL);
			fh.setFormatter(new SimpleFormatter());
			LOGGER.setLevel(Level.ALL);
			LOGGER.addHandler(fh);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void message(String message, Object... params) {
		LOGGER.log(Level.FINE, message, params);
	}
	
	public static void warning(String message, Object... params) {
		LOGGER.log(Level.WARNING, message, params);
	}
	
	public static void error(String message, Object... params) {
		LOGGER.log(Level.SEVERE, message, params);
	}
}
