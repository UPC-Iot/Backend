package com.protectify.api.security.interfaces.rest.transform;

import com.protectify.api.security.domain.model.aggregates.House;
import com.protectify.api.security.interfaces.rest.resources.HouseResource;

public class HouseResourceFromEntityAssembler {
    public static HouseResource toResourceFromEntity(House house) {
        return new HouseResource(
                house.getId(),
                house.getOwnerId(),
                house.getAddress(),
                house.getName(),
                house.getDescription()
        );
    }
}