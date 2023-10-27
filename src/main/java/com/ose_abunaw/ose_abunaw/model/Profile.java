package com.ose_abunaw.ose_abunaw.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_profiles") // Specify a table name for user profiles
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id") // Create a foreign key to link profiles to users
    private User user;

    // Additional profile attributes
    private String fullName;
    private String address;

    // Constructors
    public Profile() {
    }

    public Profile(String fullName, String address) {
        this.fullName = fullName;
        this.address = address;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
