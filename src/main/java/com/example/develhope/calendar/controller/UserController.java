package com.example.develhope.calendar.controller;

import com.example.develhope.calendar.models.User;
import com.example.develhope.calendar.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/add")
    public void addUser(User user) {
        userService.addUser(user);
    }

    @GetMapping("/all")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get/{id}")
    public User getUserById(Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(Long id) {
        userService.deleteUserById(id);
    }

    @PutMapping("/update")
    public void updateUser(User user) {
        userService.updateUser(user);
    }
}
