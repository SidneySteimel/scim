 package hwr.swe.scim;

import java.util.List;

public interface MessageManager {

	/**
	 * Generates a message and print it.
	 * 
	 * @param pList
	 *            List<String> that contains the changes.
	 *            List<Receiver>
	 * @return
	 */
	boolean giveMessage(List<Lecture> pList, List<String> pReceiver);
}
