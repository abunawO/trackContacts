package com.ose_abunaw.ose_abunaw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ose_abunaw.ose_abunaw.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId); // Custom findById method

    // Correct the return type to match JpaRepository's save method
    // User save(User user); // Custom save method

    User findByEmail(String email);
}
