package com.protectify.api.security.domain.services;

import com.protectify.api.security.domain.model.entities.Alert;
import com.protectify.api.security.domain.model.queries.GetAllAlertsQuery;
import com.protectify.api.security.domain.model.queries.GetAlertByIdQuery;
import com.protectify.api.security.domain.model.queries.GetAllAlertsByHouseIdQuery;
import com.protectify.api.security.domain.model.queries.GetAllAlertsByOwnerIdQuery;

import java.util.List;
import java.util.Optional;

public interface AlertQueryService {
    Optional<Alert> handle(GetAlertByIdQuery query);
    List<Alert> handle(GetAllAlertsQuery query);
    List<Alert> handle(GetAllAlertsByHouseIdQuery query);
    List<Alert> handle(GetAllAlertsByOwnerIdQuery query);
}