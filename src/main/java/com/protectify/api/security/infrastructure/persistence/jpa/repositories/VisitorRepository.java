package com.protectify.api.security.infrastructure.persistence.jpa.repositories;

import com.protectify.api.security.domain.model.entities.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    List<Visitor> findAllByHouse_Id(Long houseId);
    List<Visitor> findAllByHouse_Owner_Id(Long ownerId);
}