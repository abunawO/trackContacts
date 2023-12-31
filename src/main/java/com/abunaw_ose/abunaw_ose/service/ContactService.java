package com.abunaw_ose.abunaw_ose.service;

import org.springframework.stereotype.Service;

import com.abunaw_ose.abunaw_ose.model.Contact;
import com.abunaw_ose.abunaw_ose.model.User;
import com.abunaw_ose.abunaw_ose.repository.ContactRepository;
import com.abunaw_ose.abunaw_ose.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    public ContactService(ContactRepository contactRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    public Contact createContact(Long userId, Contact contact) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User existingUser = user.get();
            // You can't modify a contact, so create a new one
            Contact newContact = new Contact(null, contact.getFirstName(), contact.getLastName(),
                    contact.getEmail(), contact.getPhoneNumber(), existingUser);
            return contactRepository.save(newContact);
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
            return Collections.emptyList(); // Return an empty list
        }
    }

    public Contact updateContact(Long userId, Long contactId, Contact updatedContact) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Contact> existingContact = contactRepository.findById(contactId);
            if (existingContact.isPresent()) {
                // You can't modify a contact, so create a new one
                Contact contact = existingContact.get();
                Contact newContact = new Contact(contact.getId(), updatedContact.getFirstName(),
                        updatedContact.getLastName(),
                        updatedContact.getEmail(), updatedContact.getPhoneNumber(), user.get());
                return contactRepository.save(newContact);
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
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User existingUser = user.get();
            return existingUser.getContacts();
        } else {
            // Handle the case where the user with the given ID is not found
            return Collections.emptyList();
        }
    }

    public Contact getContactById(Long contactId) {
        return contactRepository.findById(contactId).orElse(null);
    }
}
