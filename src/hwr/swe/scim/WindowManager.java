package hwr.swe.scim;

import java.util.List;

import javax.swing.JOptionPane;

public class WindowManager implements MessageManager {

	/**
	 * Creates a window with the Strings in the list.
	 */
	@Override
	public boolean giveMessage(List<String> pList) {
		String body = generateMessage(pList);
		JOptionPane.showMessageDialog(null, body, null, JOptionPane.ERROR_MESSAGE);
		return true;
	}

	/**
	 * Creates a message from the List<String>.
	 * 
	 * @param pList
	 *            a List<String> that contains the changes
	 * @return
	 */
	private String generateMessage(List<String> pList) {
		String message = "";
		for (String change : pList) {
			message += change + "\n";
		}
		return message;
	}

}
