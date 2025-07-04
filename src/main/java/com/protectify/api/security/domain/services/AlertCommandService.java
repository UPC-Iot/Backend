package com.protectify.api.security.domain.services;

import com.protectify.api.security.domain.model.commands.CreateAlertCommand;
import com.protectify.api.security.domain.model.commands.UpdateAlertCommand;
import com.protectify.api.security.domain.model.commands.DeleteAlertCommand;
import com.protectify.api.security.domain.model.entities.Alert;

import java.util.Optional;

public interface AlertCommandService {
    Long handle(CreateAlertCommand command);
    Optional<Alert> handle(UpdateAlertCommand command);
    void handle(DeleteAlertCommand command);
}