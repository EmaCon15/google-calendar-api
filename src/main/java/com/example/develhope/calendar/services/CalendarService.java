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

@Service
public class CalendarService {
    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;

    @Transactional
    public void addCalendar(Calendar calendar) {
        calendarRepository.save(calendar);
    }

    /**
     * Adds a calendar to a user.
     *
     * @param  calendar  the calendar to be added
     * @param  userId  the id of the user
     */
    @Transactional
    public void addCalendarToUser(Calendar calendar, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));
        user.getCalendars().add(calendar);
        calendar.setUser(user);
        userRepository.save(user);
        calendarRepository.save(calendar);
    }

    /**
     * Adds an event to the calendar.
     *
     * @param  calendarId  the ID of the calendar
     * @param  event       the event to be added
     */
    public void addEventToCalendar(Long calendarId, Event event) {
        Calendar calendar = calendarRepository.findById(calendarId).orElseThrow(() -> new IllegalArgumentException("Calendar not found"));
        event.setCalendar(calendar);
        calendar.getEvents().add(event);
        calendarRepository.save(calendar);
        eventRepository.save(event);
    }

    public Iterable<Calendar> getAllCalendars() {
        return calendarRepository.findAll();
    }

    /**
     * Retrieves a list of calendars associated with a specific user.
     *
     * @param  userId  the unique identifier of the user
     * @return         an iterable collection of Calendar objects
     */
    @Transactional
    public Iterable<Calendar> getCalendarsByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getCalendars();
    }

    @Transactional
    public void deleteCalendarById(Long id) {
        calendarRepository.deleteById(id);
    }

    @Transactional
    public void updateCalendar(Calendar newCalendar) {
        calendarRepository.findById(newCalendar.getId()).ifPresent(existingCalendar -> {
            existingCalendar.setTitle(newCalendar.getTitle());
            existingCalendar.setDescription(newCalendar.getDescription());
            calendarRepository.save(existingCalendar);
        });
    }
}
