package com.abunaw_ose.abunaw_ose.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.abunaw_ose.abunaw_ose.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    // Can be impleneted in the future
}
