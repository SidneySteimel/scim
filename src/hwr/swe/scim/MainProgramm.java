package hwr.swe.scim;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.List;

import org.apache.commons.mail.EmailException;

import hwr.swe.scim.log.LogDat;
import hwr.swe.scim.log.LogLevel;

public class MainProgramm {

	private final static String OLD_FILES_DIRECTORY = "ics";
	private final static String NEW_FILES_DIRECTORY = "";
	private final static String LOG_FILES = "logFiles/";

	private static FileComparator comparator = new FileComparator();
	private static Storage storage = new Storage();

	private static LogDat log = new LogDat(LOG_FILES);

	/**
	 * This is our main method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// get all courses in a list
		List<String> courses = storage.getCourses();
		log.add("Success: Get courses.", LogLevel.INFO);

		for (String course : courses) {
			// first field = course name, second field = semester[, third field
			// = course number]
			String[] courseArray = course.split("_");
			String oldFilePath = OLD_FILES_DIRECTORY + "/" + course + ".ics";
			String newFilePath = NEW_FILES_DIRECTORY + "stundenplan_" + course + ".ics";

			try {
				List<Lecture> changes = comparator.getChanges(oldFilePath, newFilePath);
				log.add("Success: Compared files for course " + course, LogLevel.INFO);

				if (changes == null) {
					// there are no current events
					storage.deleteCourse(course);
					log.add("Course " + course + " was deleted", LogLevel.INFO);
				} else if (changes.isEmpty()) {
					// there are no changes
					log.add("No changes found for course " + course, LogLevel.INFO);
				} else if (!changes.isEmpty()) {
					sendMail(course, changes);
					log.add("Success: Mails for course " + course + " sent", LogLevel.INFO);
				}
				replaceOldFile(new File(newFilePath), new File(oldFilePath));
				log.add("Success: Procedure terminated for course " + course, LogLevel.INFO);
			} catch (Exception e) {
				log.add("Exception " + e.getMessage(), LogLevel.L1);
			}
		}
		deleteOldLogFiles();

		log.add("Success: Everything terminated for today", LogLevel.INFO);
	}

	/**
	 * Deletes old log files that are older than two weeks.
	 */
	private static void deleteOldLogFiles() {
		File dir = new File(log.getDirectory());
		File[] allFiles = dir.listFiles();

		for (File file : allFiles) {
			long modified = file.lastModified();
			long now = Instant.now().toEpochMilli();
			long twoWeeksInMillis = 1209600000;
			long twoWeeksAgo = now - twoWeeksInMillis;
			if (modified < twoWeeksAgo) {
				file.delete();
			}
		}
	}

	/**
	 * Sends a mail with changes in the schedule.
	 * 
	 * @param pCourse
	 *            course of which we have changes
	 * @param pChanges
	 *            list of changes in the course's schedule
	 * @throws IOException
	 */
	private static void sendMail(String pCourse, List<Lecture> pChanges) throws IOException {
		List<String> receivers = storage.getParticipantsOfCourse(pCourse);
		MessageManager mails = new MailManager();
		try {
			mails.giveMessage(pChanges, receivers);
		} catch (EmailException e) {
			log.add("Exception " + e.getMessage(), LogLevel.L1);
		}
	}

	/**
	 * Replaces the old file with the new file.
	 * 
	 * @param pNewFile
	 *            new File object
	 * @param pOldFile
	 *            File that should be replaced
	 * @throws IOException
	 */
	private static void replaceOldFile(File pNewFile, File pOldFile) throws IOException {
		Path from = pNewFile.toPath();
		Path to = pOldFile.toPath();
		Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
	}

}
