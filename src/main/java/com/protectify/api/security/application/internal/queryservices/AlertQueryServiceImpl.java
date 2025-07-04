package com.protectify.api.security.application.internal.queryservices;

import com.protectify.api.security.domain.model.entities.Alert;
import com.protectify.api.security.domain.model.queries.GetAllAlertsByHouseIdQuery;
import com.protectify.api.security.domain.model.queries.GetAllAlertsByOwnerIdQuery;
import com.protectify.api.security.domain.model.queries.GetAllAlertsQuery;
import com.protectify.api.security.domain.model.queries.GetAlertByIdQuery;
import com.protectify.api.security.domain.services.AlertQueryService;
import com.protectify.api.security.infrastructure.persistence.jpa.repositories.AlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlertQueryServiceImpl implements AlertQueryService {
    private final AlertRepository alertRepository;

    public AlertQueryServiceImpl(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Override
    public Optional<Alert> handle(GetAlertByIdQuery query) {
        return alertRepository.findById(query.id());
    }

    @Override
    public List<Alert> handle(GetAllAlertsQuery query) {
        return alertRepository.findAll();
    }

    @Override
    public List<Alert> handle(GetAllAlertsByHouseIdQuery query) {
        return alertRepository.findAllByHouse_Id(query.houseId());
    }

    @Override
    public List<Alert> handle(GetAllAlertsByOwnerIdQuery query) {
        return alertRepository.findAllByHouse_Owner_Id(query.ownerId());
    }
}