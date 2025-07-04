package com.protectify.api.security.application.internal.queryservices;

import com.protectify.api.security.domain.model.aggregates.House;
import com.protectify.api.security.domain.model.queries.GetAllHousesQuery;
import com.protectify.api.security.domain.model.queries.GetHouseByIdQuery;
import com.protectify.api.security.domain.model.queries.GetHouseByOwnerIdQuery;
import com.protectify.api.security.domain.services.HouseQueryService;
import com.protectify.api.security.infrastructure.persistence.jpa.repositories.HouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HouseQueryServiceImpl implements HouseQueryService {
    private final HouseRepository houseRepository;

    public HouseQueryServiceImpl(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    public Optional<House> handle(GetHouseByIdQuery query) {
        return houseRepository.findById(query.id());
    }

    @Override
    public List<House> handle(GetAllHousesQuery query) {
        return houseRepository.findAll();
    }

    @Override
    public Optional<House> handle(GetHouseByOwnerIdQuery query) {
        return houseRepository.findByOwner_Id(query.ownerId());
    }
}