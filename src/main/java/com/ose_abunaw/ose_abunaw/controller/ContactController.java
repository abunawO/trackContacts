package com.ose_abunaw.ose_abunaw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ose_abunaw.ose_abunaw.model.Contact;
import com.ose_abunaw.ose_abunaw.service.ContactService;
import com.ose_abunaw.ose_abunaw.service.UserService;
import com.ose_abunaw.ose_abunaw.model.User;

@Controller
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;
    private final UserService userService;

    public ContactController(ContactService contactService, UserService userService) {
        this.contactService = contactService;
        this.userService = userService;
    }

    @PostMapping("/createContact")
    public String createContact(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            Model model,
            RedirectAttributes redirectAttributes) {

        // Retrieve the signed-in user's information
        User user = userService.getSignedInUser();

        if (user != null) {
            // Create a new contact
            Contact newContact = new Contact(null, firstName, lastName, email, phone, user);

            // Save the new contact using the ContactService
            Contact createdContact = contactService.createContact(user.getId(), newContact);

            if (createdContact != null) {
                // Contact created successfully, add a success message and redirect to the user
                // profile page
                redirectAttributes.addFlashAttribute("successMessage", "Contact created successfully");
                return "redirect:/user/profile";
            } else {
                // Handle the case where the user with the given ID is not found
                return "error";
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
            Model model,
            RedirectAttributes redirectAttributes) {

        // Retrieve the signed-in user's information
        User user = userService.getSignedInUser();

        if (user != null) {
            // Update the contact with the provided details
            Contact updatedContact = new Contact(contactId, firstName, lastName, email, phone, user);
            updatedContact.setId(contactId); // Set the ID of the contact to be updated

            // Call the ContactService to update the contact
            Contact updated = contactService.updateContact(user.getId(), contactId, updatedContact);

            if (updated != null) {
                // Redirect back to the user profile page
                redirectAttributes.addFlashAttribute("successMessage", "Contact updated successfully");
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

    @PostMapping("/search")
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
}
