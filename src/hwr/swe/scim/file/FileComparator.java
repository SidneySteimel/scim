package hwr.swe.scim.file;

import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.fortuna.ical4j.data.ParserException;

/**
 * This class compares two ics files.
 * 
 * @author Elen Niedermeyer
 *
 */
public class FileComparator {

	/**
	 * Instance of the ICSManager.
	 */
	private ICSManager icsManager = new ICSManager();

	/**
	 * This method gets lists of events from the given files and makes a list
	 * with events that are not in both lists.
	 * 
	 * @param pOldFile
	 *            path to the old ics file
	 * @param pNewFile
	 *            path to the new ics file
	 * @return a list that contains descriptions of the lectures that are added
	 *         or deleted
	 * @throws ParserException
	 *             if there's a problem with parsing the ics file
	 * @throws IOException
	 *             if the file doesn't exists
	 * @throws ParseException
	 *             if there's a problem with parsing dates
	 */
	public List<Lecture> getChanges(String pOldFile, String pNewFile)
			throws IOException, ParserException, ParseException {
		List<Lecture> oldEvents = icsManager.getRelevantEvents(pOldFile);
		List<Lecture> newEvents = icsManager.getRelevantEvents(pNewFile);

		if (oldEvents == null || newEvents == null) {
			return null;
		} else {
			List<Lecture> changes = new LinkedList<Lecture>();

			List<Lecture> deletedChanges = getDeletedEvents(oldEvents, newEvents);

			List<Lecture> addedChanges = getAddedEvents(oldEvents, newEvents);

			if (!deletedChanges.isEmpty()) {
				changes.addAll(deletedChanges);
			}
			if (!addedChanges.isEmpty()) {
				changes.addAll(addedChanges);
			}

			return changes;
		}
	}

	/**
	 * This method compares a list of old events with a new list of events. If a
	 * new events isn't part of the old list, it must be added.
	 * 
	 * @param pOldEvents
	 *            list with old events
	 * @param pNewEvents
	 *            list with new events
	 * @return a list with all added events
	 */
	private List<Lecture> getAddedEvents(List<Lecture> pOldEvents, List<Lecture> pNewEvents) {
		List<Lecture> result = new LinkedList<Lecture>();
		Iterator<Lecture> iteratorNewEvents = pNewEvents.iterator();
		while (iteratorNewEvents.hasNext()) {
			Lecture lecture = iteratorNewEvents.next();
			if (!pOldEvents.contains(lecture)) {
				lecture.setIsCreated(true);
				result.add(lecture);
			}
		}
		return result;
	}

	/**
	 * This method compares a list of old event with a new list of events. If an
	 * old event isn't part of the new list, it must be deleted.
	 * 
	 * @param pOldEvents
	 *            old list with events
	 * @param pNewEvents
	 *            new list with events
	 * @return a list with all deleted events
	 */
	private List<Lecture> getDeletedEvents(List<Lecture> pOldEvents, List<Lecture> pNewEvents) {
		List<Lecture> result = new LinkedList<Lecture>();
		Iterator<Lecture> iteratorOldEvents = pOldEvents.iterator();
		while (iteratorOldEvents.hasNext()) {
			Lecture lecture = iteratorOldEvents.next();
			if (!pNewEvents.contains(lecture)) {
				lecture.setIsCreated(false);
				result.add(lecture);
			}
		}
		return result;
	}
}