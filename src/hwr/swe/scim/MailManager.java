package hwr.swe.scim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import hwr.swe.scim.file.Lecture;

/**
 * this class contains methods to send an email with information about lectures
 * javax mail and apache commons mail are used to send this email
 * 
 * @author Sidney Steimel
 *
 */
public class MailManager implements MessageManager {

	/**
	 * apache commons mail requires some attributes to send an email 1. a smtp
	 * server that actually send the mail 2. a user to log in because
	 * authentication is required (a password as well) 3. the email address that
	 * shall be named as sender 4. the list of email addresses that the outgoing
	 * mail shall be send to 5. the charset that shall be used in the emails
	 * text 6. the content of the email
	 */
	private static final String MAIL_SMTP_SERVER = "smtp.stud.hwr-berlin.de"; // Port 465
	private static final String USERNAME = "s_steimel";
	private static final String SENDER = "scim@hwr-berlin.de";
	private static final String CHARSET = "UTF-8";
	private static final String CONTENT = "Stundenplan\u00e4nderung";

	/**
	 * this method is used to provide information to users in this case via
	 * email
	 * 
	 * @throws EmailException 
	 * 				if message could not be send to users
	 * @throws IOException 
	 * 				if password reading failed
	 */
	@Override
	public void giveMessage(List<Lecture> pLectureList, List<String> pReceiversList) throws IOException, EmailException {
		sendMail(MAIL_SMTP_SERVER, USERNAME, SENDER, pReceiversList, CHARSET, CONTENT, generateText(pLectureList));
	}

	/**
	 * this method sends the email first an authenticator gets attached to the
	 * email the user is prompted for a password (so we dont have to write one
	 * into the code) then all the required attributes get set to the email and
	 * after that the mail gets sended
	 * 
	 * @param pMailserver
	 *            the mail sending server
	 * @param pUsername
	 *            the user used for authentication
	 * @param pAbsender
	 *            named sender
	 * @param pReceiversList
	 *            list of addresses receiving the mail
	 * @param pTextCharset
	 *            mails charset
	 * @param pBetreff
	 *            content of the email
	 * @param pText
	 *            mail body
	 * 
	 * @throws IOException
	 * 				if reading password from commandline fails
	 * @throws EmailException
	 * 				if there was a problem sending an email
	 */
	private void sendMail(String pMailserver, String pUsername, String pAbsender, List<String> pReceiversList,
			String pTextCharset, String pBetreff, String pText) throws IOException, EmailException {
		String password = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Please enter password: ");

		password = br.readLine();

		SimpleEmail email = new org.apache.commons.mail.SimpleEmail();
		if (pUsername != null && password != null) {
			email.setAuthenticator(new org.apache.commons.mail.DefaultAuthenticator(pUsername, password));
			email.setSSLOnConnect(true);
		}
		email.setHostName(pMailserver);
		email.setFrom(pAbsender);

		for (String Empfaenger : pReceiversList) {
			email.addTo(Empfaenger);
		}
		
		email.setCharset(pTextCharset);
		email.setSubject(pBetreff);
		email.setMsg(pText);

		email.send();
	}

	/**
	 * generates the email body by iterating over the provided list of lectures.
	 * lectures are treated differently according to their state the can either
	 * be 'created' or 'removed'
	 * 
	 * @param pList
	 *            a list of lectures that were changed in any way
	 * @return generatedText the generated email text
	 */
	private String generateText(List<Lecture> pList) {
		String generatedText = "Hallo liebe Studierende,\n\nIn eurem Stundenplan haben sich folgende \u00c4nderungen ergeben:\n";
		for (Lecture lecture : pList) {

			if (lecture.getIsCreated()) {
				generatedText += "\nNeue " + lecture.toString();
			} else {
				generatedText += "\nGel\u00f6schte " + lecture.toString();
			}
		}
		return generatedText;
	}
}
