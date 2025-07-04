package com.protectify.api.security.domain.services;

import com.protectify.api.security.domain.model.commands.CreateHouseCommand;
import com.protectify.api.security.domain.model.commands.UpdateHouseCommand;
import com.protectify.api.security.domain.model.commands.DeleteHouseCommand;
import com.protectify.api.security.domain.model.aggregates.House;

import java.util.Optional;

public interface HouseCommandService {
    Long handle(CreateHouseCommand command);
    Optional<House> handle(UpdateHouseCommand command);
    void handle(DeleteHouseCommand command);
}