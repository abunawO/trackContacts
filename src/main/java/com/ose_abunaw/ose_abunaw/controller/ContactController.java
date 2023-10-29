package com.ose_abunaw.ose_abunaw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> createContact(
            @RequestParam("userId") Long userId,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber) {
        try {
            // Fetch the user
            User user = userService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            // Create a new contact using the builder pattern
            Contact newContact = buildContact(firstName, lastName, email, phoneNumber, user);

            Contact createdContact = contactService.createContact(userId, newContact);

            if (createdContact != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Contact created successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create contact");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create contact");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Contact>> searchContacts(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName) {
        try {
            List<Contact> foundContacts = contactService.searchContacts(userId, firstName, lastName);
            return ResponseEntity.ok(foundContacts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateContact(
            @RequestParam("userId") Long userId,
            @RequestParam("contactId") Long contactId,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber) {
        try {
            Contact updatedContact = buildContact(firstName, lastName, email, phoneNumber, null);

            Contact updated = contactService.updateContact(userId, contactId, updatedContact);

            if (updated != null) {
                return ResponseEntity.status(HttpStatus.OK).body("Contact updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update contact");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update contact");
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteContact(
            @RequestParam("userId") Long userId,
            @RequestParam("contactId") Long contactId) {
        try {
            boolean deleted = contactService.deleteContact(userId, contactId);

            if (deleted) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Contact deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete contact");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete contact");
        }
    }

    @GetMapping("/getContactDetails/{contactId}")
    public ResponseEntity<Contact> getContactDetails(@PathVariable Long contactId) {
        Contact contact = contactService.getContactById(contactId);

        if (contact != null) {
            return ResponseEntity.ok(contact);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Private utility method to create a Contact object to reduce redundancy
    private Contact buildContact(String firstName, String lastName, String email, String phoneNumber, User user) {
        return new Contact.Builder(firstName, lastName, email, phoneNumber)
                .user(user) // Associate the contact with the user
                .build();
    }
}
