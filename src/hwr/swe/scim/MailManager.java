/**
 * 
 */
package hwr.swe.scim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.commons.mail.*;

/**
 * @author Sidney
 *
 */
public class MailManager implements MessageManager {

	private static final String MAIL_SMTP_SERVER = "smtp.stud.hwr-berlin.de"; //Port 465
	private static final String USERNAME = "s_steimel"; 
	private static final String SENDER = "noReply@hwr-berlin.de";
	private static final String RECEIVER = "thgaertner.sft@t-online.de";
	private static final String CHARSET = "UTF-8";
	private static final String CONTENT = "Test";

	/* (non-Javadoc)
	 * @see hwr.swe.scim.MessageManager#giveMessage(java.util.List)
	 */
	@Override
	public boolean giveMessage(List<Lecture> pList) {
		return sendMail(MAIL_SMTP_SERVER, USERNAME, SENDER, RECEIVER,
				CHARSET, CONTENT, generateText(pList));
	}
	
	private boolean sendMail(String pMailserver, String pUsername, String pAbsender, String pEmpfaenger,
			String pTextCharset, String pBetreff, String pText) {
	/*public static String sendeEmailMitAnhang(...
			String anhangContentType, InputStream anhangInputStream, String anhangDateiName, String anhangBeschreibung
			 )
			{*/
		String password = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter password: ");
        try {
			password = br.readLine();
		} catch (IOException e1) {
			System.out.println("reading line failed");
		}
		try {
			//MultiPartEmail email = new org.apache.commons.mail.MultiPartEmail();
			SimpleEmail email = new org.apache.commons.mail.SimpleEmail();
			if( pUsername != null && password != null ) {
				email.setAuthenticator( new org.apache.commons.mail.DefaultAuthenticator( pUsername, password ) );
			    email.setSSLOnConnect( true );
			}
			email.setHostName( pMailserver  );
			email.setFrom(     pAbsender    );
			email.addTo(       pEmpfaenger  );
			email.setCharset(  pTextCharset );
			email.setSubject(  pBetreff     );
			email.setMsg(      pText        );
			/*email.attach( new ByteArrayDataSource( anhangInputStream, anhangContentType ),
			                    anhangDateiName, anhangBeschreibung, EmailAttachment.ATTACHMENT );*/
			email.send();
		}
		catch (/*IOException |*/ EmailException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	private String generateText(List<Lecture> pList){
		String generatedText = "";
		for(Lecture lecture: pList) {
			if (lecture.getIsCreated()) {
				generatedText += "\nNeue " + lecture.toString();
			} else {
				generatedText += "\nGelöschte " + lecture.toString();
			}
		}
		return generatedText;
	}
}

