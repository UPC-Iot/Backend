package com.protectify.api.profile.application.internal.queryservices;

import com.protectify.api.profile.domain.model.entities.Owner;
import com.protectify.api.profile.domain.model.queries.GetAllOwnersQuery;
import com.protectify.api.profile.domain.model.queries.GetOwnerByIdQuery;
import com.protectify.api.profile.domain.model.queries.GetOwnerByUserIdQuery;
import com.protectify.api.profile.domain.services.OwnerQueryService;
import com.protectify.api.profile.infrastructure.persistence.jpa.repositories.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerQueryServiceImpl implements OwnerQueryService {
    private final OwnerRepository ownerRepository;

    public OwnerQueryServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Optional<Owner> handle(GetOwnerByIdQuery query) {
        return ownerRepository.findById(query.id());
    }

    @Override
    public List<Owner> handle(GetAllOwnersQuery query) {
        return ownerRepository.findAll();
    }

    @Override
    public Optional<Owner> handle(GetOwnerByUserIdQuery query) {
        return ownerRepository.findByUser_Id(query.userId());
    }
}