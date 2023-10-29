package com.ose_abunaw.ose_abunaw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ose_abunaw.ose_abunaw.model.User;

import java.util.Optional;

//UserRepository to perfom various database operations
public interface UserRepository extends JpaRepository<User, Long> {
    // method to find user by id
    Optional<User> findById(Long userId);

    // method to find user by email
    User findByEmail(String email);
}
