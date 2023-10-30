package com.ose_abunaw.ose_abunaw.service;

import com.ose_abunaw.ose_abunaw.model.User;
import com.ose_abunaw.ose_abunaw.repository.ProfileRepository;
import com.ose_abunaw.ose_abunaw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Optional;

@Service
@SessionScope
public class UserService {

    private final UserRepository userRepository;
    // Create a field to store the signed-in user
    private User signedInUser;

    @Autowired
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
