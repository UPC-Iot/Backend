package com.protectify.api.security.application.internal.commandservices;

import com.protectify.api.security.application.internal.outboundservices.acl.ExternalProfilesService;
import com.protectify.api.security.domain.exceptions.AlertNotFoundException;
import com.protectify.api.security.domain.model.commands.CreateAlertCommand;
import com.protectify.api.security.domain.model.commands.UpdateAlertCommand;
import com.protectify.api.security.domain.model.commands.DeleteAlertCommand;
import com.protectify.api.security.domain.model.entities.Alert;
import com.protectify.api.security.domain.model.aggregates.House;
import com.protectify.api.security.domain.services.AlertCommandService;
import com.protectify.api.security.infrastructure.persistence.jpa.repositories.AlertRepository;
import com.protectify.api.security.infrastructure.persistence.jpa.repositories.HouseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlertCommandServiceImpl implements AlertCommandService {
    private final AlertRepository alertRepository;
    private final HouseRepository houseRepository;
    private final ExternalProfilesService externalProfilesService;

    public AlertCommandServiceImpl(AlertRepository alertRepository, HouseRepository houseRepository, ExternalProfilesService externalProfilesService) {
        this.alertRepository = alertRepository;
        this.houseRepository = houseRepository;
        this.externalProfilesService = externalProfilesService;
    }

    @Override
    public Long handle(CreateAlertCommand command) {
        // Se asume que el comando incluye el id de la casa
        Long houseId = command.houseId();
        Optional<House> house = houseRepository.findById(houseId);
        if (house.isEmpty()) {
            throw new RuntimeException("House with id " + houseId + " not found");
        }
        Alert alert = new Alert(command, house.get());
        alertRepository.save(alert);
        return alert.getId();
    }

    @Override
    public Optional<Alert> handle(UpdateAlertCommand command) {
        var alert = alertRepository.findById(command.id());
        if (alert.isEmpty()) {
            return Optional.empty();
        }
        Alert updatedAlert = alertRepository.save(alert.get().update(command));
        return Optional.of(updatedAlert);
    }

    @Override
    public void handle(DeleteAlertCommand command) {
        var alert = alertRepository.findById(command.id());
        if (alert.isEmpty()) {
            throw new AlertNotFoundException(command.id());
        }
        alertRepository.delete(alert.get());
    }
}