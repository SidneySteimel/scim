package hwr.swe.scim.log;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogDat {
	private Logger log;
	private String directory = "C:/Temp/";

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

	public void add(String message) {
		log.info(message);
	}

	public String getDirectory() {
		return this.directory;
	}

	public void setDirectory(String dir) {
		// try {
		this.directory = dir;
		// } catch (SecurityException e) {
		// e.printStackTrace();
		// } catch (IOException e){
		// e.printStackTrace();
		// }
	}

}
