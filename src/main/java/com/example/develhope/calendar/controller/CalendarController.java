package com.example.develhope.calendar.controller;

import com.example.develhope.calendar.models.Calendar;
import com.example.develhope.calendar.models.Event;
import com.example.develhope.calendar.services.CalendarService;
import com.example.develhope.calendar.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calendars")
public class CalendarController {
    @Autowired
    CalendarService calendarService;
    @Autowired
    EventService eventService;

    @PostMapping("/add")
    public void addCalendar(Calendar calendar) {
        calendarService.addCalendar(calendar);
    }

    @PostMapping("/add-to-user")
    public void addCalendarToUser(Calendar calendar, Long userId) {
        calendarService.addCalendarToUser(calendar, userId);
    }

    @PostMapping("/add-event")
    public void addEventToCalendar(Long calendarId, Event event) {
        calendarService.addEventToCalendar(calendarId, event);
        eventService.addEventToCalendar(calendarId, event);
    }

    @GetMapping("/all")
    public Iterable<Calendar> getAllCalendars() {
        return calendarService.getAllCalendars();
    }

    @GetMapping("/calendars-by-user")
    public Iterable<Calendar> getCalendarsByUser(Long userId) {
        return calendarService.getCalendarsByUser(userId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCalendarById(@PathVariable Long id) {
        calendarService.deleteCalendarById(id);
    }

    @PutMapping("/update")
    public void updateCalendar(Calendar newCalendar) {
        calendarService.updateCalendar(newCalendar);
    }
}
