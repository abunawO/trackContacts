package com.ose_abunaw.ose_abunaw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ose_abunaw.ose_abunaw.model.Contact;
import com.ose_abunaw.ose_abunaw.model.User;
import com.ose_abunaw.ose_abunaw.service.ContactService;
import com.ose_abunaw.ose_abunaw.service.UserService;

@Controller
public class ProfileController {

    private final UserService userService;
    private final ContactService contactService;

    @Autowired
    public ProfileController(UserService userService, ContactService contactService) {
        this.userService = userService;
        this.contactService = contactService;
    }

    @GetMapping("/user/profile")
    public String showUserProfile(Model model) {
        // Retrieve the signed-in user's information
        System.out.println("in profile controller");
        User user = userService.getSignedInUser(); // Implement this method in your UserService

        if (user != null) {
            model.addAttribute("user", user);

            // Initially, load all user's contacts
            List<Contact> allContacts = contactService.getAllContactsByUserId(user.getId());
            model.addAttribute("filteredContacts", allContacts);

            System.out.println("rotuine to User Profile Page");
            return "user-profile"; // Return the HTML template
        } else {
            // Handle the case where the user is not signed in
            return "error"; // You can define an error template for this case
        }
    }

    @PostMapping("/user/profile")
    public String searchContacts(
            @RequestParam("firstName") String searchFirstName,
            @RequestParam("lastName") String searchLastName,
            Model model) {
        // Retrieve the signed-in user's information
        User user = userService.getSignedInUser();

        if (user != null) {
            model.addAttribute("user", user);

            // Search for contacts based on the criteria
            List<Contact> filteredContacts = contactService.searchContacts(user.getId(), searchFirstName,
                    searchLastName);
            model.addAttribute("filteredContacts", filteredContacts);

            // Populate search criteria to retain in the form
            model.addAttribute("searchFirstName", searchFirstName);
            model.addAttribute("searchLastName", searchLastName);

            return "user-profile"; // Return the HTML template
        } else {
            // Handle the case where the user is not signed in
            return "error"; // You can define an error template for this case
        }
    }

    @PostMapping("/logout")
    public String logout() {
        // Implement user logout logic, such as clearing the session
        userService.logout(); // You need to define this method in your UserService

        return "redirect:/users/signin"; // Redirect the user to the sign-in page after logout
    }
}
