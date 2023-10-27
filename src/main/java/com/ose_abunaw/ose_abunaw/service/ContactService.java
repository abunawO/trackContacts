package com.ose_abunaw.ose_abunaw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ose_abunaw.ose_abunaw.model.Contact;
import com.ose_abunaw.ose_abunaw.model.User;
import com.ose_abunaw.ose_abunaw.repository.ContactRepository;

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

    // Create a new contact
    public Contact createContact(Contact contact, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            contact.setUser(user);
            return contactRepository.save(contact);
        }
        return null; // Handle the case where the user does not exist.
    }

    // Get a contact by ID
    public Contact getContactById(Long id) {
        Optional<Contact> contact = contactRepository.findById(id);
        return contact.orElse(null);
    }

    // Get all contacts for a specific user
    public List<Contact> getAllContactsByUser(Long userId) {
        return contactRepository.findByUser(userRepository.findById(userId).orElse(null));
    }

    // Update a contact by ID
    public Contact updateContact(Long id, Contact updatedContact) {
        Optional<Contact> contact = contactRepository.findById(id);
        if (contact.isPresent()) {
            Contact existingContact = contact.get();
            existingContact.setFirstName(updatedContact.getFirstName());
            existingContact.setLastName(updatedContact.getLastName());
            existingContact.setEmail(updatedContact.getEmail());
            existingContact.setPhoneNumber(updatedContact.getPhoneNumber());
            return contactRepository.save(existingContact);
        }
        return null;
    }

    // Delete a contact by ID
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
}

