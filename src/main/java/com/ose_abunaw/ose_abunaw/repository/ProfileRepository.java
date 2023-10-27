package com.ose_abunaw.ose_abunaw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ose_abunaw.ose_abunaw.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    // You can add custom query methods here if needed
}
