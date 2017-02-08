package hwr.swe.scim;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class describes a lecture.
 * 
 * @author Elen Niedermeyer
 *
 */
public class Lecture {

	/**
	 * Attributes that are needed to describe a lecture.
	 */
	private String name;
	private Date startTime;
	private Date endTime;
	// isCreated = 1 means it's an added lecture
	// isCreated = 0 means it's a deleted lecture
	private boolean isCreated;

	/**
	 * Setter of the name attribute.
	 * 
	 * @param pName
	 *            name of the lecture a string
	 */
	public void setName(String pName) {
		this.name = pName;
	}

	/**
	 * Setter of the start time attribute.
	 * 
	 * @param pDate
	 *            a date object
	 */
	public void setStartTime(Date pDate) {
		this.startTime = pDate;
	}

	/**
	 * Setter of the end time attribute.
	 * 
	 * @param pDate
	 *            a date object
	 */
	public void setEndTime(Date pDate) {
		this.endTime = pDate;
	}

	/**
	 * Setter of the isCreated flag. 1 means the lecture was added, 0 means the
	 * lecture was deleted.
	 * 
	 * @param pFlag
	 */
	public void setIsCreated(boolean pFlag) {
		this.isCreated = pFlag;
	}
	
	/**
	 * Getter of the isCreated flag.
	 * 
	 * @return isCreated attribute
	 */
	public boolean getIsCreated() {
		return isCreated;
	}

	/**
	 * Returns a string that describes the lecture in a readable string. It's a
	 * German string.
	 */
	@Override
	public String toString() {
		Format dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		String startDateString = dateFormat.format(startTime);
		Format timeFormat = new SimpleDateFormat("HH.mm");
		String startTimeString = timeFormat.format(startTime);
		String endTimeString = timeFormat.format(endTime);
		String string = "Veranstaltung: " + name + " am " + startDateString + " von " + startTimeString + " bis "
				+ endTimeString;
		return string;
	}
}