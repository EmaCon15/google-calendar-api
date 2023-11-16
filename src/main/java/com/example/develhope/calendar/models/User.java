package com.example.develhope.calendar.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Il nome utente è obbligatorio")
    @Column(unique = true, name = "username", nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Il nome utente deve contenere solo lettere e numeri")
    private String username;

    @NotBlank(message = "La password è obbligatoria")
    @Column(nullable = false, name = "password", unique = true)
    @Size(min = 8, message = "La password deve contenere più di 8 caratteri")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "La password deve contenere almeno una lettera maiuscola, una minuscola e un numero")
    private String password;

    @NotBlank
    @Column(unique = true, name = "email", nullable = false)
    @Email(message = "L'email non è valida")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "L'email non è valida", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Calendar> calendars;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "event_id",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    // Assicurati che il nome corrisponda alla colonna nella tabella Event
    private List<Event> events;


    // Costruttori, getter e setter

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Calendar> getCalendars() {
        return calendars;
    }

    public void setCalendars(Set<Calendar> calendars) {
        this.calendars = calendars;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}