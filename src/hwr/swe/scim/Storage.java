package hwr.swe.scim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the mailing lists.
 * 
 * @author Elen Niedermeyer
 *
 */
public class Storage {

	/**
	 * in this directory you can find the text files with lists of mail addresses
	 * for each course
	 */
	private final static String MAILING_LISTS_DIRECTORY = "mailingLists";

	/**
	 * Returns the names of all files that are in the mailing list directory.
	 * The file names should be the course names.
	 * 
	 * @return a list that contains the file names/ course names as strings
	 */
	public List<String> getCourses() {
		File dir = new File(MAILING_LISTS_DIRECTORY);
		File[] allFiles = dir.listFiles();

		List<String> courses = new ArrayList<String>();

		for (File file : allFiles) {
			if (file.isFile()) {
				String fileName = file.getName();
				int lastDot = fileName.lastIndexOf(".");
				String course = fileName.substring(0, lastDot);
				courses.add(course);
			}
		}
		return courses;
	}

	/**
	 * Gets all email addresses that are in the mailing list of the given
	 * course.
	 * 
	 * @param pCourse
	 *            a string that is a course, it should be also the name of a
	 *            file in the mailing list directory
	 * @return a list that contains all email addresses in the file as strings
	 * @throws IOException
	 *             if the file isn't available or couldn't be read
	 */
	public List<String> getParticipantsOfCourse(String pCourse) throws IOException {
		List<String> emails = new ArrayList<String>();

		File mailingList = new File(MAILING_LISTS_DIRECTORY + "/" + pCourse + ".txt");
		if (mailingList.isFile()) {
			BufferedReader reader = new BufferedReader(new FileReader(mailingList));
			String line = reader.readLine();
			while (line != null) {
				emails.add(line);
				line = reader.readLine();
			}
			reader.close();
		}
		return emails;
	}

	/**
	 * Mailing list should be deleted if the course is in the past. This method
	 * deletes the mailing list of the given course
	 * 
	 * @param pCourse
	 *            a string that is a course, it should be also the name of a
	 *            file in the mailing list directory
	 */
	public void deleteCourse(String pCourse) {
		File file = new File(MAILING_LISTS_DIRECTORY + "/" + pCourse + ".txt");
		if (file.isFile()) {
			file.delete();
		}
	}
}
