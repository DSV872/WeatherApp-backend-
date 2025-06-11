package com.dsv.weatherApp.repository;

import com.dsv.weatherApp.model.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPreferenceRepository extends JpaRepository<UserPreference,Long> {
    Optional<UserPreference> findByUserId(String userId);
}
