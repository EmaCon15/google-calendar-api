package com.example.develhope.calendar.services;

import com.example.develhope.calendar.models.Calendar;
import com.example.develhope.calendar.models.Event;
import com.example.develhope.calendar.models.User;
import com.example.develhope.calendar.repository.CalendarRepository;
import com.example.develhope.calendar.repository.EventRepository;
import com.example.develhope.calendar.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    CalendarRepository calendarRepository;
    @Autowired
    UserRepository userRepository;

    /**
     * Adds an event to the calendar.
     *
     * @param calendarId the ID of the calendar
     * @param event      the event to be added
     */
    @Transactional
    public void addEventToCalendar(Long calendarId, Event event) {
        Calendar calendar = calendarRepository.findById(calendarId).orElseThrow(() -> new IllegalArgumentException("Calendar not found"));
        event.setCalendar(calendar);
        eventRepository.save(event);
    }

    @Transactional
    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new IllegalArgumentException("Event not found"));
    }

    @Transactional
    public Iterable<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    /**
     * Retrieves all events associated with a specific calendar.
     *
     * @param calendarId the ID of the calendar
     * @return an iterable of events associated with the calendar
     */
    @Transactional
    public Iterable<Event> getEventsByCalendarId(Long calendarId) {
        Calendar calendar = calendarRepository.findById(calendarId).orElseThrow(() -> new IllegalArgumentException("Calendar not found"));
        return calendar.getEvents();
    }

    /**
     * Retrieves a collection of events from the specified calendar that occur today.
     *
     * @param  calendarId the ID of the calendar
     * @return            an iterable collection of events occurring today
     */
    @Transactional
    public Iterable<Event> getEventsByToday(Long calendarId) {
        Calendar calendar = calendarRepository.findById(calendarId).orElseThrow(() -> new IllegalArgumentException("Calendar not found"));

        LocalDateTime startOfToday = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfToday = LocalDateTime.now().with(LocalTime.MAX);

        return calendar.getEvents().stream()
                .filter(event -> event.getStartDate().isAfter(startOfToday) && event.getEndDate().isBefore(endOfToday))
                .toList();
    }

    /**
     * Returns an iterable of events that match the given date for a specific calendar.
     *
     * @param  calendarId  the ID of the calendar
     * @param  date        the date to filter events by
     * @return             an iterable of events that match the given date
     */
    @Transactional
    public Iterable<Event> getEventsByDate(Long calendarId, LocalDate date) {
        Calendar calendar = calendarRepository.findById(calendarId).orElseThrow(() -> new IllegalArgumentException("Calendar not found"));

        return calendar.getEvents().stream()
                .filter(event -> event.getStartDate().toLocalDate().isEqual(date))
                .toList();
    }

    /**
     * Retrieves a list of events for a given calendar and week.
     *
     * @param  calendarId    the ID of the calendar
     * @param  startOfWeek   the start date of the week
     * @return               an iterable of events for the given calendar and week
     */
    @Transactional
    public Iterable<Event> getEventsByWeek(Long calendarId, LocalDate startOfWeek) {
        Calendar calendar = calendarRepository.findById(calendarId).orElseThrow(() -> new IllegalArgumentException("Calendar not found"));

        LocalDate endOfWeek = startOfWeek.plusDays(6);
        return calendar.getEvents().stream()
                .filter(event ->
                                (event.getStartDate().toLocalDate().isAfter(startOfWeek) || event.getStartDate().toLocalDate().isEqual(startOfWeek)) &&
                                (event.getEndDate().toLocalDate().isBefore(endOfWeek) || event.getEndDate().toLocalDate().isEqual(endOfWeek)))
                .toList();
    }

    /**
     * Retrieves all events from a given calendar that fall within a specific month.
     *
     * @param  calendarId   the ID of the calendar
     * @param  startOfMonth the start date of the month
     * @return              an iterable of events within the specified month
     */
    @Transactional
    public Iterable<Event> getEventsByMonth(Long calendarId, LocalDate startOfMonth) {
        Calendar calendar = calendarRepository.findById(calendarId).orElseThrow(() -> new IllegalArgumentException("Calendar not found"));

        LocalDate endOfMonth = startOfMonth.plusMonths(1);
        return calendar.getEvents().stream()
                .filter(event ->
                        (event.getStartDate().toLocalDate().isAfter(startOfMonth) || event.getStartDate().toLocalDate().isEqual(startOfMonth)) &&
                                (event.getEndDate().toLocalDate().isBefore(endOfMonth) || event.getEndDate().toLocalDate().isEqual(endOfMonth)))
                .toList();
    }

    /**
     * Updates an event with the provided new event details.
     *
     * @param  newEvent  the new event object containing the updated details
     * @return           the updated event object
     */
    @Transactional
    public Event updateEvent(Event newEvent) {
        Event event = eventRepository.findById(newEvent.getId()).orElseThrow(() -> new IllegalArgumentException("Event not found"));
        event.setTitle(newEvent.getTitle());
        event.setDescription(newEvent.getDescription());
        event.setCalendar(newEvent.getCalendar());
        event.setStartDate(newEvent.getStartDate());
        event.setEndDate(newEvent.getEndDate());
        return eventRepository.save(event);
    }

    @Transactional
    public void deleteEventById(Long id) {
        eventRepository.deleteById(id);
    }

    /**
     * Adds a participant to an event.
     *
     * @param  eventId  the ID of the event
     * @param  userId   the ID of the user
     */
    @Transactional
    public void addParticipantToEvent(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new IllegalArgumentException("Event not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        event.getParticipants().add(user);
        eventRepository.save(event);
    }

    /**
     * Removes a participant from an event.
     *
     * @param  eventId  the ID of the event
     * @param  userId   the ID of the user to be removed
     */
    @Transactional
    public void removeParticipantFromEvent(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new IllegalArgumentException("Event not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        event.getParticipants().remove(user);
        eventRepository.save(event);
    }

    @Transactional
    public void addParticipantsToEvent(Long eventId, List<Long> userIds) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new IllegalArgumentException("Event not found"));
        List<User> users = userRepository.findAllById(userIds);
        event.getParticipants().addAll(users);
        eventRepository.save(event);
    }

    @Transactional
    public void removeParticipantsFromEvent(Long eventId, List<Long> userIds) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new IllegalArgumentException("Event not found"));
        List<User> users = userRepository.findAllById(userIds);
        event.getParticipants().removeAll(users);
        eventRepository.save(event);
    }
}
