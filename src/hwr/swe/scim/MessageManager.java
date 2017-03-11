package hwr.swe.scim;

import java.io.IOException;
import java.util.List;

import org.apache.commons.mail.EmailException;

import hwr.swe.scim.file.Lecture;

public interface MessageManager {

	/**
	 * Generates a message and print it.
	 * 
	 * @param pLectureList
	 *            List<Lecture> that contains the changes. pReceiversList List
	 *            <String> containing email addresses
	 * 
	 * @throws EmailException
	 *             if sending an email failed
	 * @throws IOException
	 *             if reading password failed
	 */
	void giveMessage(List<Lecture> pLectureList, List<String> pReceiversList) throws IOException, EmailException;
}
