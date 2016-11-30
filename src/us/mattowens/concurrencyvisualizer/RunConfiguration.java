package us.mattowens.concurrencyvisualizer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class RunConfiguration {
	
	private Properties properties;
	private String filename;
	
	public RunConfiguration(String filename) {
		this.filename = filename;
		properties = new Properties();
	}
	
	
	//Returns true if saving properties was successful
	public boolean save() {
		try {
			FileOutputStream out = new FileOutputStream(filename);
			properties.store(out, "Configuration");
			out.close();
			return true;
		} catch(IOException e) {
			Logging.error(e.toString(), e);
			return false;
		}
	}
	
	public boolean readProperties() {
		try {
			FileInputStream in = new FileInputStream(filename);
			properties.load(in);
			in.close();
			return true;
		} catch(IOException e) {
			Logging.error(e.toString(), e);
			return false;
		}
	}
	
	public void set(String key, String value) {
		properties.setProperty(key, value);
	}
	
	public String get(String key) {
		return properties.getProperty(key, "");
	}
	
	
}
