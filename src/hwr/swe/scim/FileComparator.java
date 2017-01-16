package hwr.swe.scim;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.fortuna.ical4j.model.Component;

public class FileComparator {

	private ICSManager icsManager = new ICSManager();

	public List<Lecture> getChanges(String pOldFile, String pNewFile) {
		List<Component> oldEvents = icsManager.getRelevantEvents(pOldFile);
		List<Component> newEvents = icsManager.getRelevantEvents(pNewFile);

		List<Lecture> changes = new LinkedList<Lecture>();

		Iterator<Component> iteratorOldEvents = oldEvents.iterator();
		while (iteratorOldEvents.hasNext()) {
			Component component = iteratorOldEvents.next();
			if (!newEvents.contains(component)) {
				Lecture deletedLecture = new Lecture();
				deletedLecture.setName(icsManager.getEventName(component));
				deletedLecture.setStartTime(icsManager.getStartTime(component));
				deletedLecture.setEndTime(icsManager.getEndTime(component));
				deletedLecture.setIsCreated(false);
				changes.add(deletedLecture);
			}
		}

		Iterator<Component> iteratorNewEvents = newEvents.iterator();
		while (iteratorNewEvents.hasNext()) {
			Component component = iteratorNewEvents.next();
			if (!oldEvents.contains(component)) {
				Lecture addedLecture = new Lecture();
				addedLecture.setName(icsManager.getEventName(component));
				addedLecture.setStartTime(icsManager.getStartTime(component));
				addedLecture.setEndTime(icsManager.getEndTime(component));
				addedLecture.setIsCreated(true);
			}
		}

		return changes;
	}
}