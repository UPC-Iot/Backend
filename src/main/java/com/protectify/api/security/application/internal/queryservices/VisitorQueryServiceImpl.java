package com.protectify.api.security.application.internal.queryservices;

import com.protectify.api.security.domain.model.entities.Visitor;
import com.protectify.api.security.domain.model.queries.GetAllVisitorsByHouseIdQuery;
import com.protectify.api.security.domain.model.queries.GetAllVisitorsByOwnerIdQuery;
import com.protectify.api.security.domain.model.queries.GetAllVisitorsQuery;
import com.protectify.api.security.domain.model.queries.GetVisitorByIdQuery;
import com.protectify.api.security.domain.services.VisitorQueryService;
import com.protectify.api.security.infrastructure.persistence.jpa.repositories.VisitorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisitorQueryServiceImpl implements VisitorQueryService {
    private final VisitorRepository visitorRepository;

    public VisitorQueryServiceImpl(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    @Override
    public Optional<Visitor> handle(GetVisitorByIdQuery query) {
        return visitorRepository.findById(query.id());
    }

    @Override
    public List<Visitor> handle(GetAllVisitorsQuery query) {
        return visitorRepository.findAll();
    }

    @Override
    public List<Visitor> handle(GetAllVisitorsByHouseIdQuery query) {
        return visitorRepository.findAllByHouse_Id(query.houseId());
    }

    @Override
    public List<Visitor> handle(GetAllVisitorsByOwnerIdQuery query) {
        return visitorRepository.findAllByHouse_Owner_Id(query.ownerId());
    }
}