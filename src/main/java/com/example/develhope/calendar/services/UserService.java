package com.example.develhope.calendar.services;

import com.example.develhope.calendar.models.User;
import com.example.develhope.calendar.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    /**
     * Saves a user to the database.
     *
     * @param  user  the user object to be saved
     */
    @Transactional
    public void addUser(User user) {
        userRepository.save(user);
    }

    /**
     * Retrieves all users from the repository.
     *
     * @return  an iterable collection of User objects representing all the users in the repository
     */
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param  id  the ID of the user
     * @return     the user with the specified ID
     */
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    /**
     * Deletes a user by their ID.
     *
     * @param  id  the ID of the user to be deleted
     */
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Updates a user in the system.
     *
     * @param  user  the user object containing the updated information
     */
    public void updateUser(User user) {
        userRepository.findById(user.getId()).ifPresent(existingUser -> {
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setEmail(user.getEmail());
            userRepository.save(existingUser);
        });
    }
}
