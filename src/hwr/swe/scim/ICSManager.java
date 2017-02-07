package hwr.swe.scim;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
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
	 * @return a list that contains all relevant elements or null if the list is
	 *         empty
	 * 
	 * @throws IOException
	 *             if the file doesn't exists
	 * @throws ParserException
	 *             if there's a failure while parsing the ics file into a
	 *             calendar
	 * @throws ParseException
	 *             if there's a failure while parsing the date of events
	 * 
	 */
	public List<Component> getRelevantEvents(String pIcsPath) throws IOException, ParserException, ParseException {
		parseICS(pIcsPath);
		List<Component> relevantEvents = removePastEvents(getAllEvents());
		if (relevantEvents.isEmpty()) {
			return null;
		} else {
			return relevantEvents;
		}
	}

	/**
	 * Public method that returns the name of a given event. It parses it and
	 * returns only the event's name.
	 * 
	 * @param pEvent
	 *            calendar component which's summary you want to know
	 * @return the summary as string
	 */
	public String getEventName(Component pEvent) {
		String summary = pEvent.getProperty(Property.SUMMARY).getValue();
		String[] strings = summary.split(";");
		return strings[1];
	}

	/**
	 * Public method that returns the start time of a given event.
	 * 
	 * @param pEvent
	 *            calendar component which's start time you want to know
	 * @return the start time as an date object
	 * @throws ParseException
	 *             if there's a failure while parsing the date
	 */
	public Date getStartTime(Component pEvent) throws ParseException {
		// get the start time and parse it to a date object
		String startTime = pEvent.getProperty(Property.DTSTART).getValue();
		Date startDate = null;
		startDate = new SimpleDateFormat("yyyyMMdd'T'HHmmss").parse(startTime);
		return startDate;
	}

	/**
	 * Public method that returns the end time of a given event.
	 * 
	 * @param pEvent
	 *            calendar component which's end time you want to know
	 * @return the end time as an date object
	 * @throws ParseException
	 *             if there's a failure while parsing the date
	 */
	public Date getEndTime(Component pEvent) throws ParseException {
		// get the end time and parse it to a date object{
		String endTime = pEvent.getProperty(Property.DTEND).getValue();
		Date endDate = null;
		endDate = new SimpleDateFormat("yyyyMMdd'T'HHmmss").parse(endTime);
		return endDate;
	}

	/**
	 * Gets an ics file and parses it to an calendar object.
	 * 
	 * @param pFileName
	 *            path to the ics file
	 * @throws IOException
	 *             if the file couldn't be found
	 * @throws ParserException
	 *             if there's a problem with parsing the file
	 */
	private void parseICS(String pFileName) throws IOException, ParserException {
		FileInputStream input = new FileInputStream(pFileName);
		CalendarBuilder builder = new CalendarBuilder();
		calendar = builder.build(input);
	}

	/**
	 * Iterates over the class attribute calendar on adds all events to a list.
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
	 * @throws ParseException
	 *             if there's a failure while parsing the date
	 */
	private List<Component> removePastEvents(List<Component> pEvents) throws ParseException {
		Iterator<Component> iterator = pEvents.iterator();
		while (iterator.hasNext()) {
			Component component = iterator.next();
			// get the start date and parse it to a date object
			String startTime = component.getProperty(Property.DTSTART).getValue();
			Date startDate = null;
			startDate = new SimpleDateFormat("yyyyMMdd'T'HHmmss").parse(startTime);

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