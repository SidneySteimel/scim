package hwr.swe.scim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Storage {

	private final static String mailingListsDirectory = "mailingLists";
	private final static String icsDiretory = "ics";

	public List<String> getCourses() {
		File dir = new File(mailingListsDirectory);
		File[] allFiles = dir.listFiles();

		List<String> courses = new ArrayList<String>();

		for (File file : allFiles) {
			if (file.isFile()) {
				courses.add(file.getName());
			}
		}
		return courses;
	}

	public List<String> getParticipantsOfCourse(String pCourse) throws IOException {
		List<String> emails = new ArrayList<String>();

		File mailingList = new File(mailingListsDirectory + "/" + pCourse + ".txt");
		if (mailingList.isFile()) {
			BufferedReader reader = new BufferedReader(new FileReader(mailingList));
			String line = reader.readLine();
			while (line != null) {
				emails.add(line);
				line = reader.readLine();
			}
		}
		return emails;
	}
}
