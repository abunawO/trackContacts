package com.abunaw_ose.abunaw_ose.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abunaw_ose.abunaw_ose.model.User;

import java.util.Optional;

//UserRepository to perfom various database operations
public interface UserRepository extends JpaRepository<User, Long> {
    // method to find user by id
    Optional<User> findById(Long userId);

    // method to find user by email
    User findByEmail(String email);
}
