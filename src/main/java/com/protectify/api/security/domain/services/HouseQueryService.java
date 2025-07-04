package com.protectify.api.security.domain.services;

import com.protectify.api.security.domain.model.aggregates.House;
import com.protectify.api.security.domain.model.queries.GetAllHousesQuery;
import com.protectify.api.security.domain.model.queries.GetHouseByIdQuery;
import com.protectify.api.security.domain.model.queries.GetHouseByOwnerIdQuery;

import java.util.List;
import java.util.Optional;

public interface HouseQueryService {
    Optional<House> handle(GetHouseByIdQuery query);
    List<House> handle(GetAllHousesQuery query);
    Optional<House> handle(GetHouseByOwnerIdQuery query);
}