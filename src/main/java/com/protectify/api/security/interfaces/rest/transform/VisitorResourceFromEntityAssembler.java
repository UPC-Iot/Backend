package com.protectify.api.security.interfaces.rest.transform;

import com.protectify.api.security.domain.model.entities.Visitor;
import com.protectify.api.security.interfaces.rest.resources.VisitorResource;

public class VisitorResourceFromEntityAssembler {
    public static VisitorResource toResourceFromEntity(Visitor visitor) {
        return new VisitorResource(
                visitor.getId(),
                visitor.getHouseId(),
                visitor.getFirstname(),
                visitor.getLastname(),
                visitor.getPhoto(),
                visitor.getRole(),
                visitor.getLastVisit()
        );
    }
}