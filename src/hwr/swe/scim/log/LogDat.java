package hwr.swe.scim.log;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This class is dealing with the logger.
 * The logfiles are at a logFiles directory and are named with date as yyMMddkkmm. It is possible to add messages in 5 different Levels from ENUM logLevel. 
 * @author Thomas Gaertner
 */
public class LogDat {
	private Logger log;
	private String logFileDirectory = "logFiles";

	/**
	 * Constructor: build the path and create the logger.
	 * First information --> start
	 */
	public LogDat(String dir) {
		setDirectory(dir);
		log = Logger.getLogger("SCIMLogger");
		FileHandler fh;
		try {
			// set directory
			String name = new String();
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter time;
			time = DateTimeFormatter.ofPattern("yyMMddkkmm");
			name = now.format(time);
			fh = new FileHandler(logFileDirectory + name + ".log");
			// set file handler to logger
			log.addHandler(fh);
			// set format
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

			// write start
			log.info("System Start");
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * add an log 
	 * @param message the message writing in the logfile
	 * @param level defining the level
	 */
	public void add(String message, LogLevel level) {
		switch(level){
		case INFO:
			log.info(message);
			break;
		case CONFIG:
			log.config(message);
			break;
		case L1:
			log.fine(message);
			break;
		case L2:
			log.finer(message);
			break;
		case L3:
			log.finest(message);  
			break;
		default:
			break;
		}
	}

	/**
	 * get current directory
	 * @return the directory
	 */
	public String getDirectory() {
		return logFileDirectory;
	}

	/**
	 * set current directory
	 * @param dir path to the new directory
	 */
	public void setDirectory(String dir) {
		// try {
		logFileDirectory = dir;
		// } catch (SecurityException e) {
		// e.printStackTrace();
		// } catch (IOException e){
		// e.printStackTrace();
		// }
	}
	
	public void close(){
			log.info("system closed");
	}

}
