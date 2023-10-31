package com.abunaw_ose.abunaw_ose.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.abunaw_ose.abunaw_ose.model.User;
import com.abunaw_ose.abunaw_ose.repository.ProfileRepository;
import com.abunaw_ose.abunaw_ose.repository.UserRepository;

import java.util.Optional;

@Service
@SessionScope
public class UserService {

    private final UserRepository userRepository;
    // Create a field to store the signed-in user
    private User signedInUser;

    public UserService(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User signIn(String email, String password) {
        // Query the database for a user with the provided email
        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            // User credentials are valid, store the signed-in user in the session
            signedInUser = user;
            return user;
        }

        // User does not exist or the password is incorrect, return null
        return null;
    }

    public User getSignedInUser() {
        return signedInUser;
    }

    public User getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.orElse(null);
    }

    public void logout() {
        // Clear the signed-in user when logging out
        signedInUser = null;
    }

}
