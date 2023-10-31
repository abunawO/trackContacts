package com.abunaw_ose.abunaw_ose.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abunaw_ose.abunaw_ose.model.Contact;
import com.abunaw_ose.abunaw_ose.model.User;

import java.util.List;
import java.util.Optional;

//ContactRepository to perfom various database operations

public interface ContactRepository extends JpaRepository<Contact, Long> {
    // method to find contact by user
    List<Contact> findByUser(User user);

    // method to find contact by userId, FirstName, LastName
    List<Contact> findByUserIdAndFirstNameContainingAndLastNameContaining(Long userId, String firstName,
            String lastName);

    // method to find a contact by its ID
    Optional<Contact> findById(Long contactId);
}
