package us.mattowens.concurrencyvisualizer.datacapture.outputadapters;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONValue;

import us.mattowens.concurrencyvisualizer.Logging;
import us.mattowens.concurrencyvisualizer.datacapture.Event;

public class DatabaseOutputAdapter implements OutputAdapter {
	public static final String DB_LOCATION = "run_data\\concurrency_visualizer.db";
	public static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_LOCATION;
	
	private Connection conn;
	private int runId;
	
	public DatabaseOutputAdapter(String mainClass) throws SQLException {
		if(!dbExists()) {
			conn = createDB();
		} else {
			conn = connectToDB();
		}
		
		String newRun = "INSERT INTO run(main_class, run_date) VALUES(?,?)";
		PreparedStatement stmt = conn.prepareStatement(newRun);
		stmt.setString(1, mainClass);
		stmt.setString(2,  "date()");
		stmt.executeUpdate();
		ResultSet runIdResult = stmt.getGeneratedKeys();
		stmt.close();
		runId = runIdResult.getInt(1);
		runIdResult.close();
	}

	@Override
	public void sendEvent(Event eventToOutput) {
		String insertEvent = "INSERT INTO event(run_id, object_type, timestamp, data) VALUES(?, ?, ?, ?)";
		try {
			PreparedStatement stmt = conn.prepareStatement(insertEvent);
			stmt.setInt(1, runId);
			stmt.setString(2, eventToOutput.getEventClassString());
			stmt.setLong(3, eventToOutput.getTimestamp());
			stmt.setString(4, JSONValue.toJSONString(eventToOutput.collapseToMap()));
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			Logging.exception(e);
		}

	}

	@Override
	public void cleanup() {
		try {
			conn.close();
		} catch (SQLException e) {
			Logging.exception(e);
		}

	}
	
	private static boolean dbExists() {
		File dbFile = new File(DB_LOCATION);
		return dbFile.exists();
	}
	
	private static Connection connectToDB() {
		try{
			Connection conn = DriverManager.getConnection(CONNECTION_STRING);
			if(conn != null) {
				Logging.message("Database successfully connected");
				return conn;
			}
		} catch (SQLException e) {
			Logging.error("Could not connect to database", e);
		}
		return null;
	}
	
	private static Connection createDB() {
		try{
			File runDataDirectory = new File("run_data");
			runDataDirectory.mkdir();
			Connection conn = DriverManager.getConnection(CONNECTION_STRING);
			if(conn != null) {
				createTables(conn);
				Logging.message("Database created");
				return conn;
			}
		} catch (SQLException e) {
			Logging.exception(e);
		}
		return null;
	}
	
	private static void createTables(Connection conn) {
		String runTable = "CREATE TABLE IF NOT EXISTS run (\n" +
				"id integer PRIMARY KEY AUTOINCREMENT,\n" +
				"main_class text NOT NULL,\n" +
				"run_date text NOT NULL\n" +
				");";
		String eventTable = "CREATE TABLE IF NOT EXISTS event (\n" +
				"run_id integer NOT NULL,\n" +
				"object_type text NOT NULL,\n" +
				"timestamp integer NOT NULL,\n" +
				"data text NOT NULL,\n" +
				"FOREIGN KEY(run_id) REFERENCES run(id)" +
				");";
		
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(runTable);
			stmt.execute(eventTable);
		} catch(SQLException e) {
			Logging.exception(e);
		}
	}
	
	
}
