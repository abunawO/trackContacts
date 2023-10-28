package com.ose_abunaw.ose_abunaw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ose_abunaw.ose_abunaw.model.Contact;
import com.ose_abunaw.ose_abunaw.model.User;
import com.ose_abunaw.ose_abunaw.repository.ContactRepository;
import com.ose_abunaw.ose_abunaw.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    public Contact createContact(Long userId, Contact contact) {
        System.out.println("In contact service class");
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User existingUser = user.get();
            contact.setUser(existingUser); // Associate the contact with the user
            return contactRepository.save(contact);
        } else {
            // Handle the case where the user with the given ID is not found
            return null;
        }
    }

    public List<Contact> searchContacts(Long userId, String firstName, String lastName) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            if (firstName == null && lastName == null) {
                // Return all contacts for the user if both first name and last name are null
                return user.get().getContacts();
            } else {
                // Search by first name and/or last name for the user
                return contactRepository.findByUserIdAndFirstNameContainingAndLastNameContaining(userId, firstName,
                        lastName);
            }
        } else {
            // Handle the case where the user with the given ID is not found
            return null;
        }
    }

    public Contact updateContact(Long userId, Long contactId, Contact updatedContact) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Contact> existingContact = contactRepository.findById(contactId);
            if (existingContact.isPresent()) {
                Contact contact = existingContact.get();
                contact.setFirstName(updatedContact.getFirstName());
                contact.setLastName(updatedContact.getLastName());
                contact.setEmail(updatedContact.getEmail());
                contact.setPhoneNumber(updatedContact.getPhoneNumber());
                return contactRepository.save(contact);
            } else {
                // Handle the case where the contact with the given ID is not found
                return null;
            }
        } else {
            // Handle the case where the user with the given ID is not found
            return null;
        }
    }

    public boolean deleteContact(Long userId, Long contactId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Contact> contact = contactRepository.findById(contactId);
            if (contact.isPresent()) {
                contactRepository.delete(contact.get());
                return true;
            } else {
                // Handle the case where the contact with the given ID is not found
                return false;
            }
        } else {
            // Handle the case where the user with the given ID is not found
            return false;
        }
    }

    public List<Contact> getAllContactsByUserId(Long userId) {
        System.out.println("in getAllContactsByUserId");
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User existingUser = user.get();
            System.out.println("contacts: " + existingUser.getContacts());
            return existingUser.getContacts();
        } else {
            // Handle the case where the user with the given ID is not found
            return Collections.emptyList();
        }
    }

}