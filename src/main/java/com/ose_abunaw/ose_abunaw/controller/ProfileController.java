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
            return "error";
        }
    }

    @PostMapping("/logout")
    public String logout() {
        userService.logout();

        return "redirect:/users/signin"; // Redirect the user to the sign-in page after logout
    }

    @PostMapping("/createContact")
    public String createContact(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            Model model) {

        // Retrieve the signed-in user's information
        User user = userService.getSignedInUser();

        if (user != null) {
            // Create a new contact
            Contact newContact = new Contact(firstName, lastName, email, phone);

            // Save the new contact using the ContactService
            Contact createdContact = contactService.createContact(user.getId(), newContact);

            if (createdContact != null) {
                // Redirect back to the user profile page
                return "redirect:/user/profile";
            } else {
                // Handle the case where the user with the given ID is not found
                return "error"; // You can define an error template for this case
            }
        } else {
            // Handle the case where the user is not signed in
            return "error";
        }
    }

    // Add a new mapping for updating a contact
    @PostMapping("/updateContact")
    public String updateContact(
            @RequestParam("contactId") Long contactId,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            Model model) {

        // Retrieve the signed-in user's information
        User user = userService.getSignedInUser();

        if (user != null) {
            // Update the contact with the provided details
            Contact updatedContact = new Contact(firstName, lastName, email, phone);
            updatedContact.setId(contactId); // Set the ID of the contact to be updated

            // Call the ContactService to update the contact
            Contact updated = contactService.updateContact(user.getId(), contactId, updatedContact);

            if (updated != null) {
                // Redirect back to the user profile page
                return "redirect:/user/profile";
            } else {
                // Handle the case where the contact with the given ID is not found
                return "error";
            }

        } else {
            // Handle the case where the user is not signed in
            return "error";
        }
    }

}
