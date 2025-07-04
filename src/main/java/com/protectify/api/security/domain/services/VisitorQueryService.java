package com.protectify.api.security.domain.services;

import com.protectify.api.security.domain.model.entities.Visitor;
import com.protectify.api.security.domain.model.queries.GetAllVisitorsQuery;
import com.protectify.api.security.domain.model.queries.GetVisitorByIdQuery;
import com.protectify.api.security.domain.model.queries.GetAllVisitorsByHouseIdQuery;
import com.protectify.api.security.domain.model.queries.GetAllVisitorsByOwnerIdQuery;

import java.util.List;
import java.util.Optional;

public interface VisitorQueryService {
    Optional<Visitor> handle(GetVisitorByIdQuery query);
    List<Visitor> handle(GetAllVisitorsQuery query);
    List<Visitor> handle(GetAllVisitorsByHouseIdQuery query);
    List<Visitor> handle(GetAllVisitorsByOwnerIdQuery query);
}