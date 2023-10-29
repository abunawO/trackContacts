package com.ose_abunaw.ose_abunaw.controller;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.ose_abunaw.ose_abunaw.model.Contact;
import com.ose_abunaw.ose_abunaw.service.ContactService;
import com.ose_abunaw.ose_abunaw.service.UserService;
import com.ose_abunaw.ose_abunaw.model.User;

import java.util.List;

@Controller
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;
    private final UserService userService;

    @Autowired
    public ContactController(ContactService contactService, UserService userService) {
        this.contactService = contactService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public String createContact(
            @RequestParam("userId") Long userId,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber) {

        // Fetch the user
        User user = userService.getUserById(userId);
        if (user == null) {
            return "error"; // Handle user not found
        }

        // Create a new contact using the builder pattern
        Contact newContact = new Contact.Builder(firstName, lastName, email, phoneNumber)
                .user(user) // Associate the contact with the user
                .build();

        Contact createdContact = contactService.createContact(userId, newContact);

        if (createdContact != null) {
            // Handle success
            return "success";
        } else {
            // Handle failure
            return "error";
        }
    }

    @GetMapping("/search")
    public String searchContacts(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            Model model) {

        List<Contact> foundContacts = contactService.searchContacts(userId, firstName, lastName);
        model.addAttribute("contacts", foundContacts);

        return "contact-list"; // Return a view to display search results
    }

    @PostMapping("/update")
    public String updateContact(
            @RequestParam("userId") Long userId,
            @RequestParam("contactId") Long contactId,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber) {

        Contact updatedContact = new Contact.Builder(firstName, lastName, email, phoneNumber)
                .build();

        Contact updated = contactService.updateContact(userId, contactId, updatedContact);

        if (updated != null) {
            // Handle success
            return "redirect:/user/profile";
        } else {
            // Handle failure
            return "error";
        }
    }

    @PostMapping("/delete")
    public String deleteContact(
            @RequestParam("userId") Long userId,
            @RequestParam("contactId") Long contactId) {

        boolean deleted = contactService.deleteContact(userId, contactId);

        if (deleted) {
            // Handle success
            return "redirect:/user/profile";
        } else {
            // Handle failure
            return "error";
        }
    }

    @GetMapping("/getContactDetails/{contactId}")
    @ResponseBody
    public ResponseEntity<Contact> getContactDetails(@PathVariable Long contactId) {
        Contact contact = contactService.getContactById(contactId);

        if (contact != null) {
            return ResponseEntity.ok(contact); // Contact found, return it with status 200
        } else {
            return ResponseEntity.notFound().build(); // Contact not found, return status 404
        }
    }
}
