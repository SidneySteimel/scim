package hwr.swe.scim;

import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;

/**
 * This class should give information from ics files.
 * 
 * @author Elen Niedermeyer
 *
 */
public class ICSManager {

	/**
	 * events are started and ended with this tag
	 */
	private static final String eventTag = "VEVENT";
	/**
	 * a class attribute that is the actual calendar
	 */
	private Calendar calendar;

	/**
	 * Public method that gives all events that are on time.
	 * 
	 * @param pIcsPath
	 *            path to the ics file
	 * @return a list that contains all relevant elements
	 */
	public List<Component> getRelevantEvents(String pIcsPath) {
		parseICS(pIcsPath);
		List<Component> relevantEvents = removePastEvents(getAllEvents());
		return relevantEvents;
	}

	/**
	 * Public method that returns the name of a given event.
	 * 
	 * @param pEvent
	 *            calendar component which summary you want to know
	 * @return the summary as string
	 */
	public String getEventName(Component pEvent) {
		// noch parsen?
		return pEvent.getProperty(Property.SUMMARY).getValue();
	}

	/**
	 * Gets an ics file and parses it to an calendar object.
	 * 
	 * @param pFileName
	 *            path to the ics file
	 */
	private void parseICS(String pFileName) {
		try {
			FileInputStream input = new FileInputStream(pFileName);
			CalendarBuilder builder = new CalendarBuilder();
			calendar = builder.build(input);
		} catch (Exception e) {
			// hier noch was überlegen
			e.printStackTrace();
		}
	}

	/**
	 * Iterates over the class attribut calendar on adds all events to a list.
	 * 
	 * @return a list of calendar components
	 */
	private List<Component> getAllEvents() {
		List<Component> events = new LinkedList<Component>();
		// iterate over the calendar and adds an element for each event
		for (Iterator<CalendarComponent> i = calendar.getComponents(eventTag).iterator(); i.hasNext();) {
			Component component = (Component) i.next();
			events.add(component);
		}
		return events;
	}

	/**
	 * Gets a list of events and deletes the events that are in the past. It
	 * compares the start time to the actual date.
	 * 
	 * @param pEvents
	 *            is a list of components from a calendar
	 * @return the list without the events in the past
	 */
	private List<Component> removePastEvents(List<Component> pEvents) {
		Iterator<Component> iterator = pEvents.iterator();
		while (iterator.hasNext()) {
			Component component = iterator.next();
			// get the start date and parse it to a date object
			String startTime = component.getProperty(Property.DTSTART).getValue();
			Date startDate = null;
			try {
				startDate = new SimpleDateFormat("yyyyMMdd'T'HHmmss").parse(startTime);
			} catch (ParseException e) {
				// hier noch was überlegen
				e.printStackTrace();
			}
			// get today's date
			Date today = new Date();
			today.setHours(0);
			today.setMinutes(0);
			today.setSeconds(0);
			// delete event, if it's in the past
			if (startDate != null && startDate.before(today)) {
				iterator.remove();
			}
		}
		return pEvents;
	}
}