package com.protectify.api.profile.domain.services;

import com.protectify.api.profile.domain.model.entities.Owner;
import com.protectify.api.profile.domain.model.queries.GetAllOwnersQuery;
import com.protectify.api.profile.domain.model.queries.GetOwnerByIdQuery;
import com.protectify.api.profile.domain.model.queries.GetOwnerByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface OwnerQueryService {
    Optional<Owner> handle(GetOwnerByIdQuery query);
    List<Owner> handle(GetAllOwnersQuery query);
    Optional<Owner> handle(GetOwnerByUserIdQuery query);
}