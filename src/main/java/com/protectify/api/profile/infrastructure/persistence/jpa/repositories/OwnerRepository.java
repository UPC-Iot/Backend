package com.protectify.api.profile.infrastructure.persistence.jpa.repositories;

import com.protectify.api.profile.domain.model.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByUser_Id(Long userId);
}