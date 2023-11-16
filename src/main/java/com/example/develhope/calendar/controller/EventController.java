package com.example.develhope.calendar.controller;

import com.example.develhope.calendar.models.Calendar;
import com.example.develhope.calendar.models.Event;
import com.example.develhope.calendar.models.User;
import com.example.develhope.calendar.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    EventService eventService;

    @PostMapping("/add")
    public void addEventToCalendar(Long calendarId, Event event) {
        eventService.addEventToCalendar(calendarId, event);
    }

    @GetMapping("/all")
    public Iterable<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/calendar/{calendarId}")
    public Iterable<Event> getEventsByCalendarId(Long calendarId) {
        return eventService.getEventsByCalendarId(calendarId);
    }

    @GetMapping("/today")
    public Iterable<Event> getEventsByToday(Long calendarId) {
        return eventService.getEventsByToday(calendarId);
    }

    @GetMapping("/week")
    public Iterable<Event> getEventsByWeek(Long calendarId, LocalDate startOfWeek) {
        return eventService.getEventsByWeek(calendarId, startOfWeek);
    }

    @GetMapping("/month")
    public Iterable<Event> getEventsByMonth(Long calendarId, LocalDate startOfMonth) {
        return eventService.getEventsByMonth(calendarId, startOfMonth);
    }

    @GetMapping("/date")
    public Iterable<Event> getEventsByDate(Long calendarId, LocalDate date) {
        return eventService.getEventsByDate(calendarId, date);
    }

    @PutMapping("/update")
    public Event updateEvent(Event event) {
        return eventService.updateEvent(event);
    }

    @DeleteMapping("/remove")
    public void deleteEventById(Long eventId) {
        eventService.deleteEventById(eventId);
    }

    @PutMapping("/addParticipant")
    public void addParticipantToEvent(Long eventId, Long userId) {
        eventService.addParticipantToEvent(eventId, userId);
    }

    @PutMapping("/removeParticipant")
    public void removeParticipantFromEvent(Long eventId, Long userId) {
        eventService.removeParticipantFromEvent(eventId, userId);
    }

    @PutMapping("/addParticipants")
    public void addParticipantsToEvent(Long eventId, List<Long> userIds) {
        eventService.addParticipantsToEvent(eventId, userIds);
    }

    @PutMapping("/removeParticipants")
    public void removeParticipantsFromEvent(Long eventId, List<Long> userIds) {
        eventService.removeParticipantsFromEvent(eventId, userIds);
    }
}
