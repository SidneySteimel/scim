package hwr.swe.scim;

import java.util.List;

public interface MessageManager {

	/**
	 * Generates a message and print it.
	 * 
	 * @param pList
	 *            List<String> that contains the changes.
	 * @return
	 */
	boolean giveMessage(List<Lecture> pList);
}
