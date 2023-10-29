package com.ose_abunaw.ose_abunaw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ose_abunaw.ose_abunaw.model.User;
import com.ose_abunaw.ose_abunaw.model.Profile;
import com.ose_abunaw.ose_abunaw.repository.ProfileRepository;
import com.ose_abunaw.ose_abunaw.repository.UserRepository;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    // Create a field to store the signed-in user
    private User signedInUser;

    @Autowired
    public UserService(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
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

    public User getUserProfile(Long userId) {
        // Retrieve the user's profile information by their ID
        return userRepository.findById(userId).orElse(null);
    }

    public Profile createUserProfile(Long userId, Profile profile) {
        // Link the profile to the user
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            profile.setUser(user);
            return profileRepository.save(profile);
        }

        // Handle the case where the user does not exist
        return null;
    }

    public User getSignedInUser() {
        return signedInUser;
    }

    public void logout() {
        // Clear the signed-in user when logging out
        signedInUser = null;
    }

}
