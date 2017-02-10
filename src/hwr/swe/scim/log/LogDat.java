package hwr.swe.scim.log;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This class is dealing with the logger.
 * The logfiles are at C:/Temp/ and are named with date as yyMMddkkmm. It is possible to add messages in 5 different Levels from ENUM logLevel. 
 * @author Thomas
 */
public class LogDat {
	private Logger log;
	private String directory = "C:/Temp/";

	/**
	 * Constructor: build the path and create the logger.
	 * First information --> start
	 */
	public LogDat() {
		log = Logger.getLogger("SCIMLogger");
		FileHandler fh;
		try {
			// set directory
			String name = new String();
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter time;
			time = DateTimeFormatter.ofPattern("yyMMddkkmm");
			name = now.format(time);
			fh = new FileHandler(directory + name + ".log");
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
	public void add(String message, logLevel level) {
		switch(level){
		case ERR:
			log.info(message);
			break;
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
	 * 
	 * @param message Expect an
	 * @param e
	 * @param level
	 */
	public void add(String message, Exception e, logLevel level){
		switch (level){
		case ERR:
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			log.info("EXCEPTION: " + message +"\n"+ e.getMessage()+ "\n print Stacktrace: "+ sw.toString());
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
		return this.directory;
	}

	/**
	 * set current directory
	 * @param dir path to the new directory
	 */
	public void setDirectory(String dir) {
		// try {
		this.directory = dir;
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
