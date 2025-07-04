package com.protectify.api.security.infrastructure.persistence.jpa.repositories;

import com.protectify.api.security.domain.model.aggregates.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    Optional<House> findByOwner_Id(Long ownerId);
}