package com.ose_abunaw.ose_abunaw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ose_abunaw.ose_abunaw.model.Contact;
import com.ose_abunaw.ose_abunaw.model.User;
import com.ose_abunaw.ose_abunaw.service.ContactService;
import com.ose_abunaw.ose_abunaw.service.UserService;

@Controller
@RequestMapping("/user")
public class ProfileController {

    private final UserService userService;
    private final ContactService contactService;

    public ProfileController(UserService userService, ContactService contactService) {
        this.userService = userService;
        this.contactService = contactService;
    }

    @GetMapping("/profile")
    public String showUserProfile(Model model) {
        // Retrieve the signed-in user's information
        User user = userService.getSignedInUser();

        if (user != null) {
            model.addAttribute("user", user);

            // Initially, load all user's contacts
            List<Contact> allContacts = contactService.getAllContactsByUserId(user.getId());
            model.addAttribute("allContacts", allContacts); // Add all contacts to the model

            return "user-profile"; // Return the HTML template
        } else {
            // Handle the case where the user is not signed in
            return "error";
        }
    }

    @PostMapping("/profile/logout")
    public String logout() {
        userService.logout();

        return "redirect:/users/signin"; // Redirect the user to the sign-in page after logout
    }

}
