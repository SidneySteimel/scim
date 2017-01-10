package hwr.swe.scim;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FileComparator {

	/**
	 * This method compares the two given HTML files.
	 * 
	 * @param pOldFile
	 *            a File object that is a HTML file
	 * @param pNewFile
	 *            a File object that is a HTML file
	 * @return a List<String> that includes a message, if there are changes
	 */
	public List<String> compareFiles(File pOldFile, File pNewFile) {
		// new List<String> is created and set null
		List<String> changes = new ArrayList<String>();

		// get two comparable List<String> of the files
		List<String> oldFileRelevant = getRelevantStringsFromHTML(pOldFile);
		List<String> newFileRelevant = getRelevantStringsFromHTML(pNewFile);

		// if there is in the new file an object that isn't in the old one, it
		// must be different an there must be a message
		if (!oldFileRelevant.containsAll(newFileRelevant)) {
			changes.add("There's a change");
		}

		return changes;
	}

	/**
	 * This method gets a file. It parses a Document of HTML code and gets all
	 * elements by tag 'table'. Than is creates String of these elements and
	 * adds it to a list.
	 * 
	 * @param pFile
	 *            a File object of which you want to get the tables as String
	 * @return a List<String> that includes all 'table' elements of the given
	 *         file as String
	 */
	private List<String> getRelevantStringsFromHTML(File pFile) {
		List<String> htmlList = new ArrayList<String>();
		try {
			Document doc = Jsoup.parse(pFile, null);
			Elements elements = doc.getElementsByTag("table");

			for (Element element : elements) {
				htmlList.add(element.html());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return htmlList;
	}
}
