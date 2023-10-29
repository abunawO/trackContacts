package com.ose_abunaw.ose_abunaw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ose_abunaw.ose_abunaw.model.Contact;
import com.ose_abunaw.ose_abunaw.model.User;

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
