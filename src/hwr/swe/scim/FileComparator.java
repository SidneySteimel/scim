package hwr.swe.scim;

import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Component;

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
		List<Component> oldEvents = icsManager.getRelevantEvents(pOldFile);
		List<Component> newEvents = icsManager.getRelevantEvents(pNewFile);

		if (oldEvents == null || newEvents == null) {
			return null;
		} else {
			List<Lecture> changes = new LinkedList<Lecture>();

			List<Lecture> deletedChanges = getDeletedEvents(oldEvents, newEvents);

			List<Lecture> addedChanges = getAddedEvents(oldEvents, newEvents);
			
			if(!deletedChanges.isEmpty()){
				changes.addAll(deletedChanges);
			}
			if(!addedChanges.isEmpty()){
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
	 *            old list with events
	 * @param pNewEvents
	 *            new list with events
	 * @return a list with all added events
	 * @throws ParseException
	 *             if there's a problem getting the dates
	 */
	private List<Lecture> getAddedEvents(List<Component> pOldEvents, List<Component> pNewEvents) throws ParseException {
		List<Lecture> result = new LinkedList<Lecture>();
		Iterator<Component> iteratorNewEvents = pNewEvents.iterator();
		while (iteratorNewEvents.hasNext()) {
			Component component = iteratorNewEvents.next();
			if (!pOldEvents.contains(component)) {
				Lecture addedLecture = new Lecture();
				addedLecture.setName(icsManager.getEventName(component));
				addedLecture.setStartTime(icsManager.getStartTime(component));
				addedLecture.setEndTime(icsManager.getEndTime(component));
				addedLecture.setIsCreated(true);
				result.add(addedLecture);
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
	 * @throws ParseException
	 *             if there's a problem getting the dates
	 */
	private List<Lecture> getDeletedEvents(List<Component> pOldEvents, List<Component> pNewEvents)
			throws ParseException {
		List<Lecture> result = new LinkedList<Lecture>();
		Iterator<Component> iteratorOldEvents = pOldEvents.iterator();
		while (iteratorOldEvents.hasNext()) {
			Component component = iteratorOldEvents.next();
			if (!pNewEvents.contains(component)) {
				Lecture deletedLecture = new Lecture();
				deletedLecture.setName(icsManager.getEventName(component));
				deletedLecture.setStartTime(icsManager.getStartTime(component));
				deletedLecture.setEndTime(icsManager.getEndTime(component));
				deletedLecture.setIsCreated(false);
				result.add(deletedLecture);
			}
		}
		return result;
	}
}