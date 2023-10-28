package com.ose_abunaw.ose_abunaw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ose_abunaw.ose_abunaw.model.Contact;
import com.ose_abunaw.ose_abunaw.model.User;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    // List<Contact> findByFirstNameContainingAndLastNameContaining(String
    // firstName, String lastName);

    // You can define custom query methods here if needed
    // For example, you can add a method to find contacts by user

    List<Contact> findByUser(User user);

    List<Contact> findByUserIdAndFirstNameContainingAndLastNameContaining(Long userId, String firstName,
            String lastName);

    // Add a method to find a contact by its ID
    Optional<Contact> findById(Long contactId);
}
