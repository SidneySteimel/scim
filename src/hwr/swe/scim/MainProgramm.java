package hwr.swe.scim;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class MainProgramm {

	// must be the intranet address
	// private final static String PATH_NEW_FILE_ORIGINAL = "new_file.html";

	// must be our working folder
	private final static String PATH_OLD_FILE = "test/old_file.html";
	private final static String PATH_NEW_FILE_COPY = "test/new_file.html";

	private static FileComparator comparator = new FileComparator();
	private static MessageManager mailManager = new WindowManager();

	/**
	 * This method creates File objects. It copies the new file from a URL into
	 * our directory. The FileComparator compares the files and adds changes to
	 * a list. If there's a change, a mail would be sent (opens a window in
	 * prototype). Then it replaces the old file with the new file.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// create File objects
		// File newFileOriginal = new File(PATH_NEW_FILE_ORIGINAL);
		File newFileCopy = new File(PATH_NEW_FILE_COPY);
		File oldFile = new File(PATH_OLD_FILE);

		try {
			// copy new file
			// Files.copy(newFileOriginal.toPath(), newFileCopy.toPath());
			FileUtils.copyURLToFile(
					new URL("http://moodle.hwr-berlin.de/fb2-stundenplan/download.php?doctype=.html&url=./fb2-stundenplaene/informatik/semester3/kurs"),
					newFileCopy);

			// give a message, if there's a change
			List<String> changes = comparator.compareFiles(oldFile, newFileCopy);
			if (!changes.isEmpty()) {
				mailManager.giveMessage(changes);
			}

			// replace the old file with the new one
			replaceOldFile(newFileCopy, oldFile);
		} catch (IOException e) {
			e.printStackTrace();
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
		Files.delete(pOldFile.toPath());
		pNewFile.renameTo(pOldFile);
	}

}
