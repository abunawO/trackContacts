package com.ose_abunaw.ose_abunaw.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ose_abunaw.ose_abunaw.model.Contact;
import com.ose_abunaw.ose_abunaw.service.ContactService;
import com.ose_abunaw.ose_abunaw.service.UserService;

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

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createContact(
            @RequestParam("userId") Long userId,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber) {

        Contact newContact = new Contact();
        newContact.setFirstName(firstName);
        newContact.setLastName(lastName);
        newContact.setEmail(email);
        newContact.setPhoneNumber(phoneNumber);

        Contact createdContact = contactService.createContact(userId, newContact);

        if (createdContact != null) {
            // Handle success (e.g., show a success message or redirect to a confirmation
            // page
            return "success";
        } else {
            // Handle failure (e.g., show an error message or redirect to an error page)
            return "error";
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchContacts(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            Model model) {

        List<Contact> foundContacts = contactService.searchContacts(userId, firstName, lastName);
        model.addAttribute("contacts", foundContacts);

        return "contact-list"; // Return a view to display search results
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateContact(
            @RequestParam("userId") Long userId,
            @RequestParam("contactId") Long contactId,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber) {

        System.out.println("in /update method " + contactId);
        Contact updatedContact = new Contact();
        updatedContact.setFirstName(firstName);
        updatedContact.setLastName(lastName);
        updatedContact.setEmail(email);
        updatedContact.setPhoneNumber(phoneNumber);

        Contact updated = contactService.updateContact(userId, contactId, updatedContact);

        if (updated != null) {
            // Handle success (e.g., show a success message or redirect to a confirmation
            // page
            // return "success";
            return "redirect:/user/profile";
        } else {
            // Handle failure (e.g., show an error message or redirect to an error page)
            return "error";
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteContact(
            @RequestParam("userId") Long userId,
            @RequestParam("contactId") Long contactId) {

        System.out.println("in /delete method " + contactId);

        boolean deleted = contactService.deleteContact(userId, contactId);

        if (deleted) {
            // Handle success (e.g., show a success message or redirect to a confirmation
            // page
            return "redirect:/user/profile";
        } else {
            // Handle failure (e.g., show an error message or redirect to an error page)
            return "error";
        }
    }

    @GetMapping("/getContactDetails/{contactId}")
    @ResponseBody
    public ResponseEntity<Contact> getContactDetails(@PathVariable Long contactId) {
        System.out.println("in /getContactDetails/{contactId} " + contactId);
        Contact contact = contactService.getContactById(contactId);
        System.out.println("in /getContactDetails/{contactId} " + contact.getFirstName());

        if (contact != null) {
            // If the contact is found, return it as a JSON response
            return ResponseEntity.ok(contact);
        } else {
            // If the contact is not found, return a 404 status code
            return ResponseEntity.notFound().build();
        }
    }

}