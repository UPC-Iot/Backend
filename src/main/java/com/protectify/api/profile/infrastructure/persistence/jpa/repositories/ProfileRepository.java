package com.protectify.api.profile.infrastructure.persistence.jpa.repositories;

import com.protectify.api.profile.domain.model.aggregates.Profile;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUser_Id(Long userId);
}